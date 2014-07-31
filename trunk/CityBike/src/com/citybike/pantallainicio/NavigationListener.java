package com.citybike.pantallainicio;

import com.citybike.MainActivity;
import com.citybike.R;
import com.citybike.pantallainicio.Fragments.FragmentCircuitos;
import com.citybike.pantallainicio.Fragments.FragmentHomeMap;
import com.citybike.pantallainicio.Fragments.FragmentMap;
import com.citybike.pantallainicio.Fragments.SelectionFragment;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NavigationListener  implements OnItemClickListener {
	private SelectionFragment selectionFragment;
	private Fragment currentFragment;
	
	public NavigationListener(SelectionFragment selectionFragment) {
		super();
		this.selectionFragment = selectionFragment;
		currentFragment=selectionFragment.getFragment(0);
//		this.mainActivity=selectionFragment.getActivity();
	}
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
		replace(position);
		LogWrapper.d(Definitions.FragListLogTag,"onItemClick ...OK");
	}
	private String getItemNameFromPosition(int position) {
		return selectionFragment.getItemName(position);
	}
	private void changeTitleToActionBar(String itemName) {
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
								"changeTitleToActionBar");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Obtengo supportActionBar");
		ActionBar actionBar=((MainActivity)this.selectionFragment.getActivity()).getSupportActionBar();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Asigno title a actionBar");
		actionBar.setTitle(itemName);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
					"Asigno title a Pantalla_inicio (activity)");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Obtengo DrawerLayout");
		DrawerLayout drawerLayout=selectionFragment.getDrawerLayout();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Cierro drawer");
		drawerLayout.closeDrawer(selectionFragment.getDrawerList());
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"changeTitleToActionBar..OK");
		
	}
	private void replace(int position){
		String itemName=getItemNameFromPosition(position);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Item name: "+itemName);
		Fragment previousFragment=currentFragment;
		currentFragment=selectionFragment.getFragment(position);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Obtengo fragment Manager");
		FragmentManager fragmentManager = 
				((MainActivity)this.selectionFragment.getActivity()).
				getSupportFragmentManager(); 
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Reemplazo de fragment");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Begin transaction");
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"transaction.hide() && show()");
		if (!previousFragment.equals(currentFragment)){
			checkIfFragmentIsMapAndDestroyIt(previousFragment);
			transaction.hide(previousFragment);
			transaction.show(currentFragment);
			LogWrapper.d(Definitions.ReplaceFragmentLogTag,"transaction.commit()");
			transaction.commit();
			LogWrapper.d(Definitions.ReplaceFragmentLogTag,
								"Reemplazo de fragment ok");
			LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Obtengo drawerlist");
			ListView drawerList=selectionFragment.getDrawerList();
			LogWrapper.d(Definitions.ReplaceFragmentLogTag,
								"seteo item checked en true");
			drawerList.setItemChecked(position, true); 
			changeTitleToActionBar(itemName);
		}
	}
	private void checkIfFragmentIsMapAndDestroyIt(Fragment previousFragment) {
		if ((previousFragment instanceof FragmentMap)&&(currentFragment instanceof FragmentMap)){
			LogWrapper.d(Definitions.ReplaceFragmentLogTag,"El anterior es un mapa");
			if (previousFragment instanceof FragmentHomeMap)
				((FragmentHomeMap)previousFragment).destroyView(R.id.map);
			else
				((FragmentCircuitos)previousFragment).destroyView(R.id.map_circuitos);
		}
		checkCurrentFragmentOnEqualMapThenLoad();
	}
	private void checkCurrentFragmentOnEqualMapThenLoad() {
		if (currentFragment instanceof FragmentHomeMap)
			((FragmentHomeMap)currentFragment).
										replaceIdMapByGoogleMap(R.id.map,null);
		else
			if (currentFragment instanceof FragmentCircuitos)
			((FragmentCircuitos)currentFragment).
						  	replaceIdMapByGoogleMap(R.id.map_circuitos,null);
		
	}
	public Fragment getCurrentFragment() {
		return currentFragment;
	}
	
}
