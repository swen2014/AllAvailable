package com.cmu.smartphone.allavailable.ws.remote;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create the session to hold some session parameters
 *
 * @author Xi Wang
 * @version 1.0
 */
public class SessionControl {
    private static SessionControl session;

    private SharedPreferences sharedPreferences;
    private static final String SESSION_KEY = "session";
    private static final String USER_KEY = "user";
    private static final String IP_KEY = "ip";

    private SessionControl() {

    }

    /**
     * Get the SessionControl instance
     *
     * @return the instance
     */
    public static SessionControl getInstance() {
        if (session == null) {
            session = new SessionControl();
        }
        return session;
    }

    /**
     * Create the user session parameter
     *
     * @param context
     * @param username
     */
    public void createUserSession(Context context, String username) {
        sharedPreferences = context.getSharedPreferences(SESSION_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USER_KEY, username);
        editor.commit();
    }

    /**
     * Get the user name
     *
     * @param context
     * @return
     */
    public String getUserSession(Context context) {
        sharedPreferences = context.getSharedPreferences(SESSION_KEY, Context.MODE_PRIVATE);

        return sharedPreferences.getString(USER_KEY, null);
    }

    /**
     * Create the ip session parameter
     *
     * @param context
     * @param ip
     */
    public void createIpSession(Context context, String ip) {
        sharedPreferences = context.getSharedPreferences(SESSION_KEY, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(IP_KEY, ip);
        editor.commit();
    }

    /**
     * Get the host ip
     *
     * @param context
     * @return the host ip
     */
    public String getHostIp(Context context) {
        sharedPreferences = context.getSharedPreferences(SESSION_KEY, Context.MODE_PRIVATE);

        return sharedPreferences.getString(IP_KEY, null);
    }
}