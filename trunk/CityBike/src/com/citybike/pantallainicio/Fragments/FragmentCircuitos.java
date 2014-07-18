package com.citybike.pantallainicio.Fragments;

import com.citybike.R;
import com.citybike.pantallainicio.CircuitManager;
import com.citybike.pantallainicio.PantallaInicio;
import com.citybike.pantallainicio.Fragments.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class FragmentCircuitos extends Fragment implements OnGoogleMapFragmentListener{
	private GoogleMap mMap;	
	private SupportMapFragment fragment;
	private LatLng HAMBURG;
	private CircuitManager circuitManager;
	private Button b_crearCircuito;
	private Button b_guardar;
	private Button b_invitarContacto;		
	private ImageButton b_facebook;
	private Button b_borrarCircuito;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onCreate()");
		HAMBURG = new LatLng(-34.7, -58.32);
		circuitManager = new CircuitManager();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onCreateView()");
		setRetainInstance(true);
		View view = inflater.inflate(R.layout.layout_fragment_circuitos,container, false);
		if (view !=null){
			b_crearCircuito= (Button) view.findViewById(R.id.id_b_crearCircuito);
			b_guardar = (Button) view.findViewById(R.id.id_b_guardarCircuito);
			b_invitarContacto = (Button) view.findViewById(R.id.id_b_invitarContacto);		
			b_facebook = (ImageButton) view.findViewById(R.id.id_b_facebook);
			b_borrarCircuito = (Button) view.findViewById(R.id.id_b_borrarCircuito);		
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"onActivityCreated");
		createFragmentDynamically();
	}

	private void setListenerToFragmentElements() {
		LogWrapper.d(Definitions.FragmentCircuitLogTag,"Seteo listener");
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
		if (mMap==null) LogWrapper.e(Definitions.FragmentCircuitLogTag,"mMap es null");
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
		
	}

	private void createFragmentDynamically() {
		FragmentManager fm = getChildFragmentManager();
	    fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_circuitos);
	    if (fragment == null) {
	        fragment = SupportMapFragment.newInstance();
	        FragmentTransaction fragmentTransaction=fm.beginTransaction();
	        fragmentTransaction.replace(R.id.map_circuitos, fragment);
	        fragmentTransaction.addToBackStack(null);
	        fragmentTransaction.commit();
	    }else LogWrapper.d(Definitions.FragmentCircuitLogTag,"fragment no es null");
		
	}

	@Override
	public void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMap() {
	//	mMap.addMarker(new MarkerOptions().position(HAMBURG).title("Marker"));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 10));
	}
	
	private void setUpMapIfNeeded() {

    	// Si el nMap esta null entonces es porque no se instancio el mapa.
        if (mMap == null) {
        	LogWrapper.d(Definitions.FragmentCircuitLogTag,"setUpMapIfNeeded: mMap es null. obtengo mapa con el fragment");
        	// Intenta obtener el mapa del SupportMapFragment. 
            mMap = fragment.getMap();
            // Comprueba si hemos tenido éxito en la obtención del mapa.
            if (mMap != null) {
            	LogWrapper.d(Definitions.FragmentCircuitLogTag,"setUpMapIfNeeded: mMap ya no es null");
                setUpMap();
            }else LogWrapper.d(Definitions.FragmentCircuitLogTag,"setUpMapIfNeeded: el mapa sigue siendo null culpa de fragment.getMap()");
        }else LogWrapper.d(Definitions.FragmentCircuitLogTag,"mMap no es null ni bien comienza setUpMapIfNeeded");
    }
	
	@Override
	public void onDestroyView() {
		LogWrapper.d(Definitions.FragListLogTag,"ckto destruidoooooo");
		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_circuitos);
		if (fragment!= null) {
	        try {
	            getFragmentManager().beginTransaction().remove(fragment).commit();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }		
		super.onDestroyView();
	}
	@Override
	public void onMapReady(GoogleMap mMap) {
		this.mMap=mMap;
		setUpMapIfNeeded();
		setListenerToFragmentElements();
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






