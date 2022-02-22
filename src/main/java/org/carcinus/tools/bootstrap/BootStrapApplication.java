package org.carcinus.tools.bootstrap;

import com.google.common.base.Preconditions;
import org.carcinus.tools.bootstrap.login.LoginAction;
import org.carcinus.tools.bootstrap.login.LoginActionFactory;
import org.carcinus.tools.bootstrap.login.LoginActionType;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.exception.EnumConstantNotFountException;
import org.carcinus.tools.utils.EnumUtils;
import org.carcinus.tools.log.Logging;

public class BootStrapApplication implements Logging {

    public static boolean bootstrap(GlobalContext context) {
        boolean isLoginSuccess = login(context);

        return true;
    }

    private static boolean login(GlobalContext context) {
        String loginActionTypeStr = context.getConf("auto.lottery.login.action.type");
        try {
            LoginAction loginAction = LoginActionFactory.getInstance(loginActionTypeStr);
            return loginAction.login(context);
        } catch (EnumConstantNotFountException e) {
            logger.info(e.getMessage(), e);
            return false;
        }
    }
}
