package com.citybike;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.citybike.mainlogin.LogIn;
import com.citybike.mainlogin.UserLoginTask;
import com.citybike.pantallainicio.FragmentFactory.FragmentFactory;
import com.citybike.pantallainicio.FragmentFactory.PreAndPostLogInFragmentFactory;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;

public class MainActivity extends FragmentActivity{
		
	private Map<String,Fragment> preAndPostLogInFragments;
	private FragmentFactory fragmentFactory;
	private boolean isResumed = false;
	private LogIn logIn;
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = 
	    new Session.StatusCallback() {
	    @Override
	    public void call(Session session, 
	            SessionState state, Exception exception) {
	    	LogWrapper.d(Definitions.mainActivityLogTag,"Session state changed");
	        onSessionStateChange(session, state, exception);
	    }
	};
	public static final String USERSDATABASE = "usersDataBaseMock";
	public static final String CONTACTSDATABASE = "contactsDataBase";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		logIn= new LogIn(this);
		logIn.createForm();
		LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		authButton.setReadPermissions(Arrays.asList("email","user_about_me"));
		createAndStoreMainFragments();
	    FragmentManager fm = getSupportFragmentManager();
	    FragmentTransaction transaction = fm.beginTransaction();
	    for (Fragment fragment : preAndPostLogInFragments.values())
	    	transaction.hide(fragment);
	    transaction.commit();
	    uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    
	    initializeUsersDatabase();
	}
	
	private void initializeUsersDatabase(){
	    SharedPreferences registeredUsers = getSharedPreferences(USERSDATABASE, MODE_PRIVATE);
	    SharedPreferences.Editor editor = registeredUsers.edit();
	    editor.putString("leandro.miguenz@gmail.com", "Leandro Miguenz");
	    editor.putString("rodrigo.sanzone@gmail.com", "Rodrigo Sanzone");
	    editor.putString("guillermo.constantino@gmail.com", "Guillermo Constantino");
	    editor.putString("luis.ali@gmail.com", "Luis Ali");
	    editor.putString("karlo.ismael@gmail.com", "Karlo Ismael Oviedo");
	    editor.putString("victoria.perello@gmail.com", "Victoria Perello");
	    editor.putString("alejandro.torrado@gmail.com", "Alejandro Torrado");
	    //Comiteo los cambios al archivo
	    editor.commit();
	}
	
	private void createAndStoreMainFragments() {
		preAndPostLogInFragments=new HashMap<String,Fragment>();
		fragmentFactory= new PreAndPostLogInFragmentFactory(this);
		Fragment splashFragment=fragmentFactory.create(Definitions.SPLASH);
		Fragment selectionFragment=
								fragmentFactory.create(Definitions.SELECTION);
		preAndPostLogInFragments.put(Definitions.SPLASH,splashFragment);
		preAndPostLogInFragments.put(Definitions.SELECTION,selectionFragment);
	}
	private void showFragment(String fragmentType, boolean addToBackStack) {
	    FragmentManager fm = getSupportFragmentManager();
	    FragmentTransaction transaction = fm.beginTransaction();
	    for (Map.Entry<String, Fragment> entry : 
	    	preAndPostLogInFragments.entrySet()){
	    	showCurrentFragmentAndHideOthers(entry,fragmentType,transaction);
	    }
	    if (addToBackStack) {
	        transaction.addToBackStack(null);
	    }
	    transaction.commit();
	}
	private void showCurrentFragmentAndHideOthers(
			Entry<String, Fragment> entry, String fragmentType,
			FragmentTransaction transaction) {
		 if (fragmentType.equals(entry.getKey()))
	            transaction.show(entry.getValue());
	        else
	            transaction.hide(entry.getValue());
	}
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	    isResumed = true;
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	    isResumed = false;
	    


	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	private void onSessionStateChange(Session session,
										SessionState state, 
										Exception exception){
	    // Only make changes if the activity is visible
	    if (isResumed) {
	    	LogWrapper.d(Definitions.mainActivityLogTag,
	    				"the activity isResumed");
	    	clearTheBackStack();
	    	showAuthenticatedOrLogInFragment(state);
	    }
	}
	private void showAuthenticatedOrLogInFragment(SessionState state) {
		if (state.isOpened()) {
			LogWrapper.d(Definitions.mainActivityLogTag,
						"The session state is open");
			LogWrapper.d(Definitions.mainActivityLogTag,
						"Show authenticated fragment");
            showFragment(Definitions.SELECTION, false);
        } else if (state.isClosed()) {
        	LogWrapper.d(Definitions.mainActivityLogTag,
        				"If the session state is closed:");
        	LogWrapper.d(Definitions.mainActivityLogTag,
        				"Show the login fragment");
            showFragment(Definitions.SPLASH, false);
        }
		
	}
	private void clearTheBackStack() {
		FragmentManager manager = getSupportFragmentManager();
        // Get the number of entries in the back stack
        int backStackSize = manager.getBackStackEntryCount();
        // Clear the back stack
        for (int i = 0; i < backStackSize; i++) {
            manager.popBackStack();
        }
		
	}
	@Override
	protected void onResumeFragments() {
	    super.onResumeFragments();
	    Session session = Session.getActiveSession();

	    if (session != null && session.isOpened()) {
	        // if the session is already open,
	        // try to show the selection fragment
	        showFragment(Definitions.SELECTION, false);
	    } else {
	        // otherwise present the splash screen
	        // and ask the person to login.
	        showFragment(Definitions.SPLASH, false);
	    }
	}
	public void attemptLogin() {
		logIn.attemptLogin();
	}
	public EditText getmPasswordView() {
		return logIn.getmPasswordView();
	}
	public void setmAuthTask(UserLoginTask mAuthTask) {
		logIn.setmAuthTask(mAuthTask);
	}
	public void showProgress(final boolean show) {
		logIn.showProgress(show);
	}
}
