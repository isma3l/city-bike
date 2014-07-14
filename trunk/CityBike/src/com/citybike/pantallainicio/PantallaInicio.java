package com.citybike.pantallainicio;


import com.citybike.R;
import com.citybike.R.drawable;
import com.citybike.R.id;
import com.citybike.R.layout;
import com.citybike.R.menu;
import com.citybike.pantallainicio.FragmentFactory.FragmentFactory;
import com.citybike.pantallainicio.FragmentFactory.NavigationFragmentFactory;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;

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
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PantallaInicio extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String tituloFragmentSeleccionado;
    private DrawerToggle drawerToggle; 
    private List<Map<String, Object>> optionList;
    private Map<Integer,Fragment> fragmentMap;
    private FragmentFactory fragmentFactory;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrapper.d(Definitions.mainLogTag,
					"onCreate()... Comienzo del ciclo de vida de la actividad");
        setContentView(R.layout.layout_pantalla_inicio); 
        LogWrapper.d(Definitions.mainLogTag,
        			"setContentView(layout_pantalla_inicio)... OK");   
		createApplicationMainOptions();       
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
	}	
	private void addInitialFragment(Fragment fragment) {
		LogWrapper.d(Definitions.mainLogTag,"addInitialFragment()");
        FragmentManager fragmentManager = getSupportFragmentManager();
        LogWrapper.d(Definitions.mainLogTag,"getSupportFragmentManager ... OK");
        FragmentTransaction fragmentTransaction=
        									fragmentManager.beginTransaction();
        LogWrapper.d(Definitions.mainLogTag,"FragmentTransaction ...OK");
        fragmentTransaction.add(R.id.content_frame,fragment);
        LogWrapper.d(Definitions.mainLogTag,
        			"fragmentTransaction.add(R.id.content_frame, "
        			+ "new FragmentBase()) ... OK");
        fragmentTransaction.commit();  
        LogWrapper.d(Definitions.mainLogTag,
        			"fragmentTransaction.commit()... OK");
        LogWrapper.d(Definitions.mainLogTag,
        			"Se asignó por defecto en la pantalla principal el fragment"
        			+ " base");	
	}
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
        fragmentMap=new HashMap<Integer,Fragment>();
		LogWrapper.d(Definitions.FragListLogTag,"fragmentMap creado");
		fragmentFactory= new NavigationFragmentFactory();
		LogWrapper.d(Definitions.FragListLogTag,"fragmentFactory creada");
        addItemsToOptionList();
        setAdapterToDrawerList(drawerList);
        drawerList.setOnItemClickListener(new NavigationListener(this));
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
		addOption(Definitions.appIcon,
				R.drawable.home,
				Definitions.appName,
				Definitions.home);	
	LogWrapper.d(Definitions.mainLogTag,
				"Se agregó la fila con el icono y el texto: "
				+Definitions.profile);
		addOption(Definitions.appIcon,
					R.drawable.profile,
					Definitions.appName,
					Definitions.profile);	
		LogWrapper.d(Definitions.mainLogTag,
					"Se agregó la fila con el icono y el texto: "
					+Definitions.profile);
		addOption(Definitions.appIcon,
					R.drawable.contactos,
					Definitions.appName,
					Definitions.contact);
		LogWrapper.d(Definitions.mainLogTag,
					"Se agregó la fila con el icono y el texto: "
					+Definitions.contact);
		addOption(Definitions.appIcon,
					R.drawable.eventos,
					Definitions.appName,
					Definitions.event);
		LogWrapper.d(Definitions.mainLogTag,
					"Se agregó la fila con el icono y el texto: "
					+Definitions.event);
		addOption(Definitions.appIcon,
					R.drawable.circuitos,
					Definitions.appName,
					Definitions.circuits);
		LogWrapper.d(Definitions.mainLogTag,
					"Se agregó la fila con el icono y el texto: "
					+Definitions.circuits);
		addOption(Definitions.appIcon,
					R.drawable.dolar,
					Definitions.appName,
					Definitions.bePremium);		
		LogWrapper.d(Definitions.mainLogTag,
					"Se agregó la fila con el icono y el texto: "
					+Definitions.bePremium);
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
		Fragment fragment= fragmentFactory.create(appLabel);
		logFragmentCreationResult(fragment);
		Integer pos=optionList.size()-1;
		LogWrapper.d(Definitions.mainLogTag,
					"(pos,Fragment): ("+pos+","+appLabel+")");
		fragmentMap.put(pos, fragment);
		if (appLabel.equals(Definitions.home))
			addInitialFragment(fragment);
	}
	public Map<Integer, Fragment> getFragmentMap() {
		return fragmentMap;
	}
	public List<Map<String, Object>> getOptionList() {
		return optionList;
	}
	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}


	private void logFragmentCreationResult(Fragment fragment) {
		if (fragment != null) 
			LogWrapper.d(Definitions.mainLogTag,"Fragment creado!!");
		else LogWrapper.e(Definitions.mainLogTag,"fragment e null!!");
		
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
	
	public void setTituloFragmentSeleccionado(String tituloFragmentSeleccionado) {
		this.tituloFragmentSeleccionado = tituloFragmentSeleccionado;
	}
	public String getSectionTitle() {
		return tituloFragmentSeleccionado;
	}


	public ListView getDrawerList() {
		return drawerList;
	}
	
	
}
