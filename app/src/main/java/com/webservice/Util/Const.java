package com.webservice.Util;

import java.util.Random;

/**
 * Created by Pooya on 7/5/2017.
 */

public class Const {
    public static String RandURL = "&refId=" + String.valueOf(new Random().nextDouble()).replace("0.", "");
    public static String Register = "https://webservices.localtunnel.me/Register";
    public static String Login = "https://webservices.localtunnel.me/Login";
}
