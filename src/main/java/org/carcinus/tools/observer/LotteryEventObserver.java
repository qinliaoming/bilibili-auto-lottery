package org.carcinus.tools.observer;

import org.carcinus.tools.bean.lottery.LotteryEvent;

import java.util.List;


/**
 * 抽奖事件观察者接口
 */
public interface LotteryEventObserver {

     void callback(List<LotteryEvent> events);

}
