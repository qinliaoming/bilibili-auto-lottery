package org.carcinus.tools.api;

import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.bean.response.dynamic.DynamicCard;
import org.carcinus.tools.bean.response.lottery.LotteryResult;
import org.carcinus.tools.bean.response.relation.RelationModifyActionType;
import org.carcinus.tools.context.GlobalContext;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AutoLotteryApi {

    public static boolean login(GlobalContext context) throws Exception {
        return LoginApi.login(context);
    }

    public static int getLotteryTagId(GlobalContext context) throws Exception {
        int lotteryTagId = RelationApi.getLotteryTagId();
        if (lotteryTagId == -1) {
            lotteryTagId = RelationApi.createLotteryTag(context);
        }
        return lotteryTagId;
    }

    public static List<ArticleMeta> getArticleMetaByUid(int uid) throws IOException {
        return ArticleApi.getArticleMetaByUid(uid);
    }


    public static List<String> getDynamicIdInArticle(int articleId) throws IOException {
        return ArticleApi.getDynamicIdInArticle(articleId);
    }

    public static List<DynamicCard> getSpaceHistory(int uid) throws IOException {
        return DynamicApi.getSpaceHistory(uid);
    }

    public static DynamicCard getDynamicDetail(String dynamicId) throws IOException {
        return DynamicApi.getDynamicDetail(dynamicId);
    }

    public static DynamicCard getDynamicDetail(int dynamicId) throws IOException {
        return DynamicApi.getDynamicDetail(dynamicId);
    }

    public static LotteryEvent getLotteryEvent(String dynamicId) throws IOException {
        return LotteryApi.getLotteryEvent(dynamicId);
    }

    public static LotteryResult getLotteryResult(String dynamicId) throws IOException {
        return LotteryApi.getLotteryResult(dynamicId);
    }

    public static boolean repostDynamic(GlobalContext context, String dynamicId, String content) {
        try {
            return DynamicApi.repostDynamic(context, dynamicId, content);
        } catch (Exception e) {
            context.setReadyStatus(false);
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addReply(GlobalContext context, String dynamic, String message) {
        try {
            return ReplyApi.addReply(context, dynamic, message);
        } catch (Exception e) {
            context.setReadyStatus(false);
            e.printStackTrace();
        }
        return false;
    }

    public static boolean modifyRelation(GlobalContext context, int uid, RelationModifyActionType actionType) {
        try {
            return RelationApi.modifyRelation(context, uid, actionType);
        } catch (Exception e) {
            context.setReadyStatus(false);
            e.printStackTrace();
        }
        return false;
    }
}
