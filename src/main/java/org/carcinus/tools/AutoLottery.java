package org.carcinus.tools;

import lombok.extern.slf4j.Slf4j;
import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.context.GlobalContext;

import java.util.List;

@Slf4j
public class AutoLottery {


    public static void main(String[] args) {

        GlobalContext globalContext = GlobalContext.newBuilder()
                .getOrCreate();
        AutoLotteryApplication autoLotteryApplication = new AutoLotteryApplication(globalContext);
        try {
            while (true) {
                //判断是否就绪
                boolean readyStatus = globalContext.isReadyStatus();
                log.info("readyStatus --- {}", readyStatus);
                if (readyStatus) {
                    autoLotteryApplication.start();
                } else {
                    //开始引导前置条件
                    log.info("start bootstrap...");
                    bootstrap(globalContext);
                }
            }
        } catch (Exception e) {
            log.error("catch Exception: ", e);
        }
    }

    private static void bootstrap(GlobalContext context) {
        boolean isLoginSuccess = AutoLotteryApi.login(context);
        if (isLoginSuccess) {
            int lotteryTagId = AutoLotteryApi.getLotteryTagId();
            if (lotteryTagId != -1) {
                context.setConf("carcinus.auto.lottery.tag.id", String.valueOf(lotteryTagId));
                context.setReadyStatus(true);
                return;
            }
        }
        context.setReadyStatus(false);
    }
}
