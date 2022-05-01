package org.carcinus.tools;

import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.publisher.LotteryEventPublisher;
import org.carcinus.tools.publisher.PublisherThread;
import org.carcinus.tools.publisher.impl.ArticleLotteryEventPublisher;
import org.carcinus.tools.subscriber.LotteryEventSubscriber;
import org.carcinus.tools.subscriber.SubscriberThread;
import org.carcinus.tools.subscriber.impl.MemoryLotteryEventSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class AutoLotteryApplication implements AutoCloseable{
    private final PublisherThread publisherThread;
    private final SubscriberThread subscriberThread;
    private final GlobalContext context;
    public AutoLotteryApplication(GlobalContext context) {
        this.context = context;
        PriorityQueue<LotteryEvent> events = new PriorityQueue<>();
        LotteryEventSubscriber memoryLotteryEventSubscriber = new MemoryLotteryEventSubscriber(context, events);
        subscriberThread = new SubscriberThread(context, events);

        List<LotteryEventPublisher> publishers = new ArrayList<>();
        String[] ids = context.getConf(KeyConstant.LOTTERY_UP_IDS).split(",");
        Arrays.stream(ids)
                .map(Integer::parseInt)
                .map(ArticleLotteryEventPublisher::new)
                .peek(publisher -> publisher.addSubscriber(memoryLotteryEventSubscriber))
                .forEach(publishers::add);
        publisherThread = new PublisherThread(context, publishers);
    }

    public void start()  {
        if (!publisherThread.isAlive()) {
            publisherThread.start();
        }
        if (!subscriberThread.isAlive()){
            subscriberThread.start();
        }
        while (context.getReadyStatus())Thread.yield();
    }

    @Override
    public void close() throws Exception {
    }
}
