package com.csb.sports.core.storm;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.jstorm.client.spout.IFailValueSpout;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.csb.sports.common.util.LoggerUtil;
import com.csb.sports.core.service.TestService;
import com.csb.sports.core.util.Assert;
import com.csb.sports.core.util.JStormHelper;
import com.csb.sports.core.util.SpringStarter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * 测试用拓扑
 * @author 陈少波
 * @version $Id: TestTopology, v0.1 2017年05月01日 21:37 Exp $
 */
public class TestTopology {

    public static class TestBoltA extends BaseBasicBolt {

        @Override
        public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
            String uid = tuple.getString(0);
            String value = tuple.getString(1);
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "BoltTest A uid:", uid, ",value:", value);
            basicOutputCollector.emit(new Values(uid, value));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("uid", "value"));
        }
    }

    public static class TestBoltB extends BaseBasicBolt {

        private transient TestService testService;

        @Override
        public void prepare(Map stormConf, TopologyContext context) {
            super.prepare(stormConf, context);
            testService = SpringStarter.CTX.getBean(TestService.class);
        }

        @Override
        public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
            String uid = tuple.getString(0);
            String value = tuple.getString(1);
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "BoltTest B uid:", uid, ",value:", value);
            try {
                testService.test_service();
            } catch (Exception e) {
                LoggerUtil.error(e, "testService Exception!,uid:", uid, ",value:", value);
                return;
            }
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "testBolt B testService successful,", uid,
                ",value:", value);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

        }

        /**
         * Getter method for property <tt>testService</tt>.
         *
         * @return property value of testService
         */
        public TestService getTestService() {
            return testService;
        }
    }

    public static class TestSpout extends BaseRichSpout implements IFailValueSpout {

        private static LinkedBlockingDeque<String> sendingQueue = new LinkedBlockingDeque<>();

        private SpoutOutputCollector               collector;

        private static final ThreadPoolExecutor    THREAD_POOL  = new ThreadPoolExecutor(2, 4, 3,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
            new ThreadPoolExecutor.DiscardOldestPolicy());

        @Override
        public void open(Map map, TopologyContext topologyContext,
                         SpoutOutputCollector spoutOutputCollector) {
            this.collector = spoutOutputCollector;
            THREAD_POOL.execute(new DataComsumer(sendingQueue));
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "Spout open,this:", this);
        }

        @Override
        public synchronized void close() {
            //关闭spout时，关闭线程池
            if(!THREAD_POOL.isShutdown()) {
                THREAD_POOL.shutdown();
                LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER,"spout close shutdown comsumer thread pool! this:",this);
            }
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "Spout close,this:", this);
        }

        @Override
        public void nextTuple() {
            String uid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER,"spout test,uid:",uid,",this:",this);

            String value = null;
            try {
                value = sendingQueue.take();
            } catch (InterruptedException e) {
                LoggerUtil.warn(LoggerUtil.SPORTSPROD_LOGGER, e, "queue intertupted!,uid:", uid);
            }

            if (value != null) {
                LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "Spout Deal Tuple, uid:", uid,
                    ",value:", value, ",this:", this);
                collector.emit(new Values(uid, value));
            }
        }

        @Override
        public void fail(Object msgId, List<Object> values) {
            String uid = (String) values.get(0);
            String value = (String) values.get(1);
            sendingQueue.offer(value);
            LoggerUtil.warn(LoggerUtil.SPORTSPROD_LOGGER, "Spout fail,uid:", uid, ",value:", value);
            //TODO: 添加fail超过指定次数则丢弃数据的逻辑
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("uid", "value"));
        }

        private static class DataComsumer implements Runnable {

            private LinkedBlockingDeque<String> sendingQueue;

            public DataComsumer(LinkedBlockingDeque<String> sendingQueue) {
                this.sendingQueue = sendingQueue;
            }

            @Override
            public void run() {
                Properties props = new Properties();
                props.put("bootstrap.servers", "120.24.232.79:9092");
                props.put("group.id", "S_SRORTSPROD");
                props.put("enable.auto.commit", "true");
                props.put("auto.commit.interval.ms", "1000");
                props.put("key.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");
                props.put("value.deserializer",
                    "org.apache.kafka.common.serialization.StringDeserializer");
                KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
                consumer.subscribe(Arrays.asList("TP_SPROTSPROD"));
                while (true) {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "comsumer comsume,message:",
                        records);
                    for (ConsumerRecord<String, String> record : records) {
                        sendingQueue.offer(record.value());
                        LoggerUtil.debug(LoggerUtil.SPORTSPROD_LOGGER,
                            "offer message to local Queue,value:", record.value());
                    }

                    try {
                        //每批数据离散0.5s
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        LoggerUtil.warn(LoggerUtil.SPORTSPROD_LOGGER,
                            "DataComsumer thread InterruptedException!");
                        break;
                    }
                }
            }
        }

    }

    public static class DataGenerator implements Runnable {

        @Override
        public void run() {

            Properties props = new Properties();
            props.put("bootstrap.servers", "120.24.232.79:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            Producer<String, String> producer = new KafkaProducer<>(props);
            for (int i = 0; i < 100; i++) {
                String uid = System.currentTimeMillis() + "";
                try {
                    Thread.sleep(100);
                    producer.send(new ProducerRecord<String, String>("TP_SPROTSPROD",
                        Integer.toString(i), uid));
                    LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "Producer send message:", uid);
                } catch (InterruptedException e) {
                    LoggerUtil.warn(LoggerUtil.SPORTSPROD_LOGGER,
                        "thread InterruptedException!,uid:", uid);
                    break;
                }
            }
            producer.close();
        }
    }

    static boolean isLocal = true;

    public static void test(String[] args) {

        startDataGen();

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("testSpout", new TestSpout(), 6);
        builder.setBolt("testBoltA", new TestBoltA(), 6).shuffleGrouping("testSpout");
        builder.setBolt("testBoltB", new TestBoltB(), 6).shuffleGrouping("testBoltA");

        Config conf = JStormHelper.getConfig(args);
        conf.setDebug(true);
        String log4jconfig = (String) conf.get("user.defined.log4j.conf");
        LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "log4jconfig = ", log4jconfig);

        String[] className = Thread.currentThread().getStackTrace()[1].getClassName().split("\\.");
        String topologyName = className[className.length - 1];


        try {
            JStormHelper.runTopology(builder.createTopology(), topologyName, conf, 60,
                new JStormHelper.CheckAckedFail(conf), isLocal);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed");
        }

    }

    public static void startDataGen() {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 3, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());

        threadPool.execute(new DataGenerator());
        LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER,"submit dataGeneraor task!!!");
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            LoggerUtil.warn(LoggerUtil.SPORTSPROD_LOGGER, e, "submit main thread interrupted!");
        }
        threadPool.shutdown();
    }

    public static void main(String[] args) throws Exception {
        isLocal = false;
        test(args);
    }
}
