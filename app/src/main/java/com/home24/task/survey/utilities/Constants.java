package com.home24.task.survey.utilities;

public class Constants
{
    public static final String DEVICE_TYPE_IPHONE  = "iPhone";
    public static final String DEVICE_TYPE_ANDROID = "Android";
    public static int IMAGE_SIZE = 800;

    public static boolean isSandBox = false;
    public static boolean isDebug   = true;

    public static final int RESULT_COUNTRY    = 9000;
    public static final int RESULT_WISHLIST = 10919;

    /**************************************************
     *
     *		 RESULT CODES
     **************************************************/
    public static final String IMAGE_DIRECTORY = "images";
    public static final String AUDIO_DIRECTORY = "audio";


    public static final String TEMP_DIRECTORY = "temp";
    public static final String VIDEO_DIRECTORY = "video";

    /**************************************************
	 *
	 *		 USERDEFAULT USER KEYS
	 *
	 **************************************************/

    public static final String USERDEFAULT_ISLOGGEDIN   = "isLoggedIn";
    public static final String USERDEFAULT_STORE_DATA   = "storeData";
    public static final String USERDEFAULT_USER_DATA    = "userData";
    public static final String USERDEFAULT_WISH_LIST   = "wishList";
    public static final String USERDEFAULT_WISH_LIST_DIAM   = "diamondWishList";
    public static final String USERDEFAULT_REGISTER_COUNTER   = "registerCounter";

    public static final String USERDEFAULT_CLOSEST_STORE  = "hasFoundClosestStore";
    public static final String USERDEFAULT_STORE_ALERTED  = "storeAlerted";

    /**************************************************
     *
     *		 RESPONSE KEYS
     *
     **************************************************/
    public static final String RESPONSE_KEY_ERROR        = "error";
    public static final String RESPONSE_KEY_MESSAGE      = "message";
    public static final String RESPONSE_KEY_STORE        = "store";
    public static final String RESPONSE_KEY_INVALID_KEY  = "invalidKey";
    public static final String RESPONSE_KEY_FOUND_STORE  = "hasFoundClosestStore";
    public static final String RESPONSE_KEY_DATA        = "data";

    public static final String RESPONSE_KEY_USER        = "user";
    public static final String RESPONSE_KEY_MOOD        = "mood";

    /**************************************************
     *
     *
     *
     **************************************************/
    public static final String ACTION_MSG_RECEIVED       = "messageReceived";
    public static final String ACTION_TYPING             = "actionTyping";
    public static final String ACTION_SOCKET_DISCONNECT     = "socketDisconnect";
    public static final String ACTION_MSG_NOTIF             = "messageNotif";

    public static final int PUSH_ID = 900;


    /**************************************************
     *
     *		 URLS
     *
     **************************************************/
    public static final String BASE_URL            = "https://api-mobile.home24.com/api/v1/";
    public static final String MAIN_BASE_URL       = "https://api-mobile.home24.com/api/v1/articles?appDomain=1&locale=de_DE&limit=";
}