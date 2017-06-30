package com.webservice.Util;

/**
 * Created by Pooya on 6/30/2017.
 */

public class Users {

    private static Users singleton;
    private String userName;

    private Users() {}

    public static synchronized Users getInstance()
    {
        if(singleton == null)
        {
            singleton = new Users();
        }
        return singleton;
    }

    public String getUserName() {
        return userName;
    }
}
