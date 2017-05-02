package com.csb.sports.core.service;

import com.csb.sports.common.dao.SportUserDao;
import com.csb.sports.common.pojo.SportUserDo;
import com.csb.sports.common.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author 陈少波
 * @version $Id: TestService, v0.1 2017年05月01日 10:47 Exp $
 */
@Service
public class TestService {

    private static final long serialVersionUID = 6374850855596138995L;

    @Autowired
    private SportUserDao sportUserDao;

    public void test_service() {

        SportUserDo sportUserDo = new SportUserDo();
        sportUserDo.setUserName("beat");
        sportUserDo.setUid("2088208820882088");
        sportUserDo.setLoginId("15622348348");
        sportUserDao.insert(sportUserDo);
        LoggerUtil.info(LoggerUtil.SPORTSPROD_LOGGER,"insert sports user data:",sportUserDo);
    }

    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml",
            "spring/applicationContext-transaction.xml",
            "spring/applicationContext-dataSource.xml",
            "spring/applicationContext-mybatis.xml");

        TestService testService = ctx.getBean(TestService.class);
        testService.test_service();
    }

}
