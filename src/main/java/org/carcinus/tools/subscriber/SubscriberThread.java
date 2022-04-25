package org.carcinus.tools.subscriber;

import org.carcinus.tools.bean.lottery.LotteryEvent;

import java.util.PriorityQueue;

public class SubscriberThread extends Thread{

    private final PriorityQueue<LotteryEvent> events;

    public SubscriberThread(PriorityQueue<LotteryEvent> events) {

        this.events = events;
    }

    @Override
    public void run() {
        try {
            while (true){
                LotteryEvent firstEvent = events.peek();
                if (firstEvent != null && firstEvent.isOpen()) {

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
