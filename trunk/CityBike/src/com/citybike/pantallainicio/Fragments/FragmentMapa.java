package com.citybike.pantallainicio.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.citybike.R;
import com.citybike.pantallainicio.RouteManager;
import com.citybike.pantallainicio.Fragments.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.citybike.utils.HttpConnection;
import com.citybike.utils.LogWrapper;
import com.citybike.utils.ParserCSV;
import com.citybike.utils.PathJSONParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;



public class FragmentMapa extends Fragment{
	private GoogleMap mMap;	
	private SupportMapFragment fragment;
	private LatLng CENTRO;
	private String CSVBicicleterias;
	private String CSVEstaciones;
	private ArrayList<Marker> bicicleterias;
	private ArrayList<Marker> estaciones;
	private boolean mostrandoBicicleterias;
	private boolean mostrandoEstaciones;
	private RouteManager routeManager;
	private Polyline routeView;
	private boolean routesButtonToggle;
	private Button b_bicicleteria;
	private Button b_estaciones;
	private Button b_rutas;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CENTRO = new LatLng(-34.60, -58.38);
		CSVBicicleterias = "bicicleterias.csv";
		CSVEstaciones = "estaciones.csv";
		bicicleterias = new ArrayList<Marker>();
		estaciones = new ArrayList<Marker>();
		mostrandoBicicleterias = false;
		mostrandoEstaciones = false;
		routeManager = new RouteManager();
		routeView = null;
		routesButtonToggle = false;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
							ViewGroup container,
							Bundle savedInstanceState) {		 
		View view = inflater.inflate(R.layout.fragment_pantalla_principal,
									container,
									false);			
		if (view !=null){
			b_bicicleteria = (Button) view.findViewById(R.id.id_b_bicleteria);
			//Button b_talleres = (Button) view.findViewById(R.id.id_b_talleres);
			b_estaciones = (Button) view.findViewById(R.id.id_b_bicisendas);
			b_rutas = (Button) view.findViewById(R.id.id_b_rutas);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FragmentManager fm = getChildFragmentManager();
	    fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
	    if (fragment == null) {
	        fragment = SupportMapFragment.newInstance();
	        FragmentTransaction fragmentTransaction=fm.beginTransaction();
	        fragmentTransaction.replace(R.id.map, fragment);
	        fragmentTransaction.addToBackStack(null);
	        fragmentTransaction.commit();
	    }
	}
	@Override
	public void onViewCreated(View view,Bundle savedInstanceState) {
		super.onViewCreated(view,savedInstanceState);
		b_bicicleteria.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (!mostrandoBicicleterias){
					mostrandoBicicleterias = true;					
				}else{
					mostrandoBicicleterias = false;
				}
				setVisibilidadBicicleterias(mostrandoBicicleterias); 
			}
		});
		
		/*b_talleres.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        Toast.makeText(getActivity(), "Mostrar talleres en el mapa", Toast.LENGTH_SHORT).show();
				
			}
		});*/
		
		b_estaciones.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        //Toast.makeText(getActivity(), "Mostrar bicisendas en el mapa", Toast.LENGTH_SHORT).show();
		        if (!mostrandoEstaciones){
		        	mostrandoEstaciones = true;					
				}else{
					mostrandoEstaciones = false;
				}
				setVisibilidadEstaciones(mostrandoEstaciones); 
			}
		});
		
		b_rutas.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
		        if (routesButtonToggle == false){
		        	routesButtonToggle = true;
			        mMap.setOnMapClickListener(new OnMapClickListener(){
			        	boolean showingRoute = false;
						@Override
						public void onMapClick(LatLng point) {
							if( !showingRoute ){
								routeManager.addDirection(point);
								if (routeManager.isRouteCompleted()){
									String url = routeManager.getRouteQueryURL();
									ReadTask downloadTask = new ReadTask();
									downloadTask.execute(url);
									showingRoute = true;
								}
							}
						}		
			        });  
		        }else{
		        	resetRouteCreation();
		        }
			}
		});
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
            mMap = fragment.getMap();
            // Comprueba si hemos tenido éxito en la obtención del mapa.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
	
	private void setUpMap() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRO, 11));
        colocarMarcadoresBicicleterias(CSVBicicleterias);
        colocarMarcadoresEstaciones(CSVEstaciones);
        setVisibilidadBicicleterias(mostrandoBicicleterias);
        setVisibilidadEstaciones(mostrandoEstaciones);
    }


	/*
	 * este metodo fue necesario porque al crearse y destruirse este fragment
	 * parece que se detruye el fragment pero no el mapa, y no se puede crear otro mapa con el mismo id 
	 */
	@Override
	public void onDestroyView() {
		FragmentManager fm = getChildFragmentManager();
		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		if (fragment != null) {
	        try {
	            getFragmentManager().beginTransaction().remove(fragment).commit();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }		
		super.onDestroyView();
	}
	private ArrayList<MarkerOptions> generarMarcadoresBicicleterias(String rutaCSV){
		ParserCSV.cargarParser(getResources());
		ArrayList<String[]> lineas = ParserCSV.parsearArchivo(rutaCSV, 9);
		ArrayList<MarkerOptions> marcadores = new ArrayList<MarkerOptions>();
		LatLng posicion = null;
		String titulo = "";
		String detalle = "";
		boolean encabezado = true;
		for(String[] linea : lineas){
			if(!encabezado){
				if(linea.length == 9){
					posicion = new LatLng( Float.valueOf(linea[6]), Float.valueOf(linea[7]) );
					titulo = linea[0];
					detalle = linea[1] + " / " + linea[2] + " / " + linea[3];
					marcadores.add( new MarkerOptions().position( posicion ).title( titulo ).snippet( detalle )
							.visible(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_bicicleteria)));
				}
			}else{
				encabezado = false;
			}
		}
		 
		return marcadores;		
	}
		
	private void colocarMarcadoresBicicleterias(String rutaCSV){
		ArrayList<MarkerOptions> moBicicleterias = generarMarcadoresBicicleterias(rutaCSV);
		
		for(MarkerOptions marcador : moBicicleterias){
			bicicleterias.add( mMap.addMarker(marcador) );
		}
	}
	
	private void setVisibilidadBicicleterias(boolean mostrar){
		for(Marker marcador : bicicleterias){
			marcador.setVisible(mostrar);
		}
	}
	
	private ArrayList<MarkerOptions> generarMarcadoresEstaciones(String rutaCSV){
		ParserCSV.cargarParser(getResources());
		ArrayList<String[]> lineas = ParserCSV.parsearArchivo(rutaCSV, 5);
		ArrayList<MarkerOptions> marcadores = new ArrayList<MarkerOptions>();
		LatLng posicion = null;
		String titulo = "";
		boolean encabezado = true;
		for(String[] linea : lineas){
			if(!encabezado){
				if(linea.length == 5){
					posicion = new LatLng( Float.valueOf(linea[3]), Float.valueOf(linea[4]) );
					titulo = linea[1];
					marcadores.add( new MarkerOptions().position( posicion ).title( titulo ).visible(false)
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.estaciones24x32)));
				}
			}else{
				encabezado = false;
			}
		}
		 
		return marcadores;		
	}
	
	private void colocarMarcadoresEstaciones(String rutaCSV){
		ArrayList<MarkerOptions> moEstaciones = generarMarcadoresEstaciones(rutaCSV);
		for(MarkerOptions marcador : moEstaciones){
			estaciones.add( mMap.addMarker(marcador) );
		}
	}
	
	private void setVisibilidadEstaciones(boolean mostrar){
		for(Marker marcador : estaciones){
			marcador.setVisible(mostrar);
		}
	}
	
	private void resetRouteCreation(){
		routeManager.clear();
		routeView.remove();
		routesButtonToggle = false;
	}
	
	////////////Clases privadas para la consulta de rutas////////////
	
	////////////////////ReadTask/////////////////////////
	private class ReadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... url) {		
			String data = "";
			try {
		        HttpConnection http = new HttpConnection();
		        data = http.readUrl(url[0]);
		        } catch (Exception e) {
		        	LogWrapper.d("Tarea en 2do plano", e.toString());
		    }
			return data;
		}
		
		@Override
	    protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
	    }

	}
	
	////////////////////ParserTask///////////////////////
	
	private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			JSONObject jObject;
		    List<List<HashMap<String, String>>> routes = null;
		 
		    try {
		        jObject = new JSONObject(jsonData[0]);
		        PathJSONParser parser = new PathJSONParser();
		        routes = parser.parse(jObject);
	        } catch (Exception e) {
		        e.printStackTrace();
	        }
		    return routes;
		}
		
		@Override
	    protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;
	 
			//recorre las rutas y agrega sus puntos a la polyline
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);
	 
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);
	 
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);
	 
					points.add(position);
				}
	 
				polyLineOptions.addAll(points);
				polyLineOptions.width(2);
				polyLineOptions.color(Color.BLUE);
			}
	 
			routeView = mMap.addPolyline(polyLineOptions);
	    }

	}
	
}
