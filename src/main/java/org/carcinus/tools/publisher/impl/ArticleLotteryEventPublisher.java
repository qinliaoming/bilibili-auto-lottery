package org.carcinus.tools.publisher.impl;

import lombok.extern.slf4j.Slf4j;
import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.publisher.LotteryEventPublisher;
import org.carcinus.tools.subscriber.LotteryEventSubscriber;

import java.io.IOException;
import java.util.*;

@Slf4j
public class ArticleLotteryEventPublisher implements LotteryEventPublisher {

    private int uid;
    private List<LotteryEventSubscriber> subscribers;
    private final Set<Integer> cacheArticleMetaId;
    public ArticleLotteryEventPublisher(int uid) {
        this.uid = uid;
        this.subscribers = new ArrayList<>();
        this.cacheArticleMetaId = new HashSet<>();
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
            //提前退出
            if (cacheArticleMetaId.contains(articleMetaId)) {
                log.info("articleMetaId({}) already exists !", articleMetaId);
                return null;
            }else {
                cacheArticleMetaId.add(articleMetaId);
                List<String> dynamicIds = AutoLotteryApi.getDynamicIdInArticle(articleMetaId);
                log.info("uid --- {}", uid);
                log.info("articleMetaId --- {}", articleMetaId);
                List<LotteryEvent> events = new ArrayList<>();
                for (String dynamicId : dynamicIds) {
                    LotteryEvent lotteryEvent = AutoLotteryApi.getLotteryEvent(dynamicId);
                    events.add(lotteryEvent);
                }
                log.info("events --- {}", events);
                return events;
            }
        }
        return null;
    }
}
