package com.cmu.smartphone.allavailable.exception;

import android.content.Context;
import android.widget.Toast;

/**
 * The exception to handle the image chosen problem
 *
 * @author Xi Wang
 * @version 1.0
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
