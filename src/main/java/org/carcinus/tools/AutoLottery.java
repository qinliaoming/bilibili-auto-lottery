package org.carcinus.tools;

import lombok.extern.slf4j.Slf4j;
import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.EmailUtils;

@Slf4j
public class AutoLottery {


    public static void main(String[] args) {

        GlobalContext globalContext = GlobalContext.newBuilder()
                .getOrCreate();
        AutoLotteryApplication autoLotteryApplication = new AutoLotteryApplication(globalContext);
        try {
            while (true) {
                //判断是否就绪
                boolean readyStatus = globalContext.getReadyStatus();
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
            EmailUtils.sendEmail(globalContext, e.getMessage());
        }
    }

    private static void bootstrap(GlobalContext context) {
        boolean isLoginSuccess = false;
        try {
            isLoginSuccess = AutoLotteryApi.login(context);
            if (isLoginSuccess) {
                int lotteryTagId = AutoLotteryApi.getLotteryTagId(context);
                if (lotteryTagId != -1) {
                    context.setConf("carcinus.auto.lottery.tag.id", String.valueOf(lotteryTagId));
                    context.setReadyStatus(true);
                    return;
                }
            }
        } catch (Exception e) {
            context.setReadyStatus(false);
            e.printStackTrace();
        }
    }
}
