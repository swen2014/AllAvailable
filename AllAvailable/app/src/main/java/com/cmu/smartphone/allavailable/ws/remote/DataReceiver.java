package com.cmu.smartphone.allavailable.ws.remote;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Receive the data to String
 *
 * @author Xi Wang
 * @version 1.0
 */
public class DataReceiver {

    /**
     * Get json string
     *
     * @param inputStream
     * @return
     */
    public static String ChangeInputStream(InputStream inputStream) {
        String jsonString = "";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];

        try {
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            jsonString = new String(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
