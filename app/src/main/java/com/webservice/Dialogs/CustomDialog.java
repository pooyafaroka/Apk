package com.webservice.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webservice.R;

public class CustomDialog extends Dialog{

	private Context context;
	private String message = "";
    private String yesText = "";
    private String noText = "";
	private String title = "";
    private boolean ignoreDismiss = false;
	private String txtButton;

	public CustomDialog(Context context, String message)
	{
		super(context);
		this.context = context;
		this.message = message;
	}

	public void SetTitle( String title){
		this.title = title;
	}

    public void SetButtonText(String str) {
        this.txtButton = str;
    }

    @Override
    public void onBackPressed() {
        ignoreDismiss = false;
        dismiss();
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_custom);
		setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Environment
        ((TextView) findViewById(R.id.txtMsg)).setText(Html.fromHtml(message));
		if( !title.isEmpty()) ((TextView) findViewById(R.id.tvTitle)).setText(Html.fromHtml(title));
		if( !txtButton.isEmpty()) ((TextView) findViewById(R.id.tvButton)).setText(Html.fromHtml(txtButton));

        // Listeners
		LinearLayout llDissmiss = (LinearLayout) findViewById(R.id.llDissmiss);
		llDissmiss.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				dismiss();
				return false;
			}
		});
	}

	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		super.setCanceledOnTouchOutside(true);
	}

    public boolean IgnoreDismiss() {
        return ignoreDismiss;
    }
}
