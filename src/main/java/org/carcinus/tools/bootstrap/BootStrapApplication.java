package org.carcinus.tools.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.carcinus.tools.bootstrap.login.LoginAction;
import org.carcinus.tools.bootstrap.login.LoginActionFactory;
import org.carcinus.tools.bootstrap.watch.WatchListOperator;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.exception.EnumConstantNotFountException;

import java.util.List;

@Slf4j
public class BootStrapApplication{

    private static WatchListOperator watchListOperator;

    static {
        watchListOperator = new WatchListOperator();
    }

    public static void bootstrap(GlobalContext context) {
        boolean isLoginSuccess = login(context);
        if (isLoginSuccess) {
            log.info("login success get watch groups");
            List<String> watchGroups = watchListOperator.getWatchGroups(context);
            log.info("*****************************");
            watchGroups.forEach(System.out::println);
            log.info("*****************************");
            boolean isInCloudLotteryWatchGroups = watchListOperator.judgeInCloudLotteryWatchGroups(watchGroups);
            log.info("isInCloudLotteryWatchGroups --- {}", isInCloudLotteryWatchGroups);
            if (!isInCloudLotteryWatchGroups) {
                watchListOperator.createLotteryWatchGroups(context);
            }
            context.setReadyStatus(true);
            log.info("*******bootstrap success*******");
        }else {
            log.error("登录失败!");
        }
    }

    private static boolean login(GlobalContext context) {
        String loginActionTypeStr = context.getConf("auto.lottery.login.action.type");
        log.info("loginActionTypeStr --- {}", loginActionTypeStr);
        try {
            LoginAction loginAction = LoginActionFactory.getInstance(loginActionTypeStr);
            return loginAction.login(context);
        } catch (EnumConstantNotFountException e) {
            log.info(e.getMessage(), e);
            return false;
        }
    }
}
