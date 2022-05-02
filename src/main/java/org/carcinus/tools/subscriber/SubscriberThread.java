package org.carcinus.tools.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.EmailUtils;
import org.carcinus.tools.utils.LotteryCheckUtils;

import java.util.PriorityQueue;

@Slf4j
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
                if (event == null) {
                    log.info("events is empty, sleep 1h");
                    Thread.sleep(1000 * 60 * 60);
                } else if (!event.isOpen()) {
                    events.offer(event);
                    long lotteryTime = event.getLotteryTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    long sleepTime = lotteryTime - (currentTimeMillis / 1000);
                    log.info("first event no open, sleep {}s", sleepTime);
                    Thread.sleep(sleepTime);
                } else {
                    LotteryCheckUtils.check(context, event);
                }
            }
        } catch (Exception e) {
            context.setReadyStatus(false);
            EmailUtils.sendEmail(context, e.getMessage());
            e.printStackTrace();
        }
    }
}
