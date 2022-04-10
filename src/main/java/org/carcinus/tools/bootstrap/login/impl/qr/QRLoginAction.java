package org.carcinus.tools.bootstrap.login.impl.qr;

import carcinus.code.common.utils.JsonUtils;
import carcinus.code.common.utils.QRCoderUtils;
import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.carcinus.tools.bean.response.Response;
import org.carcinus.tools.bootstrap.login.LoginAction;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class QRLoginAction implements LoginAction {

    private final static String GET_LOGIN_URL = "http://passport.bilibili.com/qrcode/getLoginUrl";
    private final static String OAUTH_URL = "http://passport.bilibili.com/qrcode/getLoginInfo";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ApplyQRCodeResponse applyQRCode() {

        try {
            String responseMessage = HttpUtils.doGet(GET_LOGIN_URL, null);
            logger.info("responseMessage --- {}", responseMessage);
            return JsonUtils.readValue(responseMessage, ApplyQRCodeResponse.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean login(GlobalContext context) {

        ApplyQRCodeResponse applyQRCodeResponse = applyQRCode();

        Preconditions.checkNotNull(applyQRCodeResponse);

        String qrUrl = applyQRCodeResponse.getUrl();
        String oauthKey = applyQRCodeResponse.getOauthKey();
        try {
            Preconditions.checkNotNull(qrUrl);
            logger.info("*************Please scan the code*******************");
            QRCoderUtils.parseQRCodeToConsole(qrUrl);
            logger.info("*************Please scan the code*******************");
            loopOauth(context, oauthKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

//        context.setConf(KeyConstant.OAUTH_KEY, oauthKey);

        return true;
    }

    @SneakyThrows
    public void countdown(long time) {
        while (time-- > 0) {
            System.out.print("countdown - " + time);
            Thread.sleep(1000);
            System.out.print("\r");
        }
    }

    private void loopOauth(GlobalContext context, String oauthKey) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("oauthKey", oauthKey);
        int retry = 10;
        while (retry-- > 0) {
            countdown(10);
            if (loop0(context, URI.create(OAUTH_URL), params))break;
        }
    }

    private boolean loop0(GlobalContext context, URI oauthUrl, HashMap<String, String> params) {
        OauthResponse oauthResponse = null;
        try {
            HttpResponse response = HttpUtils.doPostResponse(oauthUrl, params);
            Preconditions.checkNotNull(response);
            oauthResponse = JsonUtils.readValue(EntityUtils.toString(response.getEntity(), "UTF-8"), OauthResponse.class);
            if (oauthResponse.getStatus()){
                logger.info("login successful --- {}", oauthResponse.getTs());
//                extractCookieHeaders(context, response);
                return true;
            }else {
                logger.info(oauthResponse.message);
            }
        } catch (IOException e) {
            logger.warn("", e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    private void extractCookieHeaders(GlobalContext context, HttpResponse response) {

        BasicCookieStore cookieStore = new BasicCookieStore();
        List<Header> cookieHeaders = Arrays.stream(response.getHeaders("Set-Cookie"))
                .map(header -> new BasicHeader("Cookie", header.getValue()))
                .collect(Collectors.toList());
    }
    public static class OauthResponse extends Response {
        private String message;
        private Data data;

        public String getUrl() {
            return this.data.url;
        }
        public static class Data {
            /**
             * 游戏分站跨域登录url
             * 游戏分站跨域登录url与cookie的值一一对应，可用于不方便设置cookie的场合提取使用
             */
            private String url;
        }
    }


    public static class ApplyQRCodeResponse extends Response {
        private Data data;

        public String getUrl() {
            return data.url;
        }

        public String getOauthKey() {
            return data.oauthKey;
        }

        public static class Data {
            /**
             * 二维码内容url	恒为87字符
             */
            private String url;
            /**
             * 扫码登录秘钥	恒为32字符
             */
            private String oauthKey;
        }
    }
}
