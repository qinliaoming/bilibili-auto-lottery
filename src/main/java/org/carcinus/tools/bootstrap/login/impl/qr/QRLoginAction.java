package org.carcinus.tools.bootstrap.login.impl.qr;

import com.google.common.base.Preconditions;
import lombok.SneakyThrows;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.response.Response;
import org.carcinus.tools.bootstrap.login.LoginAction;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.log.Logging;
import org.carcinus.tools.utils.HttpUtils;
import org.carcinus.tools.utils.JsonUtils;
import org.carcinus.tools.utils.QRCoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

public class QRLoginAction implements LoginAction, Logging {

    private final static String LOGIN_URL = "http://passport.bilibili.com/qrcode/getLoginUrl";
    private final static String OAUTH_URL = "http://passport.bilibili.com/qrcode/getLoginInfo";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ApplyQRCodeResponse applyQRCode() {

        try {
            String responseMessage = HttpUtils.doGet(URI.create(LOGIN_URL));
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

        String qrUrl = applyQRCodeResponse.getUrl();
        String oauthKey = applyQRCodeResponse.getOauthKey();
        String qrPath = context.getConf("auto.lottery.login.qr.path") + "/" + System.currentTimeMillis() + ".png";
//        QRCoder.encode(qrUrl);
        try {
            Preconditions.checkNotNull(qrUrl);
            logger.info("*************请扫码*******************");
            QRCoderUtils.createQRCode(qrUrl, qrPath);
            logger.info("*************请扫码*******************");
            loopOauth(oauthKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        context.setConf(KeyConstant.OAUTH_KEY, oauthKey);

        return true;
    }

    @SneakyThrows
    public void countdown(long time) {
        while (time-- > 0) {
            System.out.print("倒计时 - " + time);
            Thread.sleep(1000);
            System.out.print("\r");
        }
    }

    private void loopOauth(String oauthKey) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("oauthKey", oauthKey);
        int retry = 10;
        while (retry-- > 0) {
            countdown(10);
            if (loop0(URI.create(OAUTH_URL), params))break;
        }
    }

    private boolean loop0(URI oauthUrl, HashMap<String, String> params) {
        String responseStr = null;
        OauthResponse oauthResponse = null;
        try {
            responseStr = HttpUtils.doPost(oauthUrl, params);
            Preconditions.checkNotNull(responseStr);
            oauthResponse = JsonUtils.readValue(responseStr, OauthResponse.class);
            if (oauthResponse.getStatus()){
                logger.info("登录成功 --- {}", oauthResponse.getTs());
                return true;
            }else {
                logger.info(oauthResponse.message);
            }
        } catch (IOException e) {
            logger.warn(responseStr);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


    public static class OauthResponse extends Response {
        private String message;
        private Data data;


        public static class Data {
            /**
             * 游戏分站跨域登录url
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
