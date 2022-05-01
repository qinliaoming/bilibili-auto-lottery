package org.carcinus.tools.publisher.impl;

import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.publisher.LotteryEventPublisher;
import org.carcinus.tools.subscriber.LotteryEventSubscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleLotteryEventPublisher implements LotteryEventPublisher {

    private int uid;
    private List<LotteryEventSubscriber> subscribers;

    public ArticleLotteryEventPublisher(int uid) {
        this.uid = uid;
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void addSubscriber(LotteryEventSubscriber observer) {
        this.subscribers.add(observer);
    }

    @Override
    public void removeSubscriber(LotteryEventSubscriber observer) {
        this.subscribers.remove(observer);
    }

    @Override
    public void update() throws IOException {
        List<LotteryEvent> events = getLotteryEventByArticle();
        subscribers.forEach(subscriber -> subscriber.callback(events));
    }

    private List<LotteryEvent> getLotteryEventByArticle() throws IOException {
        List<ArticleMeta> articleMetas = AutoLotteryApi.getArticleMetaByUid(this.uid);
        if (articleMetas != null && !articleMetas.isEmpty()) {
            ArticleMeta articleMeta = articleMetas.get(0);
            int articleMetaId = articleMeta.getId();
            List<String> dynamicIds = AutoLotteryApi.getDynamicIdInArticle(articleMetaId);
            List<LotteryEvent> collect = new ArrayList<>();
            for (String dynamicId : dynamicIds) {
                LotteryEvent lotteryEvent = AutoLotteryApi.getLotteryEvent(dynamicId);
                collect.add(lotteryEvent);
            }
            return collect;
        }
        return null;
    }
}
