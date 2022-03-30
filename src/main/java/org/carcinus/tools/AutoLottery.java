package org.carcinus.tools;

import lombok.extern.slf4j.Slf4j;
import org.carcinus.tools.bootstrap.BootStrapApplication;
import org.carcinus.tools.context.GlobalContext;

@Slf4j
public class AutoLottery {


    public static void main(String[] args) {

        GlobalContext globalContext = GlobalContext.newBuilder()
                .getOrCreate();
        try {
        while (true) {
            //判断是否就绪
            boolean readyStatus = globalContext.isReadyStatus();
            log.info("readyStatus --- {}", readyStatus);
            if (readyStatus) {

            }else {
                //开始引导前置条件
                log.info("start bootstrap...");
                BootStrapApplication.bootstrap(globalContext);
            }
        }
        }catch (Exception e){
            log.error("catch Exception: ", e);
        }
    }
}
