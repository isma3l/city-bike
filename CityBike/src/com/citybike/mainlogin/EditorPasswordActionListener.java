package com.citybike.mainlogin;

import com.citybike.LoginActivity;
import com.citybike.R;
import com.citybike.R.id;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;


public class EditorPasswordActionListener implements TextView.OnEditorActionListener {

	private LoginActivity loginActivity;

	public EditorPasswordActionListener(LoginActivity loginActivity) {
		this.loginActivity=loginActivity;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
			loginActivity.attemptLogin();
			return true;
		}
		return false;
	}

}
