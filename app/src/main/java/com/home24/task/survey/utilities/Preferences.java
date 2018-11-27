package com.home24.task.survey.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Random;

public class Preferences
{
    private static final String SHARED_PREFRENCES_KEY = "UserSharedPrefs";

    public static void saveSharedPrefValue(Context mContext, String key, String value)
    {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor edit = UserSharedPrefs.edit();
        edit.putString(key, scrambleText(value));
        edit.commit();
    }

    public static void saveBoolSharedPrefValue(Context mContext, String key, boolean value)
    {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor edit = UserSharedPrefs.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void saveIntegerSharedPrefValue(Context mContext, String key, int value)
    {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor edit = UserSharedPrefs.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**********************************************************************************************
     *
     * @param cxt
     * @param key
     * @return
     */
    public static boolean getBoolSharedPrefValue(Context cxt, String key, boolean defaultValue)
    {
        SharedPreferences UserSharedPrefs = cxt.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        return UserSharedPrefs.getBoolean(key, defaultValue);
    }

	public static String getSharedPrefValue(Context mContext, String key)
	{
		SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
		String value = UserSharedPrefs.getString(key, null);
		return unScrambleText(value);
	}

    public static String getSimpleSharedPrefValue(Context mContext, String key)
    {
        SharedPreferences UserSharedPrefs = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        String value = UserSharedPrefs.getString(key, null);
        return value;
    }

    public static SharedPreferences getSharedPref(Context cxt)
    {
        return cxt.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
    }

    public static int getIntSharedPrefValue(Context cxt, String shared_pref_key, int defaultValue)
    {
        SharedPreferences UserSharedPrefs = cxt.getSharedPreferences("UserSharedPrefs", Activity.MODE_PRIVATE);
        return UserSharedPrefs.getInt(shared_pref_key, defaultValue);
    }

    /*******************************************************************************************
     *
     * @param mContext
     * @return
     */
    public static boolean isUserLoggedIn(Context mContext)
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.USERDEFAULT_ISLOGGEDIN, false);
    }

    public static boolean isClosestStoreFound(Context mContext)
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.USERDEFAULT_CLOSEST_STORE, false);
    }

    public static boolean isStoreAlerted(Context mContext)
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.USERDEFAULT_STORE_ALERTED, false);
    }

    public static void saveLoginDefaults(Context mContext, String jsonData)
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.USERDEFAULT_ISLOGGEDIN, true);
        editor.putString(Constants.USERDEFAULT_USER_DATA, scrambleText(jsonData));
        editor.commit();
    }

    public static void saveStoreDefaults(Context mContext, String jsonData)
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFRENCES_KEY, Activity.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USERDEFAULT_STORE_DATA, scrambleText(jsonData));
        editor.commit();
    }

    private static String scrambleText(String text)
    {
        try {
            Random r = new Random();
            String prefix = String.valueOf(r.nextInt(90000) + 10000);

            String suffix = String.valueOf(r.nextInt(90000) + 10000);

            text = prefix + text + suffix;

            byte[] bytes = text.getBytes("UTF-8");
            byte[] newBytes = new byte[bytes.length];

            for (int i = 0; i < bytes.length; i++)
            {
                newBytes[i] = (byte)(bytes[i] - 1);
            }

            return new String(newBytes, "UTF-8");
        }catch (Exception e)
        {
            return text;
        }
    }

    private static String unScrambleText(String text)
    {
        try {
            byte[] bytes = text.getBytes("UTF-8");
            byte[] newBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++)
            {
                newBytes[i] = (byte)(bytes[i] + 1);
            }
            String textVal = new String(newBytes, "UTF-8");

            return textVal.substring(5,textVal.length() - 5);
        }catch (Exception e)
        {
            return text;
        }
    }
}
