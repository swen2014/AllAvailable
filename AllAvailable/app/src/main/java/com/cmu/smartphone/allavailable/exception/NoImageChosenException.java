package com.cmu.smartphone.allavailable.exception;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangxi on 11/23/15.
 */
public class NoImageChosenException extends Exception {

    /**
     * The default exception
     *
     * @param detailMessage the error message
     */
    public NoImageChosenException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Fix the exception
     *
     * @param context the Activity context
     */
    public void fix(Context context) {
        Toast.makeText(context, getMessage(), Toast.LENGTH_LONG).show();
    }
}
