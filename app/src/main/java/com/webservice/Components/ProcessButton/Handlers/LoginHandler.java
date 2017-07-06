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
import com.webservice.Util.Users;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by Pooya on 7/6/2017.
 */
public class LoginHandler {

    private Context mContext;
    private Users users;
    private Handler handler;
    private ProcessButton button;

    public interface OnCompleteLoginListener {
        public void onComplete();
    }

    private OnCompleteLoginListener mListener;
    private int mProgress;

    public LoginHandler(OnCompleteLoginListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button, String[] strInput, final Context mContext) {
        this.mContext = mContext;
        users = new Users(mContext);
        this.button = button;
        button.setEnabled(true);
        Login(strInput);
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

    public void stop()
    {
        if(handler != null)
        {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private int generateDelay() {
        return 750;
    }

    public void Login(String[] strInput) {

        new LoginTask().execute(strInput);
    }

    class LoginTask extends AsyncTask<String, Void, String> {
        String Mobile = "";
        String Password = "";
        private String res = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strInput) {
            Mobile = strInput[0];
            Password = strInput[1];

            HttpClient hc = new DefaultHttpClient();
            String message;

            HttpPost p = new HttpPost(Const.Login);
            JSONObject object = new JSONObject();
            try {
                object.put("Mobile", Mobile);
                object.put("Password", Password);
            } catch (Exception ex) {

            }

            try {
                message = object.toString();
                p.setEntity(new StringEntity(message, "UTF8"));
                p.setHeader("Content-type", "application/json");
                HttpResponse resp = hc.execute(p);
                if (resp != null) {
                    HttpEntity entity = resp.getEntity();
                    String result = EntityUtils.toString(entity).replace("\"", "");
                    if (resp.getStatusLine().getReasonPhrase().equals("OK"))
                    {
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
            if(res.equals("OK"))
            {
                mListener.onComplete();
            }
            else if(res.equals("NOT"))
            {
                CustomDialog customDialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.wrong_username_password));
                customDialog.SetTitle(mContext.getResources().getString(R.string.faild_title));
                customDialog.SetButtonText(mContext.getResources().getString(R.string.dissmiss));
                customDialog.show();
            }
            else if(res.equals("NaN"))
            {
                CustomDialog customDialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.faild));
                customDialog.SetTitle(mContext.getResources().getString(R.string.faild_title));
                customDialog.SetButtonText(mContext.getResources().getString(R.string.dissmiss));
                customDialog.show();
            }
            stop();
            button.setText(R.string.Log_In);
            button.setProgress(0);
        }
    }
}
