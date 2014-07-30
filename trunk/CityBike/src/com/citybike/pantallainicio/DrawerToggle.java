package com.citybike.pantallainicio;

import com.citybike.R;
import com.citybike.R.drawable;
import com.citybike.R.string;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/*
 * clase usada para cambiar el titulo del actionBarActivity segun el item seleccionado
 */
public class DrawerToggle extends ActionBarDrawerToggle {	
	private PantallaInicio pantallaInicio;
	
	public DrawerToggle(PantallaInicio activity, DrawerLayout drawerLayout) {
		super(activity, drawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close);
		pantallaInicio = activity;
	}
	 
	@Override
	public void onDrawerClosed(View view) {
		pantallaInicio.getActionBar().setTitle(pantallaInicio.getSectionTitle());
    }

	@Override
    public void onDrawerOpened(View drawerView) {
        pantallaInicio.getActionBar().setTitle(pantallaInicio.getTitle());
    }
}
