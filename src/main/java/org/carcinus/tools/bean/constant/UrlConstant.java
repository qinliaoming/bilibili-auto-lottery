package org.carcinus.tools.bean.constant;

public class UrlConstant {
    /**
     * qr login
     */
    public final static String LOGIN_URL = "https://passport.bilibili.com/qrcode/getLoginUrl";
    public final static String OAUTH_URL = "https://passport.bilibili.com/qrcode/getLoginInfo";

    /**
     * watch groups
     */
    public final String GET_FLOWING_GROUP_URL = "https://api.bilibili.com/x/relation/tags?jsonp=jsonp&callback=__jp3";
    public final String CREATE_FLOWING_GROUP_URL = "https://api.bilibili.com/x/relation/tag/create";

}
