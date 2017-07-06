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
import com.webservice.Components.ProcessButton.Handlers.LoginHandler;
import com.webservice.Components.ProcessButton.iml.ActionProcessButton;
import com.webservice.Components.Shimmer.ShimmerFrameLayout;
import com.webservice.Dialogs.CustomDialog;
import com.webservice.R;
import com.webservice.Util.Users;
import com.webservice.Util.Validation;

/**
 * Created by Pooya on 7/6/2017.
 */
public class LoginActivity extends Activity implements LoginHandler.OnCompleteLoginListener{

    private LoginActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.mContext = LoginActivity.this;

        Users users = new Users(mContext);
        if(users.getLogin())
        {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }

        CustomDialog customDialog = new CustomDialog(mContext, mContext.getResources().getString(R.string.register_before));
        customDialog.SetTitle(mContext.getResources().getString(R.string.faild_title));
        customDialog.SetButtonText(mContext.getResources().getString(R.string.dissmiss));
        customDialog.show();

        ShimmerFrameLayout shimmer_actionbar_login = (ShimmerFrameLayout) findViewById(R.id.shimmer_actionbar_login);
        shimmer_actionbar_login.useDefaults();
        shimmer_actionbar_login.setDuration(2000);
        shimmer_actionbar_login.setBaseAlpha(0.5f);
        shimmer_actionbar_login.startShimmerAnimation();

        final MaterialTextField mtfMobile = (MaterialTextField) findViewById(R.id.mtfMobile);
        final MaterialTextField mtfPassword_1 = (MaterialTextField) findViewById(R.id.mtfPassword_1);

        mtfMobile.setOnClickListener(mtfOnClickListener);
        mtfPassword_1.setOnClickListener(mtfOnClickListener);

        mtfMobile.getEditText().addTextChangedListener(new LoginActivity.myTextWatcher(mtfMobile));
        mtfPassword_1.getEditText().addTextChangedListener(new LoginActivity.myTextWatcher(mtfPassword_1));

        final LoginHandler progressGenerator = new LoginHandler(this);
        final ActionProcessButton btnLogIn = (ActionProcessButton) findViewById(R.id.btnSignIn);

        btnLogIn.setMode(ActionProcessButton.Mode.ENDLESS);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                if (mView != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
                }
                String strMobile = mtfMobile.getEditText().getText().toString();
                String strPassword_1 = mtfPassword_1.getEditText().getText().toString();
                String[] strInput = new String[]{strMobile, strPassword_1};
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
                        customDialog.SetTitle(getResources().getString(R.string.title_fill_blank));
                        customDialog.SetButtonText(getResources().getString(R.string.dissmiss));
                        customDialog.show();
                    }
                    else
                    {
                        progressGenerator.start(btnLogIn, strInput, mContext);
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
        Toast.makeText(this, R.string.Successfully_Login, Toast.LENGTH_LONG).show();
        startActivity(new Intent(mContext, MainActivity.class));
        Users users = new Users(mContext);
        users.setLogin();
        finish();
    }
}