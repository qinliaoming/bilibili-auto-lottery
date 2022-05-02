package org.carcinus.tools.publisher;

import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.EmailUtils;

import java.util.List;

public class PublisherThread extends Thread{
    private final List<LotteryEventPublisher> publishers;
    private final GlobalContext context;
    public PublisherThread(GlobalContext context, List<LotteryEventPublisher> publishers) {
        this.context = context;
        this.publishers = publishers;
    }

    @Override
    public void run() {
        try {
            while (context.getReadyStatus()){
                for (LotteryEventPublisher publisher : publishers) {
                    publisher.update();
                }
                Thread.sleep(43200000);
            }
        }catch (Exception e) {
            context.setReadyStatus(false);
            EmailUtils.sendEmail(context, e.getMessage());
            e.printStackTrace();
        }
    }
}
