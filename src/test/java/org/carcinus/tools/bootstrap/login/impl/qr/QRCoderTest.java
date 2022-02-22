package org.carcinus.tools.bootstrap.login.impl.qr;

import lombok.SneakyThrows;
import org.carcinus.tools.utils.QRCoderUtils;
import org.junit.jupiter.api.Test;

class QRCoderTest {

    private static String path = "test.png";
    @SneakyThrows
    @Test
    void testWrite() {
        QRCoderUtils.createQRCode("https://passport.bilibili.com/qrcode/h5/login?oauthKey=2b057b96eacc1a32890bbfa6b6f2e83d", path);
    }

    @SneakyThrows
    @Test
    void testRead() {
        String qrCode = QRCoderUtils.readQRCode(path);
        System.out.println(qrCode);
    }
}