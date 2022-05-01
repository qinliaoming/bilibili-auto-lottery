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
    private static Map<String, String> modifyRelationParams;

    public static int getLotteryTagId() throws IOException {

        String entity = HttpUtils.doGetEntity(RELATION_TAGS_URL);
        RelationTagsResponse relationTagsResponse = JsonUtils.readValue(entity, RelationTagsResponse.class);

        Tag[] tags = relationTagsResponse.getData();
        Optional<Tag> lotteryTagOp = Arrays.stream(tags)
                .filter(tag -> tag.getName().equals("lottery"))
                .findAny();
        return lotteryTagOp.map(Tag::getTagId).orElse(-1);
    }


    public static int createLotteryTag(GlobalContext context) throws Exception {

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
    }

    public static boolean modifyRelation(GlobalContext context, String uid, RelationModifyActionType actionType) throws Exception {

        String csrf = context.getConf(KeyConstant.BILIBILI_JCT);

        if (modifyRelationParams == null) modifyRelationParams = new HashMap<>();
        modifyRelationParams.put("fid", uid);
        modifyRelationParams.put("act", actionType.getValue());
        modifyRelationParams.put("csrf", csrf);

        String entity = HttpUtils.doPostEntity(RELATION_TAG_MODIFY_URL, modifyRelationParams);
        Response response = JsonUtils.readValue(entity, Response.class);
        return response.getCode() == 0;
    }
}
