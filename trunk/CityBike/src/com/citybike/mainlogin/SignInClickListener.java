package com.citybike.mainlogin;

import com.citybike.MainActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class SignInClickListener implements OnClickListener {
	private MainActivity mainActivity;
	
	public SignInClickListener(MainActivity mainActivity) {
		this.mainActivity= mainActivity;
	}

	@Override
	public void onClick(View v) {
		mainActivity.attemptLogin();
	}

}
