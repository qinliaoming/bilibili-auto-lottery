package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.lottery.LotteryEvent;
import org.carcinus.tools.bean.response.Response;
import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.bean.response.dynamic.DynamicCard;
import org.carcinus.tools.bean.response.relation.RelationModifyActionType;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
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

    public static int getLotteryTagId(GlobalContext context) {
        int lotteryTagId = RelationApi.getLotteryTagId();
        if (lotteryTagId == -1) {
            lotteryTagId = RelationApi.createLotteryTag(context);
        }
        return lotteryTagId;
    }

    public static List<ArticleMeta> getArticleMetaByUid(int uid) {
        try {
            return ArticleApi.getArticleMetaByUid(uid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public static List<String> getDynamicIdInArticle(int articleId) {
        try {
            return ArticleApi.getDynamicIdInArticle(articleId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<DynamicCard> getSpaceHistory(int uid) {
        try {
            return DynamicApi.getSpaceHistory(uid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
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

    public static LotteryEvent getLotteryEvent(String dynamicId) {
        try {
            return LotteryApi.getNoticeLottery(dynamicId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean repostDynamic(GlobalContext context, String dynamicId, String content) {
        try {
            DynamicApi.repostDynamic(context, dynamicId, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addReply(GlobalContext context, String dynamic, String message) {
        try {
            ReplyApi.addReply(context, dynamic, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean modifyRelation(GlobalContext context, String uid, RelationModifyActionType actionType){
        try {
            RelationApi.modifyRelation(context, uid, actionType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
