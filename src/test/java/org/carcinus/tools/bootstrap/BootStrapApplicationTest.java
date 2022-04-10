package org.carcinus.tools.bootstrap;

import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.TestGlobalContextUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BootStrapApplicationTest {

    private GlobalContext globalContext;

    @Before
    public void setUp() throws Exception {
        globalContext = TestGlobalContextUtils.getGlobalContext();
        globalContext.setConf("auto.lottery.login.action.type", "QUICK_RESPONSE");
    }

    @Test
    public void bootstrap() {

        BootStrapApplication.bootstrap(globalContext);
    }
}