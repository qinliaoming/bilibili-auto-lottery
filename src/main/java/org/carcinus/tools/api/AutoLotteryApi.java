package org.carcinus.tools.api;

import org.carcinus.tools.context.GlobalContext;

public class AutoLotteryApi {

    public static boolean login(GlobalContext context) {
        try {
            return LoginApi.login(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getLotteryTagId() {
        int lotteryTagId = RelationApi.getLotteryTagId();
        if (lotteryTagId == -1) {
            lotteryTagId = RelationApi.createLotteryTag();
        }
        return lotteryTagId;
    }
}
