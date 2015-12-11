package com.cmu.smartphone.allavailable.ws.remote;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * The tool to download the image
 *
 * @author Xi Wang
 * @version 1.0
 */
public class ImageDownloader {

    /**
     * Read Image Stream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public byte[] readStream(InputStream in) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = in.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        in.close();
        return outputStream.toByteArray();
    }
}
