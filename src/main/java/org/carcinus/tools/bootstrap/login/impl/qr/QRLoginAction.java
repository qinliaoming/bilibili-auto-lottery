package org.carcinus.tools.bootstrap.login.impl.qr;

import com.google.common.base.Preconditions;
import com.google.zxing.WriterException;
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
import java.net.URL;
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
        String  qrPath = context.getConf("auto.lottery.login.qr.path") + "/" + System.currentTimeMillis() + ".png";
//        QRCoder.encode(qrUrl);
        try {
            Preconditions.checkNotNull(qrUrl);
            QRCoderUtils.createQRCode(qrUrl, qrPath);

            loopOauth(oauthKey);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        logger.info("请扫码: {}", qrPath);

        context.setConf(KeyConstant.OAUTH_KEY, oauthKey);

        return true;
    }

    private void loopOauth(String oauthKey) throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("oauthKey", oauthKey);
        while (loop0(URI.create(OAUTH_URL), params)) {

        }
    }

    private boolean loop0(URI oauthUrl, HashMap<String, String> params) throws Exception {
        HttpUtils.doPost(oauthUrl, params);
        return false;
    }


//    public static class


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
