package com.webservice.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.webservice.R;
import com.webservice.Util.Users;

/**
 * Created by Pooya on 6/30/2017.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        final Users users = Users.getInstance();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(users.getUserName() == null)
                {
                    startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 3000);
    }

}
