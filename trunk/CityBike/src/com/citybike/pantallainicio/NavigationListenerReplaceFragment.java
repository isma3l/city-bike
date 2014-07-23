package com.citybike.pantallainicio;

import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;

import android.support.v4.app.FragmentTransaction;

public class NavigationListenerReplaceFragment extends ReplaceFragment {

	public NavigationListenerReplaceFragment(PantallaInicio activity) {
		super(activity);
	}
	public void setPositionAnItemName(int position) {
		super.setPosition(position);
		setItemName(getItemNameFromPosition(position));
	}

	private String getItemNameFromPosition(int position) {
		PantallaInicio activity=getActivity();
		return activity.getItemName(position);
	}

	@Override
	public void addToBackStack(FragmentTransaction transaction) {
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
						"Agrego al stack: "+getItemName());
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
						"Entra al stack: "+getItemName());
		transaction.addToBackStack(getItemName());
	}

}
