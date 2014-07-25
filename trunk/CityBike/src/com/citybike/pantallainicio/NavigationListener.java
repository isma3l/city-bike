package com.citybike.pantallainicio;

import com.citybike.R;
import com.citybike.pantallainicio.FragmentFactory.FragmentFactory;
import com.citybike.pantallainicio.FragmentFactory.NavigationFragmentFactory;
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
	private PantallaInicio pantallaInicio;
	private FragmentFactory fragmentFactory;
	private Fragment currentFragment;
	
	public NavigationListener(PantallaInicio pantallaInicio) {
		super();
		this.pantallaInicio = pantallaInicio;
		fragmentFactory= NavigationFragmentFactory.getInstance();
		currentFragment=null;
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
		return pantallaInicio.getItemName(position);
	}
	private void changeTitleToActionBar(String itemName) {
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
								"changeTitleToActionBar");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Obtengo supportActionBar");
		ActionBar actionBar=pantallaInicio.getSupportActionBar();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Asigno title a actionBar");
		actionBar.setTitle(itemName);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
					"Asigno title a Pantalla_inicio (activity)");
		pantallaInicio.setTituloFragmentSeleccionado(itemName);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Obtengo DrawerLayout");
		DrawerLayout drawerLayout=pantallaInicio.getDrawerLayout();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Cierro drawer");
		drawerLayout.closeDrawer(pantallaInicio.getDrawerList());
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"changeTitleToActionBar..OK");
		
	}
	private void replace(int position){
		String itemName=getItemNameFromPosition(position);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Item name: "+itemName);
		currentFragment=fragmentFactory.create(itemName);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Obtengo fragment Manager");
		FragmentManager fragmentManager = 
				 					pantallaInicio.getSupportFragmentManager(); 
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Reemplazo de fragment");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Begin transaction");
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"transaction.replace()");
		transaction.replace(R.id.content_frame, currentFragment,itemName);
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"transaction.commit()");
		transaction.commit();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"Reemplazo de fragment ok");
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,"Obtengo drawerlist");
		ListView drawerList=pantallaInicio.getDrawerList();
		LogWrapper.d(Definitions.ReplaceFragmentLogTag,
							"seteo item checked en true");
		drawerList.setItemChecked(position, true); 
		changeTitleToActionBar(itemName);
	}
	public Fragment getCurrentFragment() {
		return currentFragment;
	}
	
}
