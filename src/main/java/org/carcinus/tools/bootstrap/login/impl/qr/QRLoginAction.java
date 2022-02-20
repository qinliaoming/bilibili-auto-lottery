package org.carcinus.tools.bootstrap.login.impl.qr;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.carcinus.tools.bean.response.Response;
import org.carcinus.tools.bootstrap.login.LoginAction;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.log.Logging;
import org.carcinus.tools.utils.HttpUtils;
import org.carcinus.tools.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class QRLoginAction implements LoginAction, Logging {

    private final static String loginUrl = "http://passport.bilibili.com/qrcode/getLoginUrl";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ApplyQRCodeResponse applyQRCode() {

        try {
            String responseMessage = HttpUtils.doGet(URI.create(loginUrl));
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

        logger.info("请扫码: {}", applyQRCodeResponse.getUrl());


        return false;
    }


    /**
     *
     */
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
