package com.cmu.smartphone.allavailable.ws.remote;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionControl {
    private static SessionControl session;

    private SharedPreferences sharedPreferences;
    private static final String SESSION_KEY = "session";
    private static final String USER_KEY = "user";

    private SessionControl() {

    }

    public static SessionControl getInstance() {
        if (session == null) {
            session = new SessionControl();
        }
        return session;
    }

    public void createUserSession(Context context, String username) {
        sharedPreferences = context.getSharedPreferences(SESSION_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_KEY, username);
        editor.commit();
    }

    public String getUserSession(Context context) {
        sharedPreferences = context.getSharedPreferences(SESSION_KEY, Context.MODE_PRIVATE);

        return sharedPreferences.getString(USER_KEY, null);
    }
}