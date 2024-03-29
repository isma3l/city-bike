package com.citybike.pantallainicio.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.citybike.pantallainicio
					.Fragments.GoogleMapFragment
					.OnGoogleMapFragmentListener;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

public abstract class FragmentMap extends Fragment 
										implements OnGoogleMapFragmentListener,
										OnMapClickListener{
	private GoogleMap mMap;	
	private GoogleMapFragment googleMapFragment;
	

	public void replaceIdMapByGoogleMap(int IdMap,Bundle savedInstanceState) {
		LogWrapper.d(Definitions.fragmentMapTag,"replaceIdMapByGoogleMap");
		FragmentManager fm = getChildFragmentManager();
	    googleMapFragment = (GoogleMapFragment) fm.findFragmentById(IdMap);
	    if (googleMapFragment == null) {
	    	LogWrapper.d(Definitions.fragmentMapTag,"Entra a reemplazar");
	    	googleMapFragment = GoogleMapFragment.newInstance(savedInstanceState);
	        FragmentTransaction fragmentTransaction=fm.beginTransaction();
	        fragmentTransaction.replace(IdMap, googleMapFragment);
	        fragmentTransaction.commit();
	    }
		
	}
	public void destroyView(int IdMap) {
		LogWrapper.d(Definitions.fragmentMapTag,"destroyView");
		FragmentManager fm = getChildFragmentManager();
		googleMapFragment = (GoogleMapFragment) fm.findFragmentById(IdMap);
		if (googleMapFragment != null) {
	        try {
	        	LogWrapper.d(Definitions.fragmentMapTag,"entra a borrar");
	            getFragmentManager().beginTransaction()
	            					.remove(googleMapFragment)
	            					.commit();
	            mMap=null;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }	
	}
	public GoogleMap getMap(int IdMap) {
		FragmentManager fm = getChildFragmentManager();
		GoogleMapFragment googleMapFragment = (GoogleMapFragment) fm.findFragmentById(IdMap);
		return googleMapFragment.getMap();
	}
	@Override
	public void onMapReady(GoogleMap mMap) {
		LogWrapper.d(Definitions.fragmentMapTag,"onMapReady");
		this.mMap=mMap;
		if (this.mMap==null) 
			LogWrapper.e(Definitions.fragmentMapTag,"El Mapa es null!!!");
		setUpMap();
	}
	public abstract void setUpMap();
	public abstract GoogleMap getMap();
}
