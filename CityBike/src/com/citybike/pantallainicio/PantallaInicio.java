package com.citybike.pantallainicio;


import com.citybike.R;
import com.citybike.pantallainicio.FragmentFactory.FragmentFactory;
import com.citybike.pantallainicio.FragmentFactory.NavigationFragmentFactory;
import com.citybike.pantallainicio.Fragments.DialogInviteContacts;
import com.citybike.pantallainicio.Fragments.FragmentMap;
import com.citybike.pantallainicio.Fragments.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PantallaInicio extends ActionBarActivity 
										implements OnGoogleMapFragmentListener,
										OnMapClickListener{
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String tituloFragmentSeleccionado;
    private DrawerToggle drawerToggle; 
    private FragmentFactory fragmentFactory;
    private List<Map<String, Object>> optionList;
    private  NavigationListener navigationListener;
    private List<Fragment> fragments;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrapper.d(Definitions.mainLogTag,
					"onCreate()... Comienzo del ciclo de vida de la actividad");
        setContentView(R.layout.layout_pantalla_inicio); 
        LogWrapper.d(Definitions.mainLogTag,
        			"setContentView(layout_pantalla_inicio)... OK"); 
        fragmentFactory= NavigationFragmentFactory.getInstance();
        fragments=new ArrayList<Fragment>();
        createApplicationMainOptions();      
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
	}	
	@SuppressLint("UseSparseArrays")
	private void createApplicationMainOptions() {
		LogWrapper.d(Definitions.mainLogTag,"createApplicationMainOptions()");
		assignDrawerToggleToDrawerLayout();
        createNavigationOptions();
	}
	private void assignDrawerToggleToDrawerLayout() {
		LogWrapper.d(Definitions.mainLogTag,
					"assignDrawerToggleToDrawerLayout()");
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		LogWrapper.d(Definitions.mainLogTag,
					"findViewById(R.id.drawer_layout)... OK");
        drawerToggle = new DrawerToggle(this, drawerLayout);
        LogWrapper.d(Definitions.mainLogTag,"DrawerToggle created ... OK");
        drawerLayout.setDrawerListener(drawerToggle);
        LogWrapper.d(Definitions.mainLogTag,
        			"Drawer toggle será notificado sobre los eventos que "
        			+ "ocurran en drawer layout");	
	}


	@SuppressLint("UseSparseArrays")
	private void createNavigationOptions() {
		LogWrapper.d(Definitions.mainLogTag,
					"createNavigationOptions()");
		drawerList = (ListView) findViewById(R.id.left_drawer);
        LogWrapper.d(Definitions.mainLogTag,
        			"Listview 'drawerList' created with  findViewById(R.id."
        			+ "left_drawer)... OK");
        optionList=new ArrayList<Map<String,Object>>();
        addItemsToOptionList();
        setAdapterToDrawerList(drawerList);
        navigationListener=new NavigationListener(this);
        drawerList.setOnItemClickListener(navigationListener);
        LogWrapper.d(Definitions.mainLogTag,
        			"Cuando se toca un item en drawerList se invoca a "
        			+ "NavigationItemClickListener (a su método onItemClick)");
	}


	private void setAdapterToDrawerList(ListView drawerList2){
		LogWrapper.d(Definitions.mainLogTag,
					"Le asigno el adaptador que listview necesita para mostrar"
					+ " los items que tiene adentro");
		 String[] columnNames= new String[]{Definitions.appIcon,
				 							Definitions.appName};
	     int[] columnValues=new int[]{R.id.appIcon,R.id.appName};
	     SimpleAdapter simpleAdapter=new SimpleAdapter(this,
	    				 						optionList,
	    				 						R.layout.navigation_list_item,
	    				 						columnNames,
	    				 						columnValues);
	     drawerList.setAdapter(simpleAdapter);
	     LogWrapper.d(Definitions.mainLogTag,
	    		 	"Adaptador asignado a drawerlist.. Ok");
	}
	private void addItemsToOptionList() {
		LogWrapper.d(Definitions.mainLogTag,"addItemsToOptionList()");
		addAndLogItem(R.drawable.home,Definitions.home);
		addAndLogItem(R.drawable.profile,Definitions.profile);
		addAndLogItem(R.drawable.contactos,Definitions.contact);
		addAndLogItem(R.drawable.eventos,Definitions.event);
		addAndLogItem(R.drawable.circuitos,Definitions.circuits);
		addAndLogItem(R.drawable.dolar,Definitions.bePremium);
	}
	private void addAndLogItem(int drawableId,String itemName){
		addOption(Definitions.appIcon,drawableId,Definitions.appName,itemName);	
		LogWrapper.d(Definitions.mainLogTag,
					"Se agregó la fila con "
					+ "el icono y el texto: "
									+itemName);
		fragments.add(fragmentFactory.create(itemName));
		int position=fragments.size()-1;
		 FragmentManager fragmentManager = getSupportFragmentManager();
	     LogWrapper.d(Definitions.mainLogTag,"getSupportFragmentManager... OK");
	     FragmentTransaction fragmentTransaction=
	        								fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.content_frame,
								fragments.get(position),
								getItemName(position));
        if (position==0) 
        	fragmentTransaction.show(fragments.get(position));
        else
        	fragmentTransaction.hide(fragments.get(position));
        fragmentTransaction.commit(); 
	}
	private void addOption(String colIconApp,
							Integer iconId,
							String colNameApp,
							String appLabel) {
		LogWrapper.d(Definitions.mainLogTag,
					"addOption (el mapa es un item (una opción formada por "
					+ "icono + texto) de la lista de opciones)");
		Map<String,Object> optionsMap = new HashMap<String,Object>();
		LogWrapper.d(Definitions.mainLogTag,
					"Agrego el icono con id: "+ iconId+" a la clave: "
					+colIconApp+" del mapa");
		optionsMap.put(colIconApp,iconId);
		LogWrapper.d(Definitions.mainLogTag,
					"Agrego el texto del item: "+ appLabel+" a la clave: "
					+colNameApp+" del mapa");
		optionsMap.put(colNameApp,appLabel);
		LogWrapper.d(Definitions.mainLogTag,
					"Agrego el mapa a la lista de opciones");
		optionList.add(optionsMap);
		Integer pos=optionList.size()-1;
		LogWrapper.d(Definitions.mainLogTag,
					"(pos,item): ("+pos+","+appLabel+")");
	}
	public List<Map<String, Object>> getOptionList() {
		return optionList;
	}
	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// este metodo es usado  al seleccionar un icono del actionBar
		
	    if (drawerToggle.onOptionsItemSelected(item)) {
	        return true;
	    }	 
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    drawerToggle.syncState();
	}
	 
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    drawerToggle.onConfigurationChanged(newConfig);
	}

	/*
	 * Los 2 metodos sgtes son para mostrar el icono de setting a la derecha,
	 * por si se necesita 
	 */
	
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pantalla_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) { 	  
	    return super.onPrepareOptionsMenu(menu);
	}
	public void setTituloFragmentSeleccionado(
											String tituloFragmentSeleccionado) {
		this.tituloFragmentSeleccionado = tituloFragmentSeleccionado;
	}
	public String getSectionTitle() {
		return tituloFragmentSeleccionado;
	}


	public ListView getDrawerList() {
		return drawerList;
	}
	

	// este metodo se usa para mostrar el dialogo que permite invitar contactos
	// es el camino mas facil y rapido que encontre, 
	//no es eficiente ni tiene buen diseño, pero permite mostrar los "contactos"
	public void mostrarDialogoInvitarContactos() {
		android.app.FragmentManager fm = getFragmentManager();
		DialogInviteContacts alert = new DialogInviteContacts();
		alert.show(fm, "dialog_contact");
	}

	public String getItemName(int position){
		Map<String, Object> item=optionList.get(position);
		return (String )item.get(Definitions.appName);
	}
	@Override
	public void onMapReady(GoogleMap mMap) {
		doCallBackOnMapReadyOrOnMapClick(mMap,null);
	}
	private void doCallBackOnMapReadyOrOnMapClick(GoogleMap mMap,LatLng point){
		Fragment currentFragment=navigationListener.getCurrentFragment();
		checkIfFragmentIsMapAndOnMapReadySetUp(
				currentFragment!=null ? currentFragment:fragments.get(0),
				mMap,
				point);
	}
	private void checkIfFragmentIsMapAndOnMapReadySetUp(Fragment fragment,
														GoogleMap mMap,
														LatLng point) {
		if (fragment instanceof FragmentMap )
			if (point==null)
				logAnCallBackMapReady(fragment,mMap);
			else
				logAndCallBackMapClick(fragment,point);
		
	}
	private void logAndCallBackMapClick(Fragment fragment, LatLng point) {
		LogWrapper.d(Definitions.mainLogTag,
				 "onMapClick");	
		((FragmentMap)fragment).onMapClick(point);
		
	}
	private void logAnCallBackMapReady(Fragment fragment, GoogleMap mMap) {
		LogWrapper.d(Definitions.mainLogTag,
				 "onMapReady");	
		((FragmentMap)fragment).onMapReady(mMap);
	}
	public Fragment getFragment(int position) {
		return fragments.get(position);
	}
	@Override
	public void onMapClick(LatLng point) {
		doCallBackOnMapReadyOrOnMapClick(null,point);
		
	}

}
