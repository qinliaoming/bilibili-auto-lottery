package org.carcinus.tools.observer.impl.up;

import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.bean.response.dynamic.DynamicCard;
import org.carcinus.tools.context.GlobalContext;

import java.util.List;

/**
 * https://space.bilibili.com/652666095
 */
public class LotteryUpJLDCJ extends AbstractLotteryUp {

    public LotteryUpJLDCJ(String name, int uid) {
        super("锦鲤大抽奖", 652666095);
    }

    @Override
    public List<LotteryEvent> getLotteryEvents(GlobalContext context) {

        List<ArticleMeta> articleMetas = AutoLotteryApi.getArticleMetaByUid(this.uid);
        if (articleMetas != null && !articleMetas.isEmpty()) {
            ArticleMeta articleMeta = articleMetas.get(0);
            int articleMetaId = articleMeta.getId();
            List<String> dynamicIds = AutoLotteryApi.getReadArticleUrl(articleMetaId);
            dynamicIds.forEach(dynamicId -> {
                DynamicCard dynamicDetail = AutoLotteryApi.getDynamicDetail(dynamicId);

            });
        }
        return null;
    }


}
