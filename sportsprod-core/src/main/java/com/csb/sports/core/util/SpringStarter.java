package com.csb.sports.core.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring上下文启动器
 * @author 陈少波
 * @version $Id: SpringStarter, v0.1 2017年05月01日 21:38 Exp $
 */
public class SpringStarter {

    private static final String[] SPRING_CONTEXTS = {"spring/applicationContext.xml",
        "spring/applicationContext-transaction.xml",
        "spring/applicationContext-dataSource.xml",
        "spring/applicationContext-mybatis.xml"};

    public static final ApplicationContext CTX = new ClassPathXmlApplicationContext(SPRING_CONTEXTS,true,null);

}
