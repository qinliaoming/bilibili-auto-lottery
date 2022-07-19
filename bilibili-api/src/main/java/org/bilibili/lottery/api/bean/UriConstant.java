package org.bilibili.lottery.api.bean;

public class UriConstant {
    // login
    public static final String GET_LOGIN_URI = "http://passport.bilibili.com/qrcode/getLoginUrl";
    public static final String GET_LOGIN_INFO_URI = "http://passport.bilibili.com/qrcode/getLoginInfo";


    //article
    public static final String LIST_ARTICLES_URL_FORMATTED = "https://api.bilibili.com/x/space/article?mid=%d&sort=publish_time";
    public static final String READ_ARTICLE_URL_FORMATTED = "https://www.bilibili.com/read/cv%s";

    // dynamic
    public final static String SPACE_HISTORY_URL_FORMATTED = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/space_history?host_uid=%d";
    public final static String GET_DYNAMIC_DETAIL_URL = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/get_dynamic_detail?dynamic_id=%s";
    public final static String REPOST_DYNAMIC_URL = "https://api.vc.bilibili.com/dynamic_repost/v1/dynamic_repost/repost";

    //lottery
    public static final String NOTICE_LOTTERY_URL_FORMATTED =
            "https://api.vc.bilibili.com/lottery_svr/v1/lottery_svr/lottery_notice?dynamic_id=%s";

    //relation
    public static final String RELATION_TAGS_URL = "https://api.bilibili.com/x/relation/tags";
    public static final String RELATION_TAG_CREATE_URL = "https://api.bilibili.com/x/relation/tag/create";
    public static final String RELATION_TAG_MODIFY_URL = "https://api.bilibili.com/x/relation/modify";

    //reply
    public static final String ADD_REPLY_URL = "https://api.bilibili.com/x/v2/reply/add";
}
