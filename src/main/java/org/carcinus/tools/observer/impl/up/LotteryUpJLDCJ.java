package org.carcinus.tools.observer.impl.up;

import org.carcinus.tools.api.AutoLotteryApi;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.bean.response.dynamic.DynamicCard;
import org.carcinus.tools.context.GlobalContext;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
            try {
                List<String> dynamicIds = getLotteryDynamicIds(articleMetaId);

                dynamicIds.forEach(dynamicId -> {
                    DynamicCard dynamicDetail = AutoLotteryApi.getDynamicDetail(dynamicId);

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<String> getLotteryDynamicIds(int articleId) throws IOException {
        String readArticleUrl = AutoLotteryApi.getReadArticleUrl(articleId);
        return Jsoup
                .connect(readArticleUrl)
                .get()
                .body()
                .getElementsByClass("article-link")
                .stream()
                .map(element -> element.attr("href"))
                .map(link -> link.substring(link.lastIndexOf("/"), link.lastIndexOf("\\?")))
                .collect(Collectors.toList());
    }

}
