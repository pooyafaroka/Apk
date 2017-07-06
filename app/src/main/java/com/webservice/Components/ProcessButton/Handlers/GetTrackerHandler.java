package com.webservice.Components.ProcessButton.Handlers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.webservice.Activity.LoginActivity;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Pooya on 7/6/2017.
 */
public class GetTrackerHandler {

    private final OnCompleteGetTrackerListener mListener;
    private Handler handler;
    private Context mContext;
    private int mProgress;
    private String IMEI;
    private ProcessButton button;

    public interface OnCompleteGetTrackerListener
    {
        public void onComplete();
    }

    public GetTrackerHandler(OnCompleteGetTrackerListener mListener) {
        this.mListener = mListener;
    }

    public void start(final ProcessButton button, String IMEI, Context mContext) {
        this.mContext = mContext;
        this.IMEI = IMEI;
        this.button = button;
        GetUserTracker();
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

    private void GetUserTracker() {
        new GetUserTrackerTask().execute();
    }

    class GetUserTrackerTask extends AsyncTask<Void, Void, String> {
        private String res = "";

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient hc = new DefaultHttpClient();
            String message;

            HttpPost p = new HttpPost(Const.GetUserTracker);
            JSONObject object = new JSONObject();
            try {
                object.put("IMEI", IMEI);
            } catch (Exception ex) {

            }

            try {
                message = object.toString();
                p.setEntity(new StringEntity(message, "UTF8"));
                p.setHeader("Content-type", "application/json");
                HttpResponse resp = hc.execute(p);
                if (resp != null) {
                    HttpEntity entity = resp.getEntity();
                    String result = "";
                    if (resp.getStatusLine().getReasonPhrase().equals("OK"))
                    {
                        InputStream instream = entity.getContent();
                        result = convertStreamToString(instream);
                        res = result;
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
                mListener.onComplete();
                Json json  = new Json();
                res = res.replace("\\", "").substring(1);
                res = res.trim();
                String ret = "";
                for (int i = 0; i < json.getArraySize(res); i++)
                {
                    ret += "IMEI: " + json.getByName(res, "IMEI", i) + "<br>"
                        + "STATUS: " + json.getByName(res, "STATUS", i) + "<br>"
                        + "TITLE: " + json.getByName(res, "TITLE", i) + "<br>"
                        + "MOBILE: " + json.getByName(res, "MOBILE", i) + "<br>"
                        + "MODEL: " + json.getByName(res, "MODEL", i) + "<br>"
                        + "LASTUPDATE: " + json.getByName(res, "LASTUPDATE", i) + "<br>"
                        + "LAT: " + json.getByName(res, "LAT", i) + "<br>"
                        + "LON: " + json.getByName(res, "LON", i) + "<br>";
                }
                CustomDialog customDialog = new CustomDialog(mContext, ret);
                customDialog.SetTitle(mContext.getResources().getString(R.string.receive_user_tracker));
                customDialog.SetButtonText(mContext.getResources().getString(R.string.dissmiss));
                customDialog.show();
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

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
