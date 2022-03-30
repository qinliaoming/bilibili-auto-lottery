package org.carcinus.tools.bootstrap.login;

import com.google.common.base.Preconditions;
import org.carcinus.tools.bootstrap.login.impl.PassWordLoginAction;
import org.carcinus.tools.bootstrap.login.impl.qr.QRLoginAction;
import org.carcinus.tools.exception.EnumConstantNotFountException;
import org.carcinus.tools.utils.EnumUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginActionFactory {

    private static Map<LoginActionType, LoginAction> loginActionCacheMap = new ConcurrentHashMap<>();

    public static LoginAction getInstance(String loginActionType) throws EnumConstantNotFountException {
        LoginActionType actionType = EnumUtils.parseIgnoreCase(LoginActionType.class, loginActionType);
        Preconditions.checkNotNull(actionType, loginActionType + "not impl");
        return getInstance(actionType);
    }

    public static LoginAction getInstance(LoginActionType loginActionType) {

        LoginAction loginAction = loginActionCacheMap.get(loginActionType);
        if (loginAction != null) return loginAction;
        switch (loginActionType) {
            case QUICK_RESPONSE:
                QRLoginAction qrLoginAction = new QRLoginAction();
                loginActionCacheMap.put(loginActionType, qrLoginAction);
                return qrLoginAction;
            case PASSWORD:
                PassWordLoginAction passWordLoginAction = new PassWordLoginAction();
                loginActionCacheMap.put(loginActionType, passWordLoginAction);
                return passWordLoginAction;
            default:
                return null;
        }
    }
}
