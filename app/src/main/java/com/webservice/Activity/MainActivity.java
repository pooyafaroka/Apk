package com.webservice.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.webservice.ClassType.X_Datatable;
import com.webservice.Components.ProcessButton.Handlers.GetTrackerHandler;
import com.webservice.Components.ProcessButton.Handlers.RegisterHandler;
import com.webservice.Components.ProcessButton.iml.ActionProcessButton;
import com.webservice.R;

public class MainActivity extends Activity implements GetTrackerHandler.OnCompleteGetTrackerListener{

    private MainActivity mContext;
    private GetTrackerHandler progressGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = MainActivity.this;
        final X_Datatable datatable = new X_Datatable(mContext);
        progressGenerator = new GetTrackerHandler(this);

        final ActionProcessButton btnReceive_User_Tracker = (ActionProcessButton) findViewById(R.id.btnReceive_User_Tracker);
        btnReceive_User_Tracker.setMode(ActionProcessButton.Mode.ENDLESS);
        btnReceive_User_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IMEI = datatable.getIMEI(mContext);
                progressGenerator.start(btnReceive_User_Tracker, IMEI, mContext);
            }
        });
    }

    @Override
    public void onComplete() {

    }
}
