package com.webservice.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Pooya on 6/30/2017.
 */

public class Users {

    private static final String USER_INFO = "USER_INFO";
    private final Context mContext;
    private String name;
    private String family;
    private String mobile;
    private String email;
    private String password;

    public Users(Context mContext) {
        this.mContext = mContext;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public enum UserEnum {Name, Family, Mobile, Email, Password_1, Password_2};

    public void SaveUserLocal(String[] userInfo)
    {
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences(USER_INFO, MODE_PRIVATE).edit();
        editor.putString("Name", userInfo[Users.UserEnum.Name.ordinal()]);
        editor.putString("Family", userInfo[Users.UserEnum.Family.ordinal()]);
        editor.putString("Mobile", userInfo[Users.UserEnum.Mobile.ordinal()]);
        editor.putString("Email", userInfo[Users.UserEnum.Email.ordinal()]);
        editor.putString("Password", userInfo[Users.UserEnum.Password_1.ordinal()]);
        editor.commit();
    }

    public String[] ReadUserLocal()
    {
        String[] userInfo = new String[5];
        SharedPreferences prefs = this.mContext.getSharedPreferences(USER_INFO, MODE_PRIVATE);
        String restoredText = prefs.getString("Name", null);
        if (restoredText != null) {
            userInfo[Users.UserEnum.Name.ordinal()] = prefs.getString("Name", "NaN");
            userInfo[Users.UserEnum.Family.ordinal()] = prefs.getString("Family", "NaN");
            userInfo[Users.UserEnum.Mobile.ordinal()] = prefs.getString("Mobile", "NaN");
            userInfo[Users.UserEnum.Email.ordinal()] = prefs.getString("Email", "NaN");
            userInfo[UserEnum.Password_1.ordinal()] = prefs.getString("Password", "NaN");
        }

        return userInfo;
    }

}
