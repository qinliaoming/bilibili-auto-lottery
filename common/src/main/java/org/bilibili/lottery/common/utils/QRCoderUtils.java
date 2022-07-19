package org.bilibili.lottery.common.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class QRCoderUtils {


    private static final int DEFAULT_QR_CODE_SIZE = 10;
    private static final Hashtable DEFAULT_HINTS;
    private static final String DEFAULT_CHARSET = "UTF-8";

    static {
        DEFAULT_HINTS = new Hashtable();

        DEFAULT_HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        DEFAULT_HINTS.put(EncodeHintType.CHARACTER_SET, "utf-8");
        DEFAULT_HINTS.put(EncodeHintType.MARGIN, 1);
    }


    public static BitMatrix parseQRCode(String qrCodeData, String charset, int qrCodeSize, Hashtable hints)
            throws WriterException, IOException{
        return new MultiFormatWriter().encode(
                new String(qrCodeData.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hints);
    }


    public static void parseQRCodeToConsole(String qrCodeData, String charset, int qrCodeSize, Hashtable hints)
            throws WriterException, IOException {
        BitMatrix bitMatrix = parseQRCode(qrCodeData, charset, qrCodeSize, hints);
        toConsole(bitMatrix);
    }

    public static void parseQRCodeToConsole(String qrCodeData)
            throws WriterException, IOException {
        BitMatrix bitMatrix = parseQRCode(qrCodeData, DEFAULT_CHARSET, DEFAULT_QR_CODE_SIZE, DEFAULT_HINTS);
        toConsole(bitMatrix);
    }

    /**
     * 将二维码输出到Console
     * @param bitMatrix qr data
     */
    private static void toConsole(BitMatrix bitMatrix) {
        StringBuilder sb = new StringBuilder();
        for (int rows = 0; rows < bitMatrix.getHeight(); rows++) {
            for (int cols = 0; cols < bitMatrix.getWidth(); cols++) {
                boolean x = bitMatrix.get(rows, cols);
                if (!x) {
                    // white
                    sb.append("\033[47m  \033[0m");
                } else {
                    sb.append("\033[30m  \033[0;39m");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }


    public static String readQRCode(String filePath)
            throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                DEFAULT_HINTS);
        return qrCodeResult.getText();
    }
}
