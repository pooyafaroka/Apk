package com.webservice.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.webservice.ClassType.X_Datatable;
import com.webservice.Components.Materialtextfield.MaterialTextField;
import com.webservice.Components.ProcessButton.Handlers.GetHistoryTrackerHandler;
import com.webservice.Components.ProcessButton.Handlers.GetTrackerHandler;
import com.webservice.Components.ProcessButton.Handlers.RegisterHandler;
import com.webservice.Components.ProcessButton.Handlers.SetUserTracker;
import com.webservice.Components.ProcessButton.iml.ActionProcessButton;
import com.webservice.Dialogs.CustomDialog;
import com.webservice.R;
import com.webservice.Util.Validation;

public class MainActivity extends Activity {

    private MainActivity mContext;
    private GetTrackerHandler progressGenerator;
    private GetHistoryTrackerHandler historyProgressGenerator;
    private SetUserTracker saveProgressGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mContext = MainActivity.this;
        final X_Datatable datatable = new X_Datatable(mContext);
        progressGenerator = new GetTrackerHandler();
        historyProgressGenerator = new GetHistoryTrackerHandler();
        saveProgressGenerator = new SetUserTracker();

        final ActionProcessButton btnReceive_User_Tracker = (ActionProcessButton) findViewById(R.id.btnReceive_User_Tracker);
        btnReceive_User_Tracker.setMode(ActionProcessButton.Mode.ENDLESS);
        btnReceive_User_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IMEI = datatable.getIMEI(mContext);
                progressGenerator.start(btnReceive_User_Tracker, IMEI, mContext);
            }
        });

        final ActionProcessButton btnReceive_History_Tracker_Tracker = (ActionProcessButton) findViewById(R.id.btnReceive_History_Tracker_Tracker);
        btnReceive_History_Tracker_Tracker.setMode(ActionProcessButton.Mode.ENDLESS);
        btnReceive_History_Tracker_Tracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String IMEI = datatable.getIMEI(mContext);
                historyProgressGenerator.start(btnReceive_History_Tracker_Tracker, IMEI, mContext);
            }
        });

        final MaterialTextField mtfSerialNumber = (MaterialTextField) findViewById(R.id.mtfSerialNumber);
        final MaterialTextField mtfActivationCode = (MaterialTextField) findViewById(R.id.mtfActivationCode);
        final MaterialTextField mtfTitle = (MaterialTextField) findViewById(R.id.mtfTitle);

        mtfSerialNumber.setOnClickListener(mtfOnClickListener);
        mtfActivationCode.setOnClickListener(mtfOnClickListener);
        mtfTitle.setOnClickListener(mtfOnClickListener);

        mtfSerialNumber.getEditText().addTextChangedListener(new myTextWatcher(mtfSerialNumber));
        mtfActivationCode.getEditText().addTextChangedListener(new myTextWatcher(mtfActivationCode));
        mtfTitle.getEditText().addTextChangedListener(new myTextWatcher(mtfTitle));

        final ActionProcessButton btnSave = (ActionProcessButton) findViewById(R.id.btnSave);
        btnSave.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (mView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
                }
                String strSerialNumber = mtfSerialNumber.getEditText().getText().toString();
                String strActivationCode = mtfActivationCode.getEditText().getText().toString();
                String strTitle = mtfTitle.getEditText().getText().toString();
                String[] strInput = new String[]{strSerialNumber, strActivationCode, strTitle};
                if(Validation.IsEmpty(strInput))
                {
                    CustomDialog customDialog = new CustomDialog(mContext, getResources().getString(R.string.fill_blank));
                    customDialog.SetTitle(getResources().getString(R.string.title_fill_blank));
                    customDialog.SetButtonText(getResources().getString(R.string.dissmiss));
                    customDialog.show();
                }
                else{
                    String IMEI = datatable.getIMEI(mContext);
                    saveProgressGenerator.start(btnSave, strInput, IMEI, mContext);
                }
            }
        });


    }

    public class myTextWatcher implements TextWatcher
    {

        private final View mView;

        public myTextWatcher(View mView)
        {
            this.mView = mView;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            MaterialTextField textField = ((MaterialTextField)findViewById(mView.getId()));
            if(!textField.isExpanded())
            {
                textField.toggle();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    View.OnClickListener mtfOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MaterialTextField textField = ((MaterialTextField)findViewById(view.getId()));
            textField.toggle();
        }
    };
}
