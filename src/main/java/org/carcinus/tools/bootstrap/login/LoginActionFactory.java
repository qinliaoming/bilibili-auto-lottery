package org.carcinus.tools.bootstrap.login;

import org.carcinus.tools.bootstrap.login.impl.PassWordLoginAction;
import org.carcinus.tools.bootstrap.login.impl.qr.QRLoginAction;

public class LoginActionFactory {

    public static LoginAction getInstance(LoginActionType loginActionType){
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
