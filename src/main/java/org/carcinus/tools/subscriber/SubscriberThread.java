package org.carcinus.tools.subscriber;

import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.LotteryCheckUtils;

import java.util.PriorityQueue;

public class SubscriberThread extends Thread {

    private final PriorityQueue<LotteryEvent> events;
    private final GlobalContext context;

    public SubscriberThread(GlobalContext context, PriorityQueue<LotteryEvent> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public void run() {
        try {
            while (context.getReadyStatus()) {
                LotteryEvent event = events.poll();
                if (event == null || !event.isOpen()) {
                    events.offer(event);
                    Thread.sleep(43200000);
                } else {
                    LotteryCheckUtils.check(context, event);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
