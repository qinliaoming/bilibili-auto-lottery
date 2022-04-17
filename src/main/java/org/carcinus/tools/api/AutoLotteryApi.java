package org.carcinus.tools.api;

import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.bean.response.dynamic.DynamicCard;
import org.carcinus.tools.context.GlobalContext;

import java.io.IOException;
import java.util.List;

public class AutoLotteryApi {

    public static boolean login(GlobalContext context) {
        try {
            return LoginApi.login(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getLotteryTagId() {
        int lotteryTagId = RelationApi.getLotteryTagId();
        if (lotteryTagId == -1) {
            lotteryTagId = RelationApi.createLotteryTag();
        }
        return lotteryTagId;
    }

    public static List<ArticleMeta> getArticleMetaByUid(int uid) {
        try {
            return ArticleApi.getArticleMetaByUid(uid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getReadArticleUrl(int articleId) {
        return ArticleApi.getReadArticleUrl(articleId);
    }

    public static List<DynamicCard> getSpaceHistory(int uid) {
        try {
            return DynamicApi.getSpaceHistory(uid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DynamicCard getDynamicDetail(String dynamicId) {
        try {
            return DynamicApi.getDynamicDetail(dynamicId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DynamicCard getDynamicDetail(int dynamicId) {
        try {
            return DynamicApi.getDynamicDetail(dynamicId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
