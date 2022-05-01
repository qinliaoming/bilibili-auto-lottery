package org.carcinus.tools.utils;

import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.lottery.LotteryResult;
import org.carcinus.tools.bean.response.lottery.LotteryResultPerson;
import org.carcinus.tools.context.GlobalContext;

import java.io.IOException;
import java.util.List;

public class LotteryCheckUtils {

    public static void check(GlobalContext context, LotteryEvent event) {
        try {
            int dynamicId = event.getDynamicId();
            int masterId = Integer.parseInt(context.getConf(KeyConstant.DEDE_USER_ID));
            LotteryResult lotteryResult = AutoLotteryApi.getLotteryResult(String.valueOf(dynamicId));
            if (lotteryResult != null) {
                if (checkLotteryResult(masterId, lotteryResult.getFirstPrizeResults())) {
                    EmailUtils.sendEmail(context, buildMessage(event.getDynamicId(), event.getFirstPrize()));
                }
                if (checkLotteryResult(masterId, lotteryResult.getSecondPrizeResults())) {
                    EmailUtils.sendEmail(context, buildMessage(event.getDynamicId(), event.getSecondPrize()));
                }
                if (checkLotteryResult(masterId, lotteryResult.getThirdPrizeResults())) {
                    EmailUtils.sendEmail(context, buildMessage(event.getDynamicId(), event.getThirdPrize()));
                }
            }
        } catch (IOException e) {
            context.setReadyStatus(false);
            e.printStackTrace();
        }
    }

    private static String buildMessage(int dynamicId, String prize) {
        StringBuffer sb = new StringBuffer();
        sb.append("恭喜你中奖了\n");
        sb.append("奖品为：").append(prize);
        sb.append("原始动态链接：").append("https://t.bilibili.com/").append(dynamicId);
        return sb.toString();
    }

    private static boolean checkLotteryResult(int masterId, List<LotteryResultPerson> lotteryResultPeoples) {
        if (lotteryResultPeoples != null && !lotteryResultPeoples.isEmpty()) {
            return lotteryResultPeoples
                    .stream()
                    .anyMatch(lotteryResultPerson -> lotteryResultPerson.getUid() == masterId);
        }
        return false;
    }
}
