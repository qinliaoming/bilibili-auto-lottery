package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import carcinus.code.common.utils.QRCoderUtils;
import com.google.zxing.WriterException;
import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.bean.response.login.LoginUrlResponse;
import org.carcinus.tools.bean.response.login.LoginUrlResponse.LoginUrlData;
import org.carcinus.tools.context.GlobalContext;
import org.carcinus.tools.utils.HttpUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginApi {

    private static final String GET_LOGIN_URI = "http://passport.bilibili.com/qrcode/getLoginUrl";
    private static final String GET_LOGIN_INFO_URI = "http://passport.bilibili.com/qrcode/getLoginInfo";

    private static final Map<String, String> params = new HashMap<>();

    public static boolean login(GlobalContext context) throws Exception {
        LoginUrlData qrLoginUrlData = getLoginUrlData();
        if (qrLoginUrlData == null) return false;

        String qrLoginUrlDataUrl = qrLoginUrlData.getUrl();
        System.out.println("*************Please scan the code*******************");
        parseQrCode(context, qrLoginUrlDataUrl);

        String oauthKey = qrLoginUrlData.getOauthKey();
        params.put("oauthKey", oauthKey);

        return loopOauth(context);
    }

    private static boolean loopOauth(GlobalContext context) throws Exception {
        int retry = 3;
        while (retry-- > 0) {
            if (oauthLoginStatus(params, context)) return true;
            countdown(retry * 5);
            retry++;
        }
        return false;
    }

    @SneakyThrows
    public static void countdown(int time) {
        while (time-- > 0) {
            System.out.print("countdown - " + time);
            Thread.sleep(1000);
            System.out.print("\r");
        }
    }

    private static boolean oauthLoginStatus(Map<String, String> params, GlobalContext context) throws Exception {

        HttpResponse response = HttpUtils.doPost(GET_LOGIN_INFO_URI, params);

        String entity = HttpUtils.getEntity(response);

        if (entity.contains("true")) {
            Header[] headers = response.getHeaders("Set-Cookie");
            return parseHeaders(headers, context);
        }
        return false;
    }

    private static boolean parseHeaders(Header[] headers, GlobalContext context) {
        try {
            Arrays.stream(headers)
                    .map(header -> header.getValue().split(";")[0])
                    .forEach(value -> {
                        String[] values = value.split("=");
                        String cookieKey = values[0];
                        String cookieValue = values[1];
                        switch (cookieKey) {
                            case "DedeUserID":
                                context.setConf(KeyConstant.DEDE_USER_ID, cookieValue);
                                break;
                            case "bili_jct":
                                context.setConf(KeyConstant.BILIBILI_JCT, cookieValue);
                                break;
                            default:
                        }
                    });
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void parseQrCode(GlobalContext context, String qrLoginUrlDataUrl) throws IOException, WriterException {
        String showType = context.getConf("carcinus.auto.lottery.login.qr.show.type", "console");
        switch (showType) {
            case "console":
                QRCoderUtils.parseQRCodeToConsole(qrLoginUrlDataUrl);
                break;
            case "img":
                // TODO: add img type
            default:
                System.out.println("please set qr show type");
        }
    }

    private static LoginUrlData getLoginUrlData() throws IOException {
        String entity = HttpUtils.doGetEntity(GET_LOGIN_URI);
        LoginUrlResponse qrLoginUrlResponse = JsonUtils.readValue(entity, LoginUrlResponse.class);
        return qrLoginUrlResponse.getData();
    }
}
