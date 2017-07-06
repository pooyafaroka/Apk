package com.webservice.Components.ProcessButton;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.webservice.Activity.LoginActivity;
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

import java.util.Random;

public class ProgressGenerator {

    private Context mContext;
    private Users users;
    private Handler handler;
    private ProcessButton button;

    public interface OnCompleteListener {

        public void onComplete();
    }

    private OnCompleteListener mListener;
    private int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button, String[] strInput, final Context mContext) {
        this.mContext = mContext;
        users = new Users(mContext);
        this.button = button;
        button.setEnabled(true);
        Register(strInput);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgress += 10;
                button.setProgress(mProgress);
                handler.postDelayed(this, generateDelay());
                if(mProgress >= 100)
                    mProgress = 0;
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

    public void Register(String[] strInput) {

        new RegisterTask().execute(strInput);
    }

    class RegisterTask extends AsyncTask<String, Void, String> {
        String Name = "";
        String Family = "";
        String Mobile = "";
        String Email = "";
        String Password = "";
        private String res = "";
        private int HTTP_STATUS_CODE = 204;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strInput) {
            Name = strInput[Users.UserEnum.Name.ordinal()];
            Family = strInput[Users.UserEnum.Family.ordinal()];
            Mobile = strInput[Users.UserEnum.Mobile.ordinal()];
            Email = strInput[Users.UserEnum.Email.ordinal()];
            Password = strInput[Users.UserEnum.Password_1.ordinal()];

            HttpClient hc = new DefaultHttpClient();
            String message;

            HttpPost p = new HttpPost(Const.Register);
            JSONObject object = new JSONObject();
            try {
                object.put("Name", Name);
                object.put("Family", Family);
                object.put("Mobile", Mobile);
                object.put("Email", Email);
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
                stop();
                mListener.onComplete();
            }
            else if(res.equals("EXIST"))
            {
                CustomDialog customDialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.register_before));
                customDialog.SetTitle(mContext.getResources().getString(R.string.faild_title));
                customDialog.SetButtonText(mContext.getResources().getString(R.string.dissmiss));
                customDialog.show();
                stop();
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            }
            else if(res.equals("NaN"))
            {
                CustomDialog customDialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.faild));
                customDialog.SetTitle(mContext.getResources().getString(R.string.faild_title));
                customDialog.SetButtonText(mContext.getResources().getString(R.string.dissmiss));
                customDialog.show();
                stop();
            }
            button.setText(R.string.Register);
            button.setProgress(0);
        }
    }
}
