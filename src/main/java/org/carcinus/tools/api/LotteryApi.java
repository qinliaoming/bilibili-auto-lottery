package org.carcinus.tools.api;

import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;

public class LotteryApi {
    private static final String NOTICE_LOTTERY_URL_FORMATTED =
            "https://api.vc.bilibili.com/lottery_svr/v1/lottery_svr/lottery_notice?dynamic_id=%s";


    public static void getNoticeLottery(String dynamic) throws IOException {
        String noticeLotteryUrl = String.format(NOTICE_LOTTERY_URL_FORMATTED, dynamic);
        String entity = HttpUtils.doGetEntity(noticeLotteryUrl);

    }
}
