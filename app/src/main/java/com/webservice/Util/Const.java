package com.webservice.Util;

import java.util.Random;

/**
 * Created by Pooya on 7/5/2017.
 */

public class Const {
    public static String RandURL = "&refId=" + String.valueOf(new Random().nextDouble()).replace("0.", "");
    private static String Base_URL = ;//"https://webservices.localtunnel.me";
    public static String Register = Base_URL + "/Register";
    public static String Login = Base_URL + "/Login";
    public static String GetUserTracker = Base_URL + "/GetUserTracker";
    public static String GetHistoryTracker = Base_URL + "/GetHistoryTracker";
    public static String SetUserTracker = Base_URL + "/SetUserTracker";
}
