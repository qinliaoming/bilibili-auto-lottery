package org.carcinus.tools.subscriber.impl;

import lombok.extern.slf4j.Slf4j;
import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.relation.RelationModifyActionType;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.subscriber.LotteryEventSubscriber;

import java.util.List;
import java.util.PriorityQueue;

@Slf4j
public class MemoryLotteryEventSubscriber implements LotteryEventSubscriber {

    private final PriorityQueue<LotteryEvent> eventPriorityQueue;
    private final GlobalContext context;

    public MemoryLotteryEventSubscriber(GlobalContext context, PriorityQueue<LotteryEvent> events) {
        this.context = context;
        this.eventPriorityQueue = events;
    }

    @Override
    public void callback(List<LotteryEvent> events) {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        if (events != null) {
            events.stream()
                    .filter(event -> {
                        boolean isOpen = event.isOpen(currentTimeMillis);
                        boolean isContains = eventPriorityQueue.contains(event);
                        return !isOpen && !isContains;
                    })
                    .forEach(event -> {
                        boolean isParticipate = participate(event);;
                        if (isParticipate) {
                            log.info("join lottery event:{}", event.getDynamicId());
                            eventPriorityQueue.offer(event);
                        }
                    });
        }
    }

    /**
     * 参与 抽奖
     */

    private boolean participate(LotteryEvent event) {
        String dynamicId = String.valueOf(event.getDynamicId());
        int uid = event.getUid();
        String content = context.getConf(KeyConstant.REPOST_DYNAMIC_CONTENT, "get it!");
        boolean isFlow = AutoLotteryApi.modifyRelation(context, uid, RelationModifyActionType.FOLLOW);
        boolean isRepostDynamic = AutoLotteryApi.repostDynamic(context, dynamicId, content);
        boolean isReply = AutoLotteryApi.addReply(context, dynamicId, content);
        return isFlow && isRepostDynamic && isReply;
    }
}
