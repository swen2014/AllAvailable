package com.cmu.smartphone.allavailable.exception;

import android.content.Context;
import android.widget.Toast;

/**
 * The exception to handle the reservation validation error
 *
 * @author Xi Wang
 * @version 1.0
 */
public class ReservationValidateException extends Exception {

    /**
     * The default exception
     *
     * @param detailMessage the error message
     */
    public ReservationValidateException(String detailMessage) {
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
