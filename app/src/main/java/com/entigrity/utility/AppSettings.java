package com.entigrity.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.entigrity.R;


public class AppSettings {

    public static SharedPreferences mPrefs;
    public static Editor prefsEditor;


    //login token
    public static void set_login_token(Context context, String login_token) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString(context.getResources().getString(R.string.str_token), login_token);
        prefsEditor.commit();
    }

    public static String get_login_token(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString(context.getResources().getString(R.string.str_token), "");

    }


    //fcm token


    public static void set_device_token(Context context, String login_token) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString(context.getResources().getString(R.string.device_token), login_token);
        prefsEditor.commit();
    }

    public static String get_device_token(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString(context.getResources().getString(R.string.device_token), "");

    }


    //device id

    public static void set_device_id(Context context, String device_id) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString(context.getResources().getString(R.string.device_i), device_id);
        prefsEditor.commit();
    }

    public static String get_device_id(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString(context.getResources().getString(R.string.device_i), "");

    }


    public static void removeFromSharedPreferences(Context mContext, String key) {
        if (mContext != null) {
            mPrefs.edit().remove(key).commit();
        }
    }


    //email id

    public static void set_email_id(Context context, String login_token) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString(context.getResources().getString(R.string.str_emailid), login_token);
        prefsEditor.commit();
    }

    public static String get_email_id(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString(context.getResources().getString(R.string.str_emailid), "");

    }

    // set walk rough screen
    public static void set_walkthrough(Context context, boolean walkthrough) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putBoolean(context.getResources().getString(R.string.str_walk_through_screen), walkthrough);
        prefsEditor.commit();

    }

    public static boolean get_walkthrough(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getBoolean(context.getResources().getString(R.string.str_walk_through_screen), false);

    }


}