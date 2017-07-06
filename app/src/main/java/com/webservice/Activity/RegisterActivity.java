package com.webservice.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.webservice.Components.Materialtextfield.MaterialTextField;
import com.webservice.Components.ProcessButton.RegisterHandler;
import com.webservice.Components.ProcessButton.iml.ActionProcessButton;
import com.webservice.Components.Shimmer.ShimmerFrameLayout;
import com.webservice.Dialogs.CustomDialog;
import com.webservice.R;
import com.webservice.Util.Validation;

/**
 * Created by Pooya on 6/30/2017.
 */
public class RegisterActivity extends Activity implements RegisterHandler.OnCompleteRegisterListener {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.mContext = RegisterActivity.this;

        ShimmerFrameLayout shimmer_actionbar_login = (ShimmerFrameLayout) findViewById(R.id.shimmer_actionbar_login);
        shimmer_actionbar_login.useDefaults();
        shimmer_actionbar_login.setDuration(2000);
        shimmer_actionbar_login.setBaseAlpha(0.5f);
        shimmer_actionbar_login.startShimmerAnimation();

        final MaterialTextField mtfName = (MaterialTextField) findViewById(R.id.mtfName);
        final MaterialTextField mtfFamily = (MaterialTextField) findViewById(R.id.mtfFamily);
        final MaterialTextField mtfMobile = (MaterialTextField) findViewById(R.id.mtfMobile);
        final MaterialTextField mtfEmail = (MaterialTextField) findViewById(R.id.mtfEmail);
        final MaterialTextField mtfPassword_1 = (MaterialTextField) findViewById(R.id.mtfPassword_1);
        final MaterialTextField mtfPassword_2 = (MaterialTextField) findViewById(R.id.mtfPassword_2);

        mtfName.setOnClickListener(mtfOnClickListener);
        mtfFamily.setOnClickListener(mtfOnClickListener);
        mtfMobile.setOnClickListener(mtfOnClickListener);
        mtfEmail.setOnClickListener(mtfOnClickListener);
        mtfPassword_1.setOnClickListener(mtfOnClickListener);
        mtfPassword_2.setOnClickListener(mtfOnClickListener);

        mtfName.getEditText().addTextChangedListener(new myTextWatcher(mtfName));
        mtfFamily.getEditText().addTextChangedListener(new myTextWatcher(mtfFamily));
        mtfMobile.getEditText().addTextChangedListener(new myTextWatcher(mtfMobile));
        mtfEmail.getEditText().addTextChangedListener(new myTextWatcher(mtfEmail));
        mtfPassword_1.getEditText().addTextChangedListener(new myTextWatcher(mtfPassword_1));
        mtfPassword_2.getEditText().addTextChangedListener(new myTextWatcher(mtfPassword_2));

        final RegisterHandler progressGenerator = new RegisterHandler(this);
        final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);

        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (mView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
                }
                String strName = mtfName.getEditText().getText().toString();
                String strFamily = mtfFamily.getEditText().getText().toString();
                String strMobile = mtfMobile.getEditText().getText().toString();
                String strEmail = mtfEmail.getEditText().getText().toString();
                String strPassword_1 = mtfPassword_1.getEditText().getText().toString();
                String strPassword_2 = mtfPassword_2.getEditText().getText().toString();
                String[] strInput = new String[]{strName, strFamily, strMobile, strEmail, strPassword_1, strPassword_2};
                if(Validation.IsEmpty(strInput))
                {
                    CustomDialog customDialog = new CustomDialog(mContext, getResources().getString(R.string.fill_blank));
                    customDialog.SetTitle(getResources().getString(R.string.title_fill_blank));
                    customDialog.SetButtonText(getResources().getString(R.string.dissmiss));
                    customDialog.show();
                }
                else
                {
                    if(strPassword_1.length() < 6)
                    {
                        CustomDialog customDialog = new CustomDialog(mContext, getResources().getString(R.string.short_length));
                        customDialog.SetTitle(getResources().getString(R.string.fill_blank));
                        customDialog.SetButtonText(getResources().getString(R.string.dissmiss));
                        customDialog.show();
                    }
                    else
                    {
                        if(!strPassword_1.equals(strPassword_2))
                        {
                            CustomDialog customDialog = new CustomDialog(mContext, getResources().getString(R.string.fill_blank));
                            customDialog.SetTitle(getResources().getString(R.string.same_password));
                            customDialog.SetButtonText(getResources().getString(R.string.dissmiss));
                            customDialog.show();
                        }
                        else
                        {
                            progressGenerator.start(btnSignIn, strInput, mContext);
                        }
                    }
                }
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    @Override
    public void onComplete() {
        Toast.makeText(this, R.string.Successfully_Register, Toast.LENGTH_LONG).show();
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
