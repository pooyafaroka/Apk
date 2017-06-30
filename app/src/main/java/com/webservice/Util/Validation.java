package com.webservice.Util;

/**
 * Created by Pooya on 4/15/2017.
 */

public class Validation {

    public static boolean IsEmpty(String[] strInput) {
        boolean ret = false;
        for (int i = 0; i < strInput.length; i++)
        {
            if(strInput[i].equals(""))
            {
                ret = true;
                break;
            }
        }
        return ret;
    }
}
