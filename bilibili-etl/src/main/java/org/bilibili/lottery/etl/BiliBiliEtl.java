package org.bilibili.lottery.etl;


import org.bilibili.lottery.api.BiliBiliApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class BiliBiliEtl {

    private static Logger logger = LoggerFactory.getLogger(BiliBiliEtl.class);

    public static void main(String[] args) {
        logger.info("args --- {}", Arrays.toString(args));
        for (String arg : args) {
            try {
                int uid = Integer.parseInt(arg);
                execute(uid);
            } catch (Exception e) {
                logger.error("catch Exception: ", e);
            }
        }
    }

    private static void execute(int uid) {
        logger.info("uid --- {}", uid);
        List<String> lotteryDynamicIds = BiliBiliApi.getLotteryDynamicIds(uid);

    }

}
