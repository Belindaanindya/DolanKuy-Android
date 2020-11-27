package com.example.dolankuyandroid.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {


    static final String KEY_USERNAME = "false";
    static final String KEY_STATUS = "false";
    static final String KEY_PASSWORD = "";
    static final String KEY_TOKEN = "";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setPasswordUser(Context context, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    public static String getPasswordUser(Context context){
        return getSharedPreference(context).getString(KEY_PASSWORD, "");
    }

    public static void setLoggedInUser(Context context, String username){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME,"");
    }


    public static void setStatus(Context context, String status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_STATUS, status);
        editor.apply();
    }

    public static String getStatus(Context context){
        return getSharedPreference(context).getString(KEY_STATUS,"false");
    }

    public static void setKeyToken(Context context, String token){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public static String getKeyToken(Context context){
        return getSharedPreference(context).getString(KEY_TOKEN,"");
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME);
        editor.remove(KEY_STATUS);
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_PASSWORD);
        editor.apply();
    }
}
