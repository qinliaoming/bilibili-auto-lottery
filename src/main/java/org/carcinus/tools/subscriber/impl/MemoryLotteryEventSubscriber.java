package org.carcinus.tools.subscriber.impl;

import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.relation.RelationModifyActionType;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.subscriber.LotteryEventSubscriber;

import java.util.List;
import java.util.PriorityQueue;

public class MemoryLotteryEventSubscriber implements LotteryEventSubscriber {

    private final PriorityQueue<LotteryEvent> eventPriorityQueue;
    private final GlobalContext context;

    public MemoryLotteryEventSubscriber(GlobalContext context, PriorityQueue<LotteryEvent> events) {
        this.context = context;
        this.eventPriorityQueue = events;
    }

    @Override
    public void callback(List<LotteryEvent> events) {
        long currentTimeMillis = System.currentTimeMillis();
        events.stream()
                .filter(event -> {
                    boolean isOpen = event.isOpen(currentTimeMillis);
                    boolean isContains = eventPriorityQueue.contains(event);
                    return isOpen && isContains;
                })
                .forEach(event -> {
                    boolean isParticipate = participate(event);
                    if (isParticipate) eventPriorityQueue.offer(event);
                });
    }

    /**
     * 参与 抽奖
     */

    private boolean participate(LotteryEvent event) {
        String dynamicId = String.valueOf(event.getDynamicId());
        boolean isFlow = AutoLotteryApi.modifyRelation(context, dynamicId, RelationModifyActionType.FOLLOW);
        String content = context.getConf(KeyConstant.REPOST_DYNAMIC_CONTENT, "get it!");
        boolean isRepostDynamic = AutoLotteryApi.repostDynamic(context, dynamicId, content);
        boolean isReply = AutoLotteryApi.addReply(context, dynamicId, content);
        return isFlow && isRepostDynamic && isReply;
    }
}
