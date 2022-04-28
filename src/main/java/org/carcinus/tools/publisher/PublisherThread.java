package org.carcinus.tools.publisher;

import java.util.List;

public class PublisherThread extends Thread{
    private final List<LotteryEventPublisher> publishers;

    public PublisherThread(List<LotteryEventPublisher> publishers) {
        this.publishers = publishers;
    }

    @Override
    public void run() {
        try {
            while (true){
                publishers.forEach(LotteryEventPublisher::update);
                Thread.sleep(43200000);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
