package com.citybike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class FragmentMapa extends Fragment {
	private GoogleMap mMap;	
	private static final LatLng HAMBURG = new LatLng(-34.7, -58.32);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {		 
		View view = inflater.inflate(R.layout.fragment_pantalla_principal, null, false);		
		setUpMapIfNeeded();		
		
		
		Button b_bicicleteria = (Button) view.findViewById(R.id.id_b_bicleteria);
		Button b_talleres = (Button) view.findViewById(R.id.id_b_talleres);
		Button b_estaciones = (Button) view.findViewById(R.id.id_b_bicisendas);
		Button b_rutas = (Button) view.findViewById(R.id.id_b_rutas);
		
		b_bicicleteria.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Mostrar bicicleterias en el mapa", Toast.LENGTH_SHORT).show();				
			}
		});
		
		b_talleres.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Mostrar talleres en el mapa", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		b_estaciones.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Mostrar bicisendas en el mapa", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		b_rutas.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Mostrar rutas amigables", Toast.LENGTH_SHORT).show();
				
			}
		});
		return view;
	}

	@Override
	public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
	
	private void setUpMapIfNeeded() {
    	// Si el nMap esta null entonces es porque no se instancio el mapa.
        if (mMap == null) {
        	// Intenta obtener el mapa del SupportMapFragment. 
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Comprueba si hemos tenido éxito en la obtención del mapa.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
	private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(HAMBURG).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 10));
    }


	/*
	 * este metodo fue necesario porque al crearse y destruirse este fragment
	 * parece que se detruye el fragment pero no el mapa, y no se puede crear otro mapa con el mismo id 
	 */
	@Override
	public void onDestroyView() {
		SupportMapFragment f_creado = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);

		if (f_creado != null) {
	        try {
	            getFragmentManager().beginTransaction().remove(f_creado).commit();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }		
		super.onDestroyView();
	}


	
}
