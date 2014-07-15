package com.citybike.mainlogin;

import com.citybike.MainActivity;
import com.citybike.R;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;


public class EditorPasswordActionListener implements TextView.OnEditorActionListener {

	private MainActivity mainActivity;


	public EditorPasswordActionListener(MainActivity mainActivity) {
		this.mainActivity=mainActivity;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
			mainActivity.attemptLogin();
			return true;
		}
		return false;
	}

}
