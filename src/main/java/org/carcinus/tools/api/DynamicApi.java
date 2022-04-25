package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.response.Response;
import org.carcinus.tools.bean.response.dynamic.DynamicCard;
import org.carcinus.tools.bean.response.dynamic.DynamicDetailResponse;
import org.carcinus.tools.bean.response.dynamic.SpaceHistoryDynamicResponse;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicApi {

    private final static String SPACE_HISTORY_URL_FORMATTED = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=%d";
    private final static String GET_DYNAMIC_DETAIL_URL = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/get_dynamic_detail?dynamic_id=%s";
    private final static String REPOST_DYNAMIC_URL = "https://api.vc.bilibili.com/dynamic_repost/v1/dynamic_repost/repost";

    private static Map<String, String> repostDynamicParams;

    public static List<DynamicCard> getSpaceHistory(int uid) throws IOException {

        String spaceHistoryUrl = String.format(SPACE_HISTORY_URL_FORMATTED, uid);
        String entity = HttpUtils.doGetEntity(spaceHistoryUrl);
        SpaceHistoryDynamicResponse response = JsonUtils.readValue(entity, SpaceHistoryDynamicResponse.class);
        return response.getCards();

    }

    public static DynamicCard getDynamicDetail(int dynamicId) throws IOException {
        return getDynamicDetail(String.valueOf(dynamicId));
    }

    public static DynamicCard getDynamicDetail(String dynamicId) throws IOException {

        String getDynamicDetailUrl = String.format(GET_DYNAMIC_DETAIL_URL, dynamicId);
        String entity = HttpUtils.doGetEntity(getDynamicDetailUrl);
        DynamicDetailResponse response = JsonUtils.readValue(entity, DynamicDetailResponse.class);
        return response.getCard();

    }

    public static boolean repostDynamic(GlobalContext context, String dynamicId, String content) throws Exception {
        if (repostDynamicParams == null) {
            repostDynamicParams = new HashMap<>();
            String csrf = context.getConf(KeyConstant.BILIBILI_JCT);
            repostDynamicParams.put("uid", context.getConf(KeyConstant.DEDE_USER_ID));
            repostDynamicParams.put("type", "1");
            repostDynamicParams.put("content", content);
            repostDynamicParams.put("csrf_token", csrf);
            repostDynamicParams.put("csrf", csrf);
        }
        repostDynamicParams.put("dynamic_id", dynamicId);
        String entity = HttpUtils.doPostEntity(REPOST_DYNAMIC_URL, repostDynamicParams);
        Response response = JsonUtils.readValue(entity, Response.class);
        return response.getCode() == 0;
    }

}
