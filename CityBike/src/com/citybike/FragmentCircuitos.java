package com.citybike;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class FragmentCircuitos extends Fragment{
	private GoogleMap mMap;	
	private static final LatLng HAMBURG = new LatLng(-34.7, -58.32);
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_fragment_circuitos, container, false);
		setUpMapIfNeeded();	
		

		Button b_crearCircuito= (Button) view.findViewById(R.id.id_b_crearCircuito);
		Button b_guardar = (Button) view.findViewById(R.id.id_b_guardarCircuito);
		Button b_invitarContacto = (Button) view.findViewById(R.id.id_b_invitarContacto);		
		ImageButton b_facebook = (ImageButton) view.findViewById(R.id.id_b_facebook);
		
		b_crearCircuito.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Crear circuito", Toast.LENGTH_SHORT).show();				
			}
		});
		
		b_guardar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Guardar Circuito", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		b_invitarContacto.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Invitar COntacto", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		b_facebook.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Compartir en facebook", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		return view;
	}
	
	private void setUpMap() {
		mMap.addMarker(new MarkerOptions().position(HAMBURG).title("Marker"));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 10));
	}
	
	private void setUpMapIfNeeded() {
    	// Si el nMap esta null entonces es porque no se instancio el mapa.
        if (mMap == null) {
        	// Intenta obtener el mapa del SupportMapFragment. 
            mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_circuitos)).getMap();
            // Comprueba si hemos tenido éxito en la obtención del mapa.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
	@Override
	public void onDestroyView() {
		SupportMapFragment f_creado = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_circuitos);

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