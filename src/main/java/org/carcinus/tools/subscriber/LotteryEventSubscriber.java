package org.carcinus.tools.subscriber;

import org.carcinus.tools.bean.lottery.LotteryEvent;

import java.util.List;


/**
 * 抽奖事件观察者接口
 */
public interface LotteryEventSubscriber {

     void callback(List<LotteryEvent> events);

}
