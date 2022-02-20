package org.carcinus.tools.bootstrap.login.impl.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.SneakyThrows;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

public class QRCoder {


    private static int QR_CODE_SIZE = 100;

    public static void encode(String url) throws WriterException, IOException {
        Hashtable hints = new Hashtable();

        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, hints);

//        BufferedImage image =  convertImage(bitMatrix);
//        FileOutputStream fileOutputStream = new FileOutputStream("test.png");
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, "test", fileOutputStream);
//        byte[] bytes = outputStream.toByteArray();


//        printConsole(bitMatrix);

    }

    private static BufferedImage convertImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }



//    private static void printConsole(BitMatrix bitMatrix) {
//        int width = bitMatrix.getWidth();
//        int height = bitMatrix.getHeight();
//
//        boolean flag = false;
//        int x = 0, y = 0;
//        for (int h = y; h < height; h++) {
//            for (int w = x; w < width; w++) {
//                //ture 为黑色
//                if (bitMatrix.get(w, h)) {
//                    if (!flag) {
//                        x = w;
//                        y = h;
//                    }
//                    flag = true;
//                    System.out.print("\033[31m"+"▇");
//                }else if (flag){
//                    System.out.print("\033[32m"+"*");
//
//                }
//            }
//            if (flag)System.out.println("");
//        }
//    }
    private static void printConsole(BitMatrix bitMatrix){

    }

    @SneakyThrows
    public static void main(String[] args) {
        encode("https://passport.bilibili.com/qrcode/h5/login?oauthKey=2b057b96eacc1a32890bbfa6b6f2e83d");
    }
}
