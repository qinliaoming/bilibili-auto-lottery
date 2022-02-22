package org.carcinus.tools.bootstrap.login;

import com.google.common.base.Preconditions;
import org.carcinus.tools.bootstrap.login.impl.PassWordLoginAction;
import org.carcinus.tools.bootstrap.login.impl.qr.QRLoginAction;
import org.carcinus.tools.exception.EnumConstantNotFountException;
import org.carcinus.tools.utils.EnumUtils;

public class LoginActionFactory {

    public static LoginAction getInstance(String loginActionType) throws EnumConstantNotFountException {
        LoginActionType actionType = EnumUtils.parseIgnoreCase(LoginActionType.class, loginActionType);
        Preconditions.checkNotNull(actionType, loginActionType + "not impl");
        return getInstance(actionType);
    }

    public static LoginAction getInstance(LoginActionType loginActionType) {

        switch (loginActionType) {
            case QUICK_RESPONSE:
                return new QRLoginAction();
            case PASSWORD:
                return new PassWordLoginAction();
            default:
                return null;
        }
    }
}
