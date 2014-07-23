package com.citybike.pantallainicio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

import com.citybike.R;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;

public abstract class ReplaceFragment {
	private PantallaInicio activity;
	private int position;
	private String itemName;
	
	public ReplaceFragment(PantallaInicio activity) {
		this.activity = activity;
	}
	public PantallaInicio getActivity() {
		return activity;
	}
	public void start(){
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Item name: "+itemName);
		Fragment fragment=activity.getFragments().get(position);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Obtengo fragment Manager");
		FragmentManager fragmentManager = 
				 					activity.getSupportFragmentManager(); 
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Reemplazo de fragment");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Begin transaction");
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"transaction.replace()");
		transaction.replace(R.id.content_frame, fragment,itemName);
		addToBackStack(transaction);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"transaction.commit()");
		transaction.commit();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Reemplazo de fragment ok");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Obtengo drawerlist");
		ListView drawerList=activity.getDrawerList();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"seteo item checked en true");
		drawerList.setItemChecked(position, true); 
		changeTitleToActionBar();
	}
	public String getItemName() {
		return itemName;
	}
	public abstract void addToBackStack(FragmentTransaction transaction);
	private void changeTitleToActionBar() {
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
								"changeTitleToActionBar");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Obtengo supportActionBar");
		ActionBar actionBar=activity.getSupportActionBar();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Asigno title a actionBar");
		actionBar.setTitle(itemName);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
					"Asigno title a Pantalla_inicio (activity)");
		activity.setTituloFragmentSeleccionado(itemName);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Obtengo DrawerLayout");
		DrawerLayout drawerLayout=activity.getDrawerLayout();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Cierro drawer");
		drawerLayout.closeDrawer(activity.getDrawerList());
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"changeTitleToActionBar..OK");
		
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
