package com.example.dolankuyandroid.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String KEY_STATUS = "false";
    static final String KEY_TOKEN = "";
    static final String KEY_LATITUDE = "0";


    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setKeyLatitude(Context context, String latitude){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_LATITUDE, latitude);
        editor.apply();
    }

    public static String getKeyLatitude(Context context){
        return getSharedPreference(context).getString(KEY_LATITUDE,"0");
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
        editor.remove(KEY_STATUS);
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_LATITUDE);
        editor.apply();
    }
}
