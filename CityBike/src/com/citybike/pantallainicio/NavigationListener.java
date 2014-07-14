package com.citybike.pantallainicio;

import java.util.List;
import java.util.Map;

import com.citybike.R;
import com.citybike.R.id;
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
	private PantallaInicio activity;
	public NavigationListener(PantallaInicio activity) {
		this.activity=activity;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, 
							View view, 
							int position,
							long id) {
		LogWrapper.d(Definitions.FragListLogTag,"onItemClick");
		LogWrapper.d(Definitions.FragListLogTag,
					"Hicieron click en la posición: "+position+" del menú.");
		LogWrapper.d(Definitions.FragListLogTag,"Obtengo fragmentMap");
		Map<Integer, Fragment> fragmentMap=activity.getFragmentMap();
		LogWrapper.d(Definitions.FragListLogTag,
					"Obtengo fragment de la posición: "+position);
		Fragment fragment=fragmentMap.get(position);
		LogWrapper.d(Definitions.FragListLogTag,"Obtengo fragment Manager");
		FragmentManager fragmentManager = 
				 					activity.getSupportFragmentManager(); 
		LogWrapper.d(Definitions.FragListLogTag,"Reemplazo de fragment");
		LogWrapper.d(Definitions.FragListLogTag,"Begin transaction");
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		LogWrapper.d(Definitions.FragListLogTag,"transaction.replace()");
		transaction.replace(R.id.content_frame, fragment);
		LogWrapper.d(Definitions.FragListLogTag,"transaction.commit()");
		transaction.commit();
		LogWrapper.d(Definitions.FragListLogTag,"Reemplazo de fragment ok");
		LogWrapper.d(Definitions.FragListLogTag,"Obtengo drawerlist");
		ListView drawerList=activity.getDrawerList();
		LogWrapper.d(Definitions.FragListLogTag,"seteo item checked en true");
		drawerList.setItemChecked(position, true); 
		changeTitleToActionBar(position);
		LogWrapper.d(Definitions.FragListLogTag,"onItemClick ...OK");
	}
	private void changeTitleToActionBar(int position) {
		LogWrapper.d(Definitions.FragListLogTag,"changeTitleToActionBar");
		LogWrapper.d(Definitions.FragListLogTag,"Obtengo lista de item");
		List<Map<String, Object>> itemList=activity.getOptionList();
		LogWrapper.d(Definitions.FragListLogTag,"Obtengo el item");
		Map<String, Object> rowItem=itemList.get(position); 
		LogWrapper.d(Definitions.FragListLogTag,"Busco el título de la app.");
		String title=new String((String)rowItem.get(Definitions.appName));
		LogWrapper.d(Definitions.FragListLogTag,"Obtengo supportActionBar");
		ActionBar actionBar=activity.getSupportActionBar();
		LogWrapper.d(Definitions.FragListLogTag,"Asigno title a actionBar");
		actionBar.setTitle(title);
		LogWrapper.d(Definitions.FragListLogTag,
					"Asigno title a Pantalla_inicio (activity)");
		activity.setTituloFragmentSeleccionado(title);
		LogWrapper.d(Definitions.FragListLogTag,"Obtengo DrawerLayout");
		DrawerLayout drawerLayout=activity.getDrawerLayout();
		LogWrapper.d(Definitions.FragListLogTag,"Cierro drawer");
		drawerLayout.closeDrawer(activity.getDrawerList());
		LogWrapper.d(Definitions.FragListLogTag,"changeTitleToActionBar..OK");
		
	}
}
