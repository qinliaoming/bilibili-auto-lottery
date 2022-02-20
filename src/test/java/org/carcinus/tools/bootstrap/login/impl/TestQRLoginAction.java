package org.carcinus.tools.bootstrap.login.impl;


import org.carcinus.tools.bootstrap.login.LoginAction;
import org.carcinus.tools.bootstrap.login.LoginActionFactory;
import org.carcinus.tools.bootstrap.login.LoginActionType;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.TestGlobalContextUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class TestQRLoginAction {

    private static LoginAction loginAction;
    private static GlobalContext context;
    @Before
    public void init() {
        loginAction = LoginActionFactory.getInstance(LoginActionType.QUICK_RESPONSE);
        HashMap<Object, Object> conf = new HashMap<>();

        context = TestGlobalContextUtils.getGlobalContext();
    }

    @Test
    public void testLogin() {

        loginAction.login(context);
    }

}