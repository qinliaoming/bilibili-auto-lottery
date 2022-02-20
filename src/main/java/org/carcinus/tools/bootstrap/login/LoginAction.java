package org.carcinus.tools.bootstrap.login;

import org.carcinus.tools.context.GlobalContext;

public interface LoginAction {
    boolean login(GlobalContext context);
}
