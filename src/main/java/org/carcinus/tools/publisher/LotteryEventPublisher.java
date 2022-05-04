package org.carcinus.tools.publisher;

import org.carcinus.tools.subscriber.LotteryEventSubscriber;

import java.io.IOException;

/**
 * 抽奖事件被观察者接口
 */
public interface LotteryEventPublisher {

    void addSubscriber(LotteryEventSubscriber observer);

    void removeSubscriber(LotteryEventSubscriber observer);

    void update() throws IOException;
}