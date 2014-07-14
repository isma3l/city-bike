package com.citybike;

import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

	private final String mEmail;
	private final String mPassword;
	private LoginActivity loginActivity;

	UserLoginTask(String email, String password, LoginActivity loginActivity) {
		mEmail = email;
		mPassword = password;
		this.loginActivity=loginActivity;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		LogWrapper.d(Definitions.UserLoginTaskLogTag,
					"attempt authentication against a network service.");
		try {
			LogWrapper.d(Definitions.UserLoginTaskLogTag,
					"Simulate network access.");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			return false;
		}

		for (String credential : Definitions.DUMMY_CREDENTIALS) {
			String[] pieces = credential.split(":");
			if (accountExists(pieces,mEmail)) return passwordMatches(pieces);
		}
		registerNewAccount();
		return true;
	}

	private void registerNewAccount() {
		LogWrapper.d(Definitions.UserLoginTaskLogTag,
				"register the new account here.");
	}

	private Boolean passwordMatches(String[] pieces) {
		return pieces[1].equals(mPassword);
	}

	private boolean accountExists(String[] pieces,String mEmail) {
		return (pieces[0].equals(mEmail));
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		loginActivity.setmAuthTask(null);
		loginActivity.showProgress(false);
		if (success)
			startActivityPantallaInicio();
		else
			adviceWrongPasswordAndFocusInPasswordEditText();
	}

	private void adviceWrongPasswordAndFocusInPasswordEditText() {
		LogWrapper.d(Definitions.UserLoginTaskLogTag,
				"advice wrongPassword");
		String wrongPassword=
				loginActivity.getString(R.string.error_incorrect_password);
		EditText mPasswordView=loginActivity.getmPasswordView();
		mPasswordView.setError(wrongPassword);
		mPasswordView.requestFocus();	
	}

	private void startActivityPantallaInicio() {
		LogWrapper.d(Definitions.UserLoginTaskLogTag,
				"start activity pantalla inicio");
		Intent intent= new Intent(loginActivity,PantallaInicio.class);
		loginActivity.startActivity(intent);
	}

	@Override
	protected void onCancelled() {
		LogWrapper.d(Definitions.UserLoginTaskLogTag,"onCancelled().");
		loginActivity.setmAuthTask(null);
		loginActivity.showProgress(false);
	}
}
