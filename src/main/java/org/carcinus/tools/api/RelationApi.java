package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.apache.http.HttpResponse;
import org.carcinus.tools.bean.response.relation.RelationCreateTagResponse;
import org.carcinus.tools.bean.response.relation.RelationTagsResponse;
import org.carcinus.tools.bean.response.relation.Tag;
import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RelationApi {
    private static final String RELATION_TAGS_URL = "https://api.bilibili.com/x/relation/tags";
    private static final String RELATION_TAG_CREATE_URL = "https://api.bilibili.com/x/relation/tag/create";
    private static final Map<String, String> params = new HashMap<>();

    public static int getLotteryTagId() {

        try {
            HttpResponse response = HttpUtils.doGet(RELATION_TAGS_URL);
            String entity = HttpUtils.getEntity(response);
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


    public static int createLotteryTag() {
        try {

            Map<String, String> params = getParams();

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

    private static Map<String, String> getParams() {
        if (params.size() == 0) {
            params.put("tag", "lottery");
            params.put("jsonp", "jsonp");
            params.put("csrf", "bb191d7671fc2d2d94c07757cf3a1161");
        }
        return params;
    }
}
