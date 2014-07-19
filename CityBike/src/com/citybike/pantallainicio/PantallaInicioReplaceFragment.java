package com.citybike.pantallainicio;

import java.util.Map;

import com.citybike.utils.Definitions;

import android.support.v4.app.FragmentTransaction;

public class PantallaInicioReplaceFragment extends ReplaceFragment {
	public PantallaInicioReplaceFragment(PantallaInicio activity) {
		super(activity);
	}
	public void setPositionAnItemName(String itemName) {
		super.setItemName(itemName);
		setPosition(getPositionFromItemName(itemName));
	}
	private int getPositionFromItemName(String itemName) {
		int position=0;
		PantallaInicio activity= getActivity();
		for (Map<String,Object> optionsMap:activity.getOptionList()){
			String currentItemName=(String)optionsMap.get(Definitions.appName);
			if (itemName.equals(currentItemName))
				break;
			position++;
		}
		return position;
	}
	@Override
	public void addToBackStack(FragmentTransaction transaction) {}
}
