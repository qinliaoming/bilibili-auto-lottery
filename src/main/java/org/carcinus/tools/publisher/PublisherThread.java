package org.carcinus.tools.publisher;

import java.util.List;

public class PublisherThread extends Thread{
    private final List<LotteryEventPublisher> publishers;
    private final long SLEEP_TIME = 43200000;

    public PublisherThread(List<LotteryEventPublisher> publishers) {
        this.publishers = publishers;
    }

    @Override
    public void run() {
        try {
            while (true){
                publishers.forEach(LotteryEventPublisher::update);
                Thread.sleep(SLEEP_TIME);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
