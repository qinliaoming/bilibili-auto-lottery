package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.carcinus.tools.bean.response.dynamic.DynamicCard;
import org.carcinus.tools.bean.response.dynamic.DynamicDetailResponse;
import org.carcinus.tools.bean.response.dynamic.SpaceHistoryDynamicResponse;
import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;
import java.util.List;

public class DynamicApi {

    private final static String SPACE_HISTORY_URL_FORMATTED = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=%d";
    private final static String GET_DYNAMIC_DETAIL_URL = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/get_dynamic_detail?dynamic_id=%d";

    public static List<DynamicCard> getSpaceHistory(int uid) {

        try {
            String spaceHistoryUrl = String.format(SPACE_HISTORY_URL_FORMATTED, uid);
            String entity = HttpUtils.doGetEntity(spaceHistoryUrl);
            SpaceHistoryDynamicResponse response = JsonUtils.readValue(entity, SpaceHistoryDynamicResponse.class);
            return response.getCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DynamicCard getDynamicDetail(String dynamicId) {
        return getDynamicDetail(Integer.parseInt(dynamicId));
    }

    private static DynamicCard getDynamicDetail(int dynamicId) {

        try {
            String getDynamicDetailUrl = String.format(GET_DYNAMIC_DETAIL_URL, dynamicId);
            String entity = HttpUtils.doGetEntity(getDynamicDetailUrl);
            DynamicDetailResponse response = JsonUtils.readValue(entity, DynamicDetailResponse.class);
            return response.getCard();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
