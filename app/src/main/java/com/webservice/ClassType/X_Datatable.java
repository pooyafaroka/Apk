package com.webservice.ClassType;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.webservice.Activity.MainActivity;

/**
 * Created by Pooya on 7/6/2017.
 */

public class X_Datatable {

    private Context mContext;

    public X_Datatable(Context mContext) {
        this.mContext = mContext;
    }

    public String getIMEI(Context mContext)
    {
        TelephonyManager telephonyManager = (TelephonyManager)this.mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        return IMEI;
    }

}
