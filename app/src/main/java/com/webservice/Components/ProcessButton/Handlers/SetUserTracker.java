package com.webservice.Components.ProcessButton.Handlers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.webservice.Components.ProcessButton.ProcessButton;
import com.webservice.Dialogs.CustomDialog;
import com.webservice.R;
import com.webservice.Util.Const;
import com.webservice.Util.Json;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Pooya on 7/6/2017.
 */

public class SetUserTracker {

    private Handler handler;
    private Context mContext;
    private int mProgress;
    private String IMEI;
    private ProcessButton button;
    private String[] strInput;
    private int SerialNumber_index = 0;
    private int ActivationCode_index = 1;
    private int Title_index = 2;

    public void start(final ProcessButton button, String[] strInput, String IMEI, Context mContext) {
        this.mContext = mContext;
        this.IMEI = IMEI;
        this.button = button;
        this.strInput = strInput;
        SetUserTracker();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                handler.postDelayed(this, generateDelay());
                if(mProgress >= 100)
                    mProgress = 1;
            }
        }, generateDelay());
    }

    private int generateDelay() {
        return 750;
    }

    public void stop()
    {
        if(handler != null)
        {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void SetUserTracker() {
        new SetUserTrackerTask().execute();
    }

    class SetUserTrackerTask extends AsyncTask<Void, Void, String> {
        private String res = "";

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient hc = new DefaultHttpClient();
            String message;
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String REGISTERDATE = df.format(c.getTime());

            HttpPost p = new HttpPost(Const.SetUserTracker);
            JSONObject object = new JSONObject();
            try {
                object.put("IMEI", IMEI);
                object.put("SerialNumber", strInput[SerialNumber_index]);
                object.put("ActivationCode", strInput[ActivationCode_index]);
                object.put("Title", strInput[Title_index]);
                object.put("REGISTERDATE", REGISTERDATE);
            } catch (Exception ex) {

            }

            try {
                message = object.toString();
                p.setEntity(new StringEntity(message, "UTF8"));
                p.setHeader("Content-type", "application/json");
                HttpResponse resp = hc.execute(p);
                if (resp != null) {
                    HttpEntity entity = resp.getEntity();
                    if (resp.getStatusLine().getReasonPhrase().equals("OK"))
                    {
                        res = "OK";
                    }
                    else
                    {
                        res = "NaN";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return res;
        }


        @Override
        protected void onPostExecute(String res) {
            if(!res.equals("NaN"))
            {
                stop();
                button.setProgress(0);
                Toast.makeText(mContext, R.string.Success, Toast.LENGTH_SHORT).show();
            }
            else
            {
                CustomDialog customDialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.faild));
                customDialog.SetTitle(mContext.getResources().getString(R.string.faild_title));
                customDialog.SetButtonText(mContext.getResources().getString(R.string.dissmiss));
                customDialog.show();
            }
        }
    }

}
