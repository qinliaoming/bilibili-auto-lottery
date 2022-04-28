package org.carcinus.tools.utils;

import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.context.GlobalContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailUtilsTest {

    private GlobalContext context = TestGlobalContextUtils.getGlobalContext();

    @Before
    public void setConf() {
        context.setConf(KeyConstant.EMAIL_USER_NAME, "qinliaoming@163.com");
        context.setConf(KeyConstant.EMAIL_PASSWORD, "RMNTKHDEBHARAFOZ");
        context.setConf(KeyConstant.EMAIL_SEND_TO, "qinliaoming@163.com");
//        context.setConf(KeyConstant.EMAIL_SEND_TO, "1195058849@qq.com");
    }

    @Test
    public void sendEmail() {
        EmailUtils.sendEmail(context, "Test");
    }
}