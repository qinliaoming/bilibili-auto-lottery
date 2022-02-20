package org.carcinus.tools.utils;

import lombok.SneakyThrows;
import lombok.ToString;
import org.carcinus.tools.bootstrap.login.impl.qr.QRLoginAction;
import org.junit.Test;

public class TestJsonUtils {

    @SneakyThrows
    @Test
    public void testReadValue() {
        String str = "{\"code\":0,\"status\":true,\"ts\":1645164490,\"data\":{\"url\":\"https://passport.bilibili.com/qrcode/h5/login?oauthKey=5238e96e11c881bab463264d1fb64ad2\",\"oauthKey\":\"5238e96e11c881bab463264d1fb64ad2\"}}";

        QRLoginAction.ApplyQRCodeResponse applyQRCodeResponse = JsonUtils.readValue(str, QRLoginAction.ApplyQRCodeResponse.class);
        System.out.println(applyQRCodeResponse);

//        String str = "{\"data\":\"1\"}";
//        Data data = JsonUtils.readValue(str, Data.class);
//        System.out.println(data.data);
    }

    @SneakyThrows
    @Test
    public void testReadValue2() {

//        A ba = new A();
//        ba.t = new B();
//        String value = JsonUtils.writeValue(ba);
        String value = "{\"a\":1,\"t\":{\"b\":2}}";
        System.out.println(value);
        A a = JsonUtils.readValue(value, A.class);
        System.out.println();
    }
    @ToString
    public static class A {
        private int a = 1;
        private B t;

        @ToString
        public static class B{
            private int b = 2;
        }
    }




    @SneakyThrows
    @Test
    public void testWriteValue() {
        Data data = new Data();
        String value = JsonUtils.writeValue(data);
        System.out.println(value);
    }

    @lombok.Data
    public static class Data{
        private String data;

    }

}