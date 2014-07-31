package com.citybike.pantallainicio.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.citybike.MainActivity;
import com.citybike.R;
import com.citybike.pantallamapa.RouteManager;
import com.citybike.utils.Definitions;
import com.citybike.utils.HttpConnection;
import com.citybike.utils.LogWrapper;
import com.citybike.utils.ParserCSV;
import com.citybike.utils.PathJSONParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;



public class FragmentHomeMap extends FragmentMap{
	
	private LatLng CENTRO;
	private ArrayList<Marker> bicicleterias;
	private ArrayList<Marker> estaciones;
	private boolean mostrandoBicicleterias;
	private boolean mostrandoEstaciones;
	private boolean showingRoute;
	private Marker initialRouteMark;
	private Marker finalRouteMark;
	private RouteManager routeManager;
	private Polyline routeView;
	private ArrayList<Polyline> bicisendas;
	private PolylineOptions polyLineOptions;
	private ToggleButton b_bicicleteria;
	private ToggleButton b_estaciones;
	private ToggleButton b_rutas;
	private ToggleButton b_bicisendas;
	private ListView log_out_listview;
	
	private ArrayList<MarkerOptions> marcadoresEstaciones;
	private ArrayList<MarkerOptions> marcadoresBicicleterias;
	private ArrayList<PolylineOptions> polilineasBicisendas;
	private MainActivity mainActivity;
	
	public static FragmentHomeMap newInstance(Bundle args){
		FragmentHomeMap fragmentMapa=new FragmentHomeMap();
		if (args !=null) 
			fragmentMapa.setArguments(args);
		return fragmentMapa;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogWrapper.d(Definitions.fragmentHomeMapTag,"onCreate()");
		initializeValues();		
		
	}
	private void initializeValues() {
		LogWrapper.d(Definitions.fragmentHomeMapTag,"initializeValues()");
		CENTRO = new LatLng(-34.60, -58.38);
		marcadoresEstaciones = new ArrayList<MarkerOptions>();
		marcadoresBicicleterias = new ArrayList<MarkerOptions>();
		polilineasBicisendas = new ArrayList<PolylineOptions>();
		mostrandoBicicleterias = false;
		mostrandoEstaciones = false;
		showingRoute = false;
		routeManager = new RouteManager();
		routeView = null;
		bicisendas = new ArrayList<Polyline>();
		bicicleterias = new ArrayList<Marker>();
		estaciones = new ArrayList<Marker>();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
							ViewGroup container,
							Bundle savedInstanceState) {
		LogWrapper.d(Definitions.fragmentHomeMapTag,"onCreateView()");
		View view = inflater.inflate(R.layout.fragment_pantalla_principal,
									container,
									false);	
		if (view !=null)
			initializeButton(view);
		return view;
	}
	private void initializeButton(View view) {
		b_bicicleteria = (ToggleButton) view.findViewById(R.id.id_b_bicleteria);
		b_estaciones = (ToggleButton) view.findViewById(R.id.id_b_estaciones);
		b_rutas = (ToggleButton) view.findViewById(R.id.id_b_rutas);
		//b_bicisendas = (ToggleButton) view.findViewById(R.id.id_b_bicisendas);
		log_out_listview=(ListView) view.findViewById(R.id.log_out_listview);
		assignValuesToListView();
		assignClickListener();
	}
	
	private void assignValuesToListView() {
	    final ArrayList<String> list = new ArrayList<String>();
	      list.add("salir");
	      LogWrapper.d(Definitions.fragmentHomeMapTag,"assignValuesToListView()");
	    final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
	    		R.layout.logout_listview_item, list);
	    LogWrapper.d(Definitions.fragmentHomeMapTag,"setAdapter()");
	    log_out_listview.setAdapter(adapter);
	}
	private void assignClickListener() {
		
		 b_bicicleteria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			    	setVisibilidadBicicleterias(isChecked);
			    }
		 });
			
		 /*b_bicisendas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	setVisibilidadBicisendas(isChecked);
		    }
		});*/
		
		b_estaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    	setVisibilidadEstaciones(isChecked);
		    }
		});
		log_out_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		          int position, long id) {
		        final String item = (String) parent.getItemAtPosition(position);
		        LogWrapper.d(Definitions.fragmentHomeMapTag,"Hicieronclick en logout");
		      }

		    });
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		 LogWrapper.d(Definitions.fragmentHomeMapTag,"onActivityCreated()");		 
		 replaceIdMapByGoogleMap(R.id.map,savedInstanceState);
	}
	public void setUpMap() {
		LogWrapper.d(Definitions.fragmentHomeMapTag,"setUpMap()");
		getMap().setOnMapClickListener(mainActivity);
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRO, 11));
        colocarMarcadoresBicicleterias(Definitions.CsvBicicleterias);
        colocarMarcadoresEstaciones(Definitions.CsvEstaciones);
        //colocarBicisendas(Definitions.CsvBicisendas);
        setVisibilidadBicicleterias(mostrandoBicicleterias);
        setVisibilidadEstaciones(mostrandoEstaciones);
    }


	/*
	 * este metodo fue necesario porque al crearse y destruirse este fragment
	 * parece que se detruye el fragment pero no el mapa, y no se puede crear otro mapa con el mismo id 
	 */
	@Override
	public void onDestroyView() {
		LogWrapper.d(Definitions.fragmentHomeMapTag,"onDestroy()");
		destroyView(R.id.map);
		super.onDestroyView();
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		LogWrapper.d(Definitions.fragmentHomeMapTag,"onAttach()");
		this.mainActivity=(MainActivity)activity;
	}
	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
		LogWrapper.d(Definitions.fragmentHomeMapTag,"onViewStateRestored()");
		if (savedInstanceState!=null){
			LogWrapper.d(Definitions.fragmentHomeMapTag,"estoy por restaurar estado");
			setUpMap();
		}
	}
	private void generarMarcadoresBicicleterias(String rutaCSV){
		ParserCSV.cargarParser(getResources());
		ArrayList<String[]> lineas = ParserCSV.parsearArchivo(rutaCSV, 9);
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
					marcadoresBicicleterias.add( new MarkerOptions().position( posicion ).title( titulo ).snippet( detalle )
							.visible(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_bicicleteria)));
				}
			}else{
				encabezado = false;
			}
		}
	}
		
	private void colocarMarcadoresBicicleterias(String rutaCSV){
		generarMarcadoresBicicleterias(rutaCSV);
		
		for(MarkerOptions marcador : marcadoresBicicleterias){
			bicicleterias.add( getMap().addMarker(marcador) );
		}
	}
	
	private void setVisibilidadBicicleterias(boolean mostrar){
		for(Marker marcador : bicicleterias){
			marcador.setVisible(mostrar);
		}
	}
	
	private void generarMarcadoresEstaciones(String rutaCSV){
		ParserCSV.cargarParser(getResources());
		ArrayList<String[]> lineas = ParserCSV.parsearArchivo(rutaCSV, 5);
		LatLng posicion = null;
		String titulo = "";
		boolean encabezado = true;
		for(String[] linea : lineas){
			if(!encabezado){
				if(linea.length == 5){
					posicion = new LatLng( Float.valueOf(linea[3]), Float.valueOf(linea[4]) );
					titulo = linea[1];
					marcadoresEstaciones.add( new MarkerOptions().position( posicion ).title( titulo ).visible(false)
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.estaciones24x32)));
				}
			}else{
				encabezado = false;
			}
		}	
	}
	
	private void colocarMarcadoresEstaciones(String rutaCSV){
		generarMarcadoresEstaciones(rutaCSV);
		for(MarkerOptions marcador : marcadoresEstaciones){
			estaciones.add( getMap().addMarker(marcador) );
		}
	}
	
	private void setVisibilidadEstaciones(boolean mostrar){
		for(Marker marcador : estaciones){
			marcador.setVisible(mostrar);
		}
	}
	
	private void generarBicisendas(String rutaCSV){
		ParserCSV.cargarParser(getResources());
		final int CAMPOSBICISENDAS = 16;
		ArrayList<String[]> lineas = ParserCSV.parsearArchivo(rutaCSV, CAMPOSBICISENDAS);
		
		String jsonString = null;
		List<LatLng> puntosBicisenda = null;
		boolean encabezado = true;
		for(String[] linea : lineas){
			if(!encabezado){
				jsonString = linea[linea.length - 1];
				jsonString = jsonString.substring(1, jsonString.length() - 1);
				if( jsonString != null && !jsonString.startsWith("{") ){
					continue;
				}
				
		        try{
		        	puntosBicisenda = PathJSONParser.parseBikePath(jsonString);
		        }catch(Exception e){
		        	continue;
		        }
		        
				PolylineOptions polyOptions = new PolylineOptions();
				//agrega los puntos de la bicisenda a la polyline
				polyOptions.addAll(puntosBicisenda);
				polyOptions.width(2);
				polyOptions.color(Color.CYAN);

				polilineasBicisendas.add(polyOptions);
			}else{
				encabezado = false;
			}
		}
		LogWrapper.d(Definitions.fragmentHomeMapTag,"BICISENDAS CREADAS");
	}
	
	private void colocarBicisendas(String rutaCSV){
		generarBicisendas(rutaCSV);
		for(PolylineOptions polyOptions : polilineasBicisendas){
			bicisendas.add( getMap().addPolyline(polyOptions) );
		}
		LogWrapper.d(Definitions.fragmentHomeMapTag,"BICISENDAS AGREGADAS");
	}
	
	private void setVisibilidadBicisendas(boolean mostrar){
		for(Polyline bicisenda : bicisendas){
			bicisenda.setVisible(mostrar);
		}
		LogWrapper.d(Definitions.fragmentHomeMapTag,"BICISENDAS: VISIBILIDAD=" + mostrar);
	}
	
	private void resetRouteCreation(){
		LogWrapper.d(Definitions.fragmentHomeMapTag,"resetRouteCreation()");
		routeManager.clear();
		routeView.remove();
		initialRouteMark.remove();
		finalRouteMark.remove();
		showingRoute=false;
	}
	@Override
	public GoogleMap getMap(){
		return getMap(R.id.map);
	}
////////////Clases privadas para el adaptador del listview logout////////////
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

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
		        routes = parser.parseRouteQuery(jObject);
	        } catch (Exception e) { 
		        e.printStackTrace();
	        }
		    return routes;
		}
		
		@Override
	    protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			LogWrapper.d(Definitions.fragmentHomeMapTag,"onPostExcecute() parserTask class");
			ArrayList<LatLng> points = null;
			//PolylineOptions polyLineOptions = null;
	 
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
				polyLineOptions.color(Color.GREEN);
				
			}
	 
			routeView = getMap().addPolyline(polyLineOptions);
	    }

	}

	@Override
	public void onMapClick(LatLng point) {
		if (b_rutas.isChecked()){
			if (!showingRoute){
				showRoute(point);
			}else 	
				resetRouteCreation();
			
		}
	}
	
	private void showRoute(LatLng point) {
		LogWrapper.d(Definitions.fragmentHomeMapTag,"onMapClick() showing route es false");
		routeManager.addDirection(point);
		MarkerOptions mark = new MarkerOptions().position(point).visible(true);
		if (routeManager.isRouteCompleted()){
			finalRouteMark = getMap().addMarker(mark);
			LogWrapper.d(Definitions.fragmentHomeMapTag,"onMapClick() la ruta esta completa");
			String url = routeManager.getRouteQueryURL();
			ReadTask downloadTask = new ReadTask();
			downloadTask.execute(url);
			showingRoute = true;
		}else{
			initialRouteMark = getMap().addMarker(mark);
		}
		
	}
	
	
}
