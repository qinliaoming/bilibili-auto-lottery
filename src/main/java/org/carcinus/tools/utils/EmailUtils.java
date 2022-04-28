package org.carcinus.tools.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.carcinus.tools.bean.constant.KeyConstant;
import org.carcinus.tools.context.GlobalContext;

public class EmailUtils {

    public static void sendEmail(GlobalContext context, String content){
        try {
            MultiPartEmail email =  getMultiPartEmail(context);
            String sendTo = context.getConf(KeyConstant.EMAIL_SEND_TO, "qinliaoming@163.com");
            email.addTo(sendTo);
            email.setContent(content, "text/html");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

    private static MultiPartEmail getMultiPartEmail(GlobalContext context) throws EmailException {

        String useName = context.getConf(KeyConstant.EMAIL_USER_NAME);
        String password = context.getConf(KeyConstant.EMAIL_PASSWORD);
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.163.com");
        email.setSmtpPort(465);
        email.setAuthentication(useName, password);
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        email.setFrom(useName);
        email.setSubject("[BILIBILI AUTO LOTTERY REMIND]");
        return email;
    }
}
