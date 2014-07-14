package com.citybike;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {	
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrapper.d(Definitions.loginLogTag,"onCreate()");
		setContentView(R.layout.activity_login);
		LogWrapper.d(Definitions.loginLogTag,"Set up the login form.");
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		populateAutoComplete();
		mPasswordView = (EditText) findViewById(R.id.password);
		LogWrapper.d(Definitions.loginLogTag,"set actionlistener to passview");
		mPasswordView
			.setOnEditorActionListener(new EditorPasswordActionListener(this));
		Button mEmailSignInButton = 
			(Button) findViewById(R.id.email_sign_in_button);
		LogWrapper.d(Definitions.loginLogTag,
				"set cliklistener to SignIn button");
		mEmailSignInButton.setOnClickListener(new SignInClickListener(this));
		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
	}

	private void populateAutoComplete() {
		LogWrapper.d(Definitions.loginLogTag,"Servicio  de autocompletado");
		getLoaderManager().initLoader(0, null, this);
	}
	public void attemptLogin() {
		LogWrapper.i(Definitions.loginLogTag,
					"Attempts to sign in or register the account specified by "
					+ "the login form.");
		LogWrapper.i(Definitions.loginLogTag,"If there are form errors (invalid"
				+ " email, missing fields, etc.), the errors are presented and "
				+ "no actual login attempt is made.");
		if (noCurrentBackgroundTaskPerformingUserLoginAttempt()){
			LogWrapper.d(Definitions.loginLogTag,"Reset errors.");
			mEmailView.setError(null);
			mPasswordView.setError(null);
			LogWrapper.d(Definitions.loginLogTag,
						"Store values at the time of the login attempt.");
			String email = mEmailView.getText().toString();
			String password = mPasswordView.getText().toString();
			View focusView = getFocusView(email,password);
			focusToViewWithErrorOrStartBackground(focusView,email,password);
		}
	}

	private void focusToViewWithErrorOrStartBackground(View focusView,
			String email, String password) {
		if (errorInLogIn(focusView))
			focusToViewWereErrorWasProduced(focusView);
		else 
			startBackgroundTaskToPerformLogInAttempt(email,password);
		
	}

	private void startBackgroundTaskToPerformLogInAttempt(String email,
			String password) {
		LogWrapper.d(Definitions.loginLogTag,
					"Show a progress spinner, and kick off a background task to"
					+ " perform the user login attempt.");
		showProgress(true);
		mAuthTask = new UserLoginTask(email, password,this);
		LogWrapper.d(Definitions.loginLogTag,"Start background task");
		mAuthTask.execute((Void) null);
		
	}

	public void setmAuthTask(UserLoginTask mAuthTask) {
		this.mAuthTask = mAuthTask;
	}

	private void focusToViewWereErrorWasProduced(View focusView) {
		LogWrapper.d(Definitions.loginLogTag,
				"There was an error; don't attempt login and focus "
				+ "the first form field with an error.");
	focusView.requestFocus();
		
	}

	private boolean errorInLogIn(View focusView) {
		return (focusView!=null);
	}

	private View getFocusView(String email, String password) {
		LogWrapper.d(Definitions.loginLogTag,
					"Check for a valid email address.");
		if (mailIsEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			return mEmailView;
		}
		if (mailExistsAndIsInvalid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			return mEmailView;
		}
		LogWrapper.d(Definitions.loginLogTag,
				"Check for a valid password, if the user entered one.");
		if (passwordExistsAndIsInvalid(password)){
			mPasswordView.setError(getString(R.string.error_invalid_password));
			return mPasswordView;
		}
		return null;
	}
	private boolean mailIsEmpty(String email){
		return TextUtils.isEmpty(email);
	}
	private boolean mailExistsAndIsInvalid(String email){
		return ((!TextUtils.isEmpty(email))&&((!isEmailValid(email))));
	}
	private boolean passwordExistsAndIsInvalid(String password) {
		return (!TextUtils.isEmpty(password) && !isPasswordValid(password));
	}

	private boolean noCurrentBackgroundTaskPerformingUserLoginAttempt() {
		return (mAuthTask == null);
	}

	private boolean isEmailValid(String email) {
		// TODO: Replace this with your own logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		LogWrapper.i(Definitions.loginLogTag,
				"Shows the progress UI and hides the login form.");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
			useHoneyCombMr2ViewPropertyAnimatorApiToFadeInProgressSpinner(show);
		else
			showAndHideRelevantUiComponents(show);
	}

	public EditText getmPasswordView() {
		return mPasswordView;
	}

	private void showAndHideRelevantUiComponents(final boolean show) {
		mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
		mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
	}

	private void useHoneyCombMr2ViewPropertyAnimatorApiToFadeInProgressSpinner(
													final boolean show) {
		int shortAnimTime = getResources().getInteger(
				android.R.integer.config_shortAnimTime);
		LogWrapper.d(Definitions.loginLogTag,"use HoneyCombMr2 api's");
		setViewProperties(true,mLoginFormView,show,shortAnimTime);
		setViewProperties(false,mProgressView,show,shortAnimTime);
		mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	private void setViewProperties(boolean isLogInView,
									View view,
									final boolean show,
									int shortAnimTime) {
		view.setVisibility(show ? View.GONE : View.VISIBLE);
		ViewPropertyAnimator viewPropertyAnimator=view.animate();
		viewPropertyAnimator.setDuration(shortAnimTime);
		viewPropertyAnimator.alpha( isLogInView ?(show ? 0 : 1):(show ? 0 : 1));
		ViewAnimatorListenerAdapter viewAnimatorListenerAdapter=
									new ViewAnimatorListenerAdapter(view,show);
		viewPropertyAnimator.setListener(viewAnimatorListenerAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		Uri uriRetrieveDataRowsForTheDeviceUsersProfileContact=
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
				ContactsContract.Contacts.Data.CONTENT_DIRECTORY);
		String[] projection=ProfileQuery.PROJECTION;
		String onlyEmailAddressSelection=
				ContactsContract.Contacts.Data.MIMETYPE + " = ?";
		String[] selectionArgs=
				new String[] {
				ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE };
		String orderPrimaryEmailAddressFirst=
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC";
		CursorLoader loaderQueriesContentResolverReturnCursor=
				new CursorLoader(this,
						uriRetrieveDataRowsForTheDeviceUsersProfileContact,
						projection,
						onlyEmailAddressSelection,
						selectionArgs,
						orderPrimaryEmailAddressFirst);
		return loaderQueriesContentResolverReturnCursor;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}
		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		ArrayAdapter<String> adapterAutoCompleteTextViewShowInDropdownList = 
				new ArrayAdapter<String>(LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);
		mEmailView.setAdapter(adapterAutoCompleteTextViewShowInDropdownList);
	}
}
