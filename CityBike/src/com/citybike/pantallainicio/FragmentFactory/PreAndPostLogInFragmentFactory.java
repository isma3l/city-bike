package com.citybike.pantallainicio.FragmentFactory;

import com.citybike.MainActivity;
import com.citybike.R;
import com.citybike.utils.Definitions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class PreAndPostLogInFragmentFactory implements FragmentFactory {
	private MainActivity mainActivity;
	
	public PreAndPostLogInFragmentFactory(MainActivity mainActivity) {
		super();
		this.mainActivity = mainActivity;
	}

	@Override
	public Fragment create(String type) {
		FragmentManager fm = mainActivity.getSupportFragmentManager();
		if (type.equals(Definitions.SPLASH))
			return fm.findFragmentById(R.id.splashFragment);
		if (type.equals(Definitions.SELECTION))
			return fm.findFragmentById(R.id.selectionFragment);
		return null;
	}

}
