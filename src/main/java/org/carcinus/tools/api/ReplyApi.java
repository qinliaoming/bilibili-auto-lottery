package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.response.Response;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class ReplyApi {
    private static final String ADD_REPLY_URL = "https://api.bilibili.com/x/v2/reply/add";
    private static Map<String, String> addReplyParams;

    public static boolean addReply(GlobalContext context, String dynamicId, String message) throws Exception {
        if(addReplyParams == null) {
            addReplyParams = new HashMap<>();
            addReplyParams.put("csrf", context.getConf(KeyConstant.BILIBILI_JCT));
            addReplyParams.put("type", "17");
        }
        addReplyParams.put("oid", dynamicId);
        addReplyParams.put("message", message);
        String entity = HttpUtils.doPostEntity(ADD_REPLY_URL, addReplyParams);
        Response response = JsonUtils.readValue(entity, Response.class);
        return response.getCode() == 0;
    }
}
