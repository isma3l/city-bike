package com.citybike.pantallainicio.Fragments;

import com.citybike.R;
import com.citybike.pantallainicio.CircuitManager;
import com.citybike.pantallainicio.PantallaInicio;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class FragmentCircuitos extends FragmentMap{
	private LatLng HAMBURG;
	private CircuitManager circuitManager;
	private Button b_crearCircuito;
	private Button b_guardar;
	private Button b_invitarContacto;		
	private ImageButton b_facebook;
	private Button b_borrarCircuito;
	
	public static FragmentCircuitos newInstance(Bundle args){
		FragmentCircuitos fragmentCircuitos=new FragmentCircuitos();
		if (args !=null) 
			fragmentCircuitos.setArguments(args);
		return fragmentCircuitos;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onCreate()");
		HAMBURG = new LatLng(-34.7, -58.32);
		circuitManager = new CircuitManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
								ViewGroup container,
								Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onCreateView()");
		setRetainInstance(true);
		View view = inflater.inflate(R.layout.layout_fragment_circuitos,
																container,
																false);
		if (view !=null){
			b_crearCircuito=(Button) view.findViewById(R.id.id_b_crearCircuito);
			b_guardar = (Button) view.findViewById(R.id.id_b_guardarCircuito);
			b_invitarContacto=
						(Button) view.findViewById(R.id.id_b_invitarContacto);		
			b_facebook = (ImageButton) view.findViewById(R.id.id_b_facebook);
			b_borrarCircuito=
						(Button) view.findViewById(R.id.id_b_borrarCircuito);		
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onActivityCreated");
		replaceIdMapByGoogleMap(R.id.map_circuitos,savedInstanceState);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onViewCreated");
		b_borrarCircuito.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				getmMap().clear();
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
					Toast.makeText(getActivity(),
									"Se guardo satisfactoriamente",
									Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		b_invitarContacto.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				((PantallaInicio)getActivity()).
											mostrarDialogoInvitarContactos();
			}
		});
		
		b_facebook.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Compartir en facebook",
		        							Toast.LENGTH_SHORT).show();
				
			}
		});	
	}
	public void setUpMap() {
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"setUpMap");
		getmMap().setOnMapClickListener(new OnMapClickListener(
				) {
			@Override
			public void onMapClick(LatLng point) {
				if(circuitManager.isCreated()) {
					circuitManager.add(point);
					getmMap().addPolyline(circuitManager.getCircuit());
				}
						
			}
		});
	//	mMap.addMarker(new MarkerOptions().position(HAMBURG).title("Marker"));
		getmMap().moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 10));
	}
	@Override
	public void onDestroyView() {
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onDestroyView");
		destroyView(R.id.map_circuitos);		
		super.onDestroyView();
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onAttach");
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onDestroy");
	}
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onDetach");
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onPause");
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onStart");
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onStop");
	}
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onViewStateRestored");
	}
	@Override
	public void onResume() {
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onResume");
		super.onResume();
//		replaceIdMapByGoogleMap(R.id.map_circuitos,null);
	}
}






