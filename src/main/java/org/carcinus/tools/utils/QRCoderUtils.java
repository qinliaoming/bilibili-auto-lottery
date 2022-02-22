package org.carcinus.tools.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class QRCoderUtils {


    private static int QR_CODE_SIZE = 1;
    private static final Hashtable HINTS;
    private static String CHARSET = "UTF-8";

    static {
        HINTS = new Hashtable();

        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        HINTS.put(EncodeHintType.CHARACTER_SET, "utf-8");
        HINTS.put(EncodeHintType.MARGIN, 1);
    }

    /**
     * 生成二維碼
     *
     * @param qrCodeData 二維碼字串
     * @param filePath   檔案路徑
     * @throws WriterException
     * @throws IOException
     */
    public static void createQRCode(String qrCodeData, String filePath)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(qrCodeData.getBytes(CHARSET), CHARSET),
                BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, HINTS);
//        MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath
//                .lastIndexOf('.') + 1), new File(filePath).toPath());
        System.out.println(toAscii(matrix));
    }

    /**
     * 将二维码输出为 ASCII
     *
     * @param bitMatrix
     * @return
     */
    private static String toAscii(BitMatrix bitMatrix) {
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
        return sb.toString();
    }

    /**
     * 讀取二維碼
     *
     * @param filePath 檔案路徑
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotFoundException
     */
    public static String readQRCode(String filePath)
            throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                HINTS);
        return qrCodeResult.getText();
    }
}
