package com.cmu.smartphone.allavailable.exception;

import android.content.Context;
import android.widget.Toast;

/**
 * The exception to handle the network problem
 *
 * @author Xi Wang
 * @version 1.0
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
