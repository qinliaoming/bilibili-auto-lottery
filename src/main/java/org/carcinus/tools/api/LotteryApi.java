package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.lottery.NoticeLotteryData;
import org.carcinus.tools.bean.response.lottery.NoticeLotteryResponse;
import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;

public class LotteryApi {
    private static final String NOTICE_LOTTERY_URL_FORMATTED =
            "https://api.vc.bilibili.com/lottery_svr/v1/lottery_svr/lottery_notice?dynamic_id=%s";


    public static LotteryEvent getNoticeLottery(String dynamic) throws IOException {
        String noticeLotteryUrl = String.format(NOTICE_LOTTERY_URL_FORMATTED, dynamic);
        String entity = HttpUtils.doGetEntity(noticeLotteryUrl);
        NoticeLotteryResponse noticeLotteryResponse = JsonUtils.readValue(entity, NoticeLotteryResponse.class);
        NoticeLotteryData data = noticeLotteryResponse.getData();

        return null;
    }
}
