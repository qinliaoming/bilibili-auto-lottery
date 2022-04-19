package org.carcinus.tools.observer;

import org.carcinus.tools.context.GlobalContext;

/**
 * 抽奖事件被观察者接口
 */
public interface LotteryEventObservable {

    void addObserver(LotteryEventObserver observer);

    void removeObserver(LotteryEventObserver observer);

    void update(GlobalContext context);
}