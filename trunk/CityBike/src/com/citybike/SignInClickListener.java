package com.citybike;

import android.view.View;
import android.view.View.OnClickListener;

public class SignInClickListener implements OnClickListener {
	private LoginActivity loginActivity;
	
	public SignInClickListener(LoginActivity loginActivity) {
		this.loginActivity = loginActivity;
	}

	@Override
	public void onClick(View v) {
		loginActivity.attemptLogin();
	}

}
