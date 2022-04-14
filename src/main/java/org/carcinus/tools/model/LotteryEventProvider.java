package org.carcinus.tools.model;

import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.context.GlobalContext;

import java.util.List;


/**
 * 针对不同抽奖集合体拓展不同的方案
 */
public interface LotteryEventProvider {

    List<LotteryEvent> providerLotteryEvent(GlobalContext context) throws Exception;

}
