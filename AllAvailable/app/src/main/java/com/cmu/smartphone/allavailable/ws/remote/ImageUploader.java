package com.cmu.smartphone.allavailable.ws.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;

/**
 * Created by wangxi on 11/13/15.
 */
public class ImageUploader {

    public static final String RESULT_TYPE = "URI";

    private Context context;

    public ImageUploader(Context context) {
        this.context = context;
    }

    public void uploadImage(Bitmap bitmap, String url, String eventName) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();

            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);

            RequestParams params = new RequestParams();
            params.put("action", "upload");
            params.put("name", eventName);
            params.put("photo", photo);

            AsyncHttpClient client = new AsyncHttpClient();
            Log.v("photo", url);
            client.post(url, params, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    try {
                        if (statusCode != 200) {
                            Toast.makeText(context,
                                    "Network Problem，Error Code:" + statusCode, Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context,
                            "Network Problem, Error Code " + statusCode, Toast.LENGTH_SHORT).show();
                }


            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
