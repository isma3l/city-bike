package com.citybike.pantallainicio.Fragments;

import com.citybike.R;
import com.citybike.pantallainicio.CircuitManager;
import com.citybike.pantallainicio.PantallaInicio;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
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
	private CircuitManager circuitManager = new CircuitManager();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  setRetainInstance(true);
		View view = inflater.inflate(R.layout.layout_fragment_circuitos, container, false);
		setUpMapIfNeeded();	

		Button b_crearCircuito= (Button) view.findViewById(R.id.id_b_crearCircuito);
		Button b_guardar = (Button) view.findViewById(R.id.id_b_guardarCircuito);
		Button b_invitarContacto = (Button) view.findViewById(R.id.id_b_invitarContacto);		
		ImageButton b_facebook = (ImageButton) view.findViewById(R.id.id_b_facebook);
		Button b_borrarCircuito = (Button) view.findViewById(R.id.id_b_borrarCircuito);		

		
		b_borrarCircuito.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				mMap.clear();
				circuitManager.clear();
				
			}
		});
		
		b_crearCircuito.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				circuitManager.createCircuit();
			}
		});
		
		b_guardar.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if(circuitManager.circuit()) {
					Toast.makeText(getActivity(), "Se guardo satisfactoriamente", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		b_invitarContacto.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				((PantallaInicio)getActivity()).mostrarDialogoInvitarContactos();
			}
		});
		
		b_facebook.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Compartir en facebook", Toast.LENGTH_SHORT).show();
				
			}
		});
		
		mMap.setOnMapClickListener(new OnMapClickListener(
				) {
			@Override
			public void onMapClick(LatLng point) {
				if(circuitManager.isCreated()) {
					circuitManager.add(point);
					mMap.addPolyline(circuitManager.getCircuit());
				}
						
			}
		});
		
		return view;
	}
	
	private void setUpMap() {
	//	mMap.addMarker(new MarkerOptions().position(HAMBURG).title("Marker"));
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
		LogWrapper.d(Definitions.FragListLogTag,"ckto destruidoooooo");


		SupportMapFragment f_creado = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map_circuitos);

		if (f_creado != null) {
	        try {
	            getFragmentManager().beginTransaction().remove(f_creado).commit();
	            mMap = null;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }		
		super.onDestroyView();
	}
	/*
	@Override
	public void onAttach (Activity activity) {
		super.onAttach(activity);
		LogWrapper.d("**************", "atachhhhhhhhhhhhh");
	}
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrapper.d("**************", "createeeeeeeeeeee");
	}
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogWrapper.d("**************", "activityyyycreateddddd");
	}
	@Override
	public void onViewStateRestored (Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		LogWrapper.d("**************", "restoredddddddddddd");
	}
	@Override
	public void onStart () {
		super.onStart();
		LogWrapper.d("**************", "startttttttttttt");
	}
	@Override
	public void onResume () {
		super.onResume();
		LogWrapper.d("**********", "onResumeeeeeeeeeeeee");
	}
	@Override
	public void onPause () {
		super.onPause();
		LogWrapper.d("**********", "onPauseeeeeeeeeeee");
	}

	@Override
	public void onStop () {
		super.onStop();
		LogWrapper.d("**********", "onStopppppppppppp");
	}

	@Override
	public void onDetach () {
		super.onDetach();
		LogWrapper.d("**********", "onDetachhhhhhhhhh");
	}
	*/
}






