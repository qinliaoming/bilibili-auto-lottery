package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.apache.http.HttpResponse;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.response.Response;
import org.carcinus.tools.bean.response.relation.RelationCreateTagResponse;
import org.carcinus.tools.bean.response.relation.RelationModifyActionType;
import org.carcinus.tools.bean.response.relation.RelationTagsResponse;
import org.carcinus.tools.bean.response.relation.Tag;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RelationApi {
    private static final String RELATION_TAGS_URL = "https://api.bilibili.com/x/relation/tags";
    private static final String RELATION_TAG_CREATE_URL = "https://api.bilibili.com/x/relation/tag/create";
    private static final String RELATION_TAG_MODIFY_URL = "https://api.bilibili.com/x/relation/modify";
    private static final Map<String, String> params = new HashMap<>();

    public static int getLotteryTagId() {

        try {
            String entity = HttpUtils.doGetEntity(RELATION_TAGS_URL);
            RelationTagsResponse relationTagsResponse = JsonUtils.readValue(entity, RelationTagsResponse.class);

            Tag[] tags = relationTagsResponse.getData();
            Optional<Tag> lotteryTagOp = Arrays.stream(tags)
                    .filter(tag -> tag.getName().equals("lottery"))
                    .findAny();
            if (lotteryTagOp.isPresent()) {
                return lotteryTagOp.get().getTagId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static int createLotteryTag(GlobalContext context) {
        try {

            Map<String, String> params = new HashMap<>();
            String csrf = context.getConf(KeyConstant.BILIBILI_JCT);
            params.put("tag", "lottery");
            params.put("jsonp", "jsonp");
            params.put("csrf", csrf);
            HttpResponse response = HttpUtils.doPost(RELATION_TAG_CREATE_URL, params);

            String entity = HttpUtils.getEntity(response);
            RelationCreateTagResponse relationCreateTagResponse = JsonUtils.readValue(entity, RelationCreateTagResponse.class);
            int tagId = relationCreateTagResponse.getTagId();

            return tagId;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean modifyRelation(GlobalContext context, String uid, RelationModifyActionType actionType) throws Exception {

        String csrf = context.getConf(KeyConstant.BILIBILI_JCT);
        Map<String, String> params = new HashMap<>();

        params.put("fid", uid);
        params.put("act", actionType.getValue());
        params.put("csrf", csrf);

        String entity = HttpUtils.doPostEntity(uid, params);
        Response response = JsonUtils.readValue(entity, Response.class);
        return response.getCode() == 0;
    }
}
