package com.cmu.smartphone.allavailable.exception;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangxi on 12/3/15.
 */
public class NetworkException extends Exception {

    String message = "Network Problem";

    /**
     * Fix the exception
     *
     * @param context the Activity context
     */
    public void fix(Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
