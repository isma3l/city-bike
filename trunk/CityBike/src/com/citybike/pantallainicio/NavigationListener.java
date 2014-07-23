package com.citybike.pantallainicio;

import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class NavigationListener  implements OnItemClickListener {
	private ReplaceFragment replaceFragment=null;
	private int currentPositionItemClick;
	@Override
	public void onItemClick(AdapterView<?> parent, 
							View view, 
							int position,
							long id) {
		LogWrapper.d(Definitions.FragListLogTag,"onItemClick");
		LogWrapper.d(Definitions.FragListLogTag,
					"Hicieron click en la posición: "+position+" del menú.");
		LogWrapper.d(Definitions.FragListLogTag,
					"Creación del fragment de la posición: "+position);
		currentPositionItemClick=position;
		((NavigationListenerReplaceFragment)replaceFragment).
										setPositionAnItemName(position);
		((NavigationListenerReplaceFragment)replaceFragment).start();
		LogWrapper.d(Definitions.FragListLogTag,"onItemClick ...OK");
	}
	public int getCurrentPositionItemClick() {
		return currentPositionItemClick;
	}
	public void setReplaceFragment(ReplaceFragment replaceFragment) {
		this.replaceFragment = replaceFragment;
	}
	
}
