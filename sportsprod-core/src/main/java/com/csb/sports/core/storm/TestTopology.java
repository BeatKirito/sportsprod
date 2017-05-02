package com.csb.sports.core.storm;

import java.util.Map;
import java.util.UUID;

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
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "BoltTest A uid:", uid);
            basicOutputCollector.emit(new Values(uid));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("uid"));
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
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "BoltTest B uid:", uid);
            try {
                testService.test_service();
            } catch (Exception e) {
                LoggerUtil.error(e, "testService Exception!,uid:", uid);
                return;
            }
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "testBolt B testService successful,", uid);
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

    public static class TestSpout extends BaseRichSpout {

        private SpoutOutputCollector collector;

        @Override
        public void open(Map map, TopologyContext topologyContext,
                         SpoutOutputCollector spoutOutputCollector) {
            this.collector = spoutOutputCollector;
        }

        @Override
        public void nextTuple() {
            String uid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "SpoutTest, uid:", uid);
            collector.emit(new Values(uid));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("uid"));
        }
    }

    static boolean isLocal = true;

    public static void test(String[] args) {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("testSpout", new TestSpout(), 2);
        builder.setBolt("testBoltA", new TestBoltA(), 2).shuffleGrouping("testSpout");
        builder.setBolt("testBoltB", new TestBoltB(), 2).shuffleGrouping("testBoltA");

        Config conf = JStormHelper.getConfig(args);
        conf.setDebug(true);

        LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER, "TestTopology Go!!!!!");

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

    public static void main(String[] args) throws Exception {
        isLocal = true;
        test(args);
    }
}
