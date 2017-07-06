package com.webservice.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

import com.webservice.ClassType.X_Datatable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Pooya on 6/30/2017.
 */

public class Users {
    public enum UserEnum {Name, Family, Mobile, Email, Password_1, Password_2};
    private static final String USER_INFO = "USER_INFO";
    private final Context mContext;

    public Users(Context mContext) {
        this.mContext = mContext;
    }

    public String getIMEI() {
        return new X_Datatable(this.mContext).getIMEI(mContext);
    }

    public void setRegisterBefore() {
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences(USER_INFO, MODE_PRIVATE).edit();
        editor.putBoolean("setRegisterBefore", true);
        editor.commit();
    }

    public boolean getRegisterBefore() {
        SharedPreferences prefs = this.mContext.getSharedPreferences(USER_INFO, MODE_PRIVATE);
        return prefs.getBoolean("setRegisterBefore", false);
    }

    public void setLogin() {
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences(USER_INFO, MODE_PRIVATE).edit();
        editor.putBoolean("setLogin", true);
        editor.commit();
    }

    public boolean getLogin() {
        SharedPreferences prefs = this.mContext.getSharedPreferences(USER_INFO, MODE_PRIVATE);
        return prefs.getBoolean("setLogin", false);
    }

}
