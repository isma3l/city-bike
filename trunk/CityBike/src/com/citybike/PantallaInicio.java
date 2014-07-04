package com.citybike;


import com.citybike.listaMenuDesplegable.Lista_Adaptador;
import com.citybike.listaMenuDesplegable.ValoresMenu;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;


public class PantallaInicio extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String tituloFragmentSeleccionado;
    private DrawerToggle drawerToggle;    
    private int posicionActual = -1;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_inicio);
               
        // se asigna por defecto en la pantalla principal el fragment base
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame, new FragmentBase()).commit();     
		
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new DrawerToggle(this, drawerLayout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
 
        drawerList.setAdapter(new Lista_Adaptador(getSupportActionBar().getThemedContext(),
        		R.layout.layout_entrada_menu, ValoresMenu.itemsMenu));
        
        
        drawerList.setOnItemClickListener(new OnItemClickListener() {        	
            @Override
            public void onItemClick(AdapterView parent, View view,
                    int position, long id) {        
                Fragment fragment = null;
                
              
                /*
                 * esta linea se usa cuando se selecciona el mismo item(fragment) que se esta mostrando,
                 * ya que es innecesario destruirlo y crearlo nuevamente si se quiere ver lo mismo
                 * 
                 * aunque la razon principal es que al seleccionar dos veces el mapa o circuito
                 * ocurre un error porque se trata de crear nuevamente un mapa,o circuito,
                 * con su mismo id sin haber eliminado el mapa anterior, no lo entiendo del todo, 
                 * pero por esa razon es que en los fragment del mapa y del  circuito se sobreescribe el 
                 * metodo ondestroy  para asegurarse qe se borren los mapas que contienen. 
                 * 
                 */
                if(posicionActual == position)
                	return;
                
                posicionActual = position;
                
                /*
                 * al seleccionar un item del menu se crea un nuevo fragment
                 * con lo cual deberia guardarse el estado de cada uno al cerrarse 
                 * o se podria tener una lista de todos fragments,
                 * 
                 */
                switch (position) {                	
                    case 0:
                        fragment = new FragmentPerfil();
                        break;
                    case 1:
                        fragment = new FragmentContactos();
                        break;
                    case 2:                                    	 
                    	fragment = new FragmentMapa();                  
                    	break; 
                    case 3:
                    	fragment = new FragmentEventos();
                    	break;    
                    case 4:
                    	fragment = new FragmentCircuitos();
                    	break;
                    case 5:
                    	fragment = new FragmentPremium();
                    	break;
                }
          
                FragmentManager fragmentManager = getSupportFragmentManager();     
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                
                drawerList.setItemChecked(position, true);
     
                tituloFragmentSeleccionado = ValoresMenu.getTitlePosicion(position);
                getSupportActionBar().setTitle(ValoresMenu.getTitlePosicion(position));
     
                drawerLayout.closeDrawer(drawerList);
            }		
        });
        
	
        drawerLayout.setDrawerListener(drawerToggle);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
	
	public String getSectionTitle() {
		return tituloFragmentSeleccionado;
	}
	
	
}
