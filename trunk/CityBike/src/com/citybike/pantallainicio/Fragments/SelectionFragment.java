package com.citybike.pantallainicio.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.citybike.MainActivity;
import com.citybike.R;
import com.citybike.pantallainicio.DrawerToggle;
import com.citybike.pantallainicio.NavigationListener;
import com.citybike.pantallainicio.FragmentFactory.FragmentFactory;
import com.citybike.pantallainicio.FragmentFactory.NavigationFragmentFactory;
import com.citybike.pantallainicio.Fragments.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;

public class SelectionFragment extends Fragment implements OnGoogleMapFragmentListener,
OnMapClickListener{
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private UiLifecycleHelper uiHelper;
	
	private DrawerLayout drawerLayout;
    private ListView drawerList;
    private DrawerToggle drawerToggle; 
    private FragmentFactory fragmentFactory;
    private List<Map<String, Object>> optionList;
    private  NavigationListener navigationListener;
    private List<Fragment> fragments;
	@Override
	public void onMapClick(LatLng point) {
		doCallBackOnMapReadyOrOnMapClick(null,point);
		
	}
	@Override
	public void onMapReady(GoogleMap mMap) {
		doCallBackOnMapReadyOrOnMapClick(mMap,null);
	}
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(final Session session, final SessionState state, final Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	public static SelectionFragment newInstance(Bundle args){
		SelectionFragment selectionFragment=new SelectionFragment();
		if (args !=null) 
			selectionFragment.setArguments(args);
		return selectionFragment;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REAUTH_ACTIVITY_CODE) {
	        uiHelper.onActivityResult(requestCode, resultCode, data);
	    }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	    LogWrapper.d(Definitions.mainLogTag,
				"onCreate()... Comienzo del ciclo de vida de la actividad");
    LogWrapper.d(Definitions.mainLogTag,
    			"setContentView(layout_pantalla_inicio)... OK"); 
    fragmentFactory= NavigationFragmentFactory.getInstance();
    fragments=new ArrayList<Fragment>();    
    getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    getActivity().getActionBar().setHomeButtonEnabled(true);
	}
	@SuppressLint("UseSparseArrays")
	private void createApplicationMainOptions(View view) {
		LogWrapper.d(Definitions.mainLogTag,"createApplicationMainOptions()");
		assignDrawerToggleToDrawerLayout(view);
        createNavigationOptions(view);
	}
	private void assignDrawerToggleToDrawerLayout(View view) {
		LogWrapper.d(Definitions.mainLogTag,
					"assignDrawerToggleToDrawerLayout()");
		drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
		LogWrapper.d(Definitions.mainLogTag,
					"findViewById(R.id.drawer_layout)... OK");
        drawerToggle = new DrawerToggle((MainActivity) getActivity(), drawerLayout);
        LogWrapper.d(Definitions.mainLogTag,"DrawerToggle created ... OK");
        drawerLayout.setDrawerListener(drawerToggle);
        LogWrapper.d(Definitions.mainLogTag,
        			"Drawer toggle será notificado sobre los eventos que "
        			+ "ocurran en drawer layout");	
	}


	@SuppressLint("UseSparseArrays")
	private void createNavigationOptions(View view) {
		LogWrapper.d(Definitions.mainLogTag,
					"createNavigationOptions()");
		drawerList = (ListView) view.findViewById(R.id.left_drawer);
        LogWrapper.d(Definitions.mainLogTag,
        			"Listview 'drawerList' created with  findViewById(R.id."
        			+ "left_drawer)... OK");
        optionList=new ArrayList<Map<String,Object>>();
        addItemsToOptionList();
        setAdapterToDrawerList(drawerList);
        navigationListener=new NavigationListener(this);
        drawerList.setOnItemClickListener(navigationListener);
        LogWrapper.d(Definitions.mainLogTag,
        			"Cuando se toca un item en drawerList se invoca a "
        			+ "NavigationItemClickListener (a su método onItemClick)");
	}


	private void setAdapterToDrawerList(ListView drawerList2){
		LogWrapper.d(Definitions.mainLogTag,
					"Le asigno el adaptador que listview necesita para mostrar"
					+ " los items que tiene adentro");
		 String[] columnNames= new String[]{Definitions.appIcon,
				 							Definitions.appName};
	     int[] columnValues=new int[]{R.id.appIcon,R.id.appName};
	     SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),
	    				 						optionList,
	    				 						R.layout.navigation_list_item,
	    				 						columnNames,
	    				 						columnValues);
	     drawerList.setAdapter(simpleAdapter);
	     LogWrapper.d(Definitions.mainLogTag,
	    		 	"Adaptador asignado a drawerlist.. Ok");
	}
	private void addItemsToOptionList() {
		LogWrapper.d(Definitions.mainLogTag,"addItemsToOptionList()");
		addAndLogItem(R.drawable.home,Definitions.home);
		addAndLogItem(R.drawable.profile,Definitions.profile);
		addAndLogItem(R.drawable.contactos,Definitions.contact);
		addAndLogItem(R.drawable.eventos,Definitions.event);
		addAndLogItem(R.drawable.circuitos,Definitions.circuits);
		addAndLogItem(R.drawable.dolar,Definitions.bePremium);
	}
	private void addAndLogItem(int drawableId,String itemName){
		addOption(Definitions.appIcon,drawableId,Definitions.appName,itemName);	
		LogWrapper.d(Definitions.mainLogTag,
					"Se agregó la fila con "
					+ "el icono y el texto: "
									+itemName);
		fragments.add(fragmentFactory.create(itemName));
		int position=fragments.size()-1;
		 FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
	     LogWrapper.d(Definitions.mainLogTag,"getSupportFragmentManager... OK");
	     FragmentTransaction fragmentTransaction=
	        								fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.map_fragment,
								fragments.get(position),
								getItemName(position));
        if (position==0) 
        	fragmentTransaction.show(fragments.get(position));
        else
        	fragmentTransaction.hide(fragments.get(position));
        fragmentTransaction.commit(); 
	}
	private void addOption(String colIconApp,
							Integer iconId,
							String colNameApp,
							String appLabel) {
		LogWrapper.d(Definitions.mainLogTag,
					"addOption (el mapa es un item (una opción formada por "
					+ "icono + texto) de la lista de opciones)");
		Map<String,Object> optionsMap = new HashMap<String,Object>();
		LogWrapper.d(Definitions.mainLogTag,
					"Agrego el icono con id: "+ iconId+" a la clave: "
					+colIconApp+" del mapa");
		optionsMap.put(colIconApp,iconId);
		LogWrapper.d(Definitions.mainLogTag,
					"Agrego el texto del item: "+ appLabel+" a la clave: "
					+colNameApp+" del mapa");
		optionsMap.put(colNameApp,appLabel);
		LogWrapper.d(Definitions.mainLogTag,
					"Agrego el mapa a la lista de opciones");
		optionList.add(optionsMap);
		Integer pos=optionList.size()-1;
		LogWrapper.d(Definitions.mainLogTag,
					"(pos,item): ("+pos+","+appLabel+")");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pantalla_principal, 
	            					container,
	            					false);
		profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);
		createApplicationMainOptions(view);  
		checkForAnOpenSession();
	    return view;
	}
	private void checkForAnOpenSession() {
	    Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        // Get the user's data
	        makeMeRequest(session);
	    }
		
	}

	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
	    super.onSaveInstanceState(bundle);
	    uiHelper.onSaveInstanceState(bundle);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}
	private void makeMeRequest(final Session session) {
	    // Make an API call to get user data and define a 
	    // new callback to handle the response.
	    Request request = Request.newMeRequest(session, 
	            new Request.GraphUserCallback() {
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	            // If the response is successful
	            if (session == Session.getActiveSession()) {
	                if (user != null) {
	                    // Set the id for the ProfilePictureView
	                    // view that in turn displays the profile picture.
	                    profilePictureView.setProfileId(user.getId());
	                    // Set the Textview's text to the user's name.
	                    userNameView.setText(user.getName());
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	        }
	    });
	    request.executeAsync();
	} 
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
	    if (session != null && session.isOpened()) {
	        // Get the user's data.
	        makeMeRequest(session);
	    }
	}
	private void doCallBackOnMapReadyOrOnMapClick(GoogleMap mMap,LatLng point){
		Fragment currentFragment=navigationListener.getCurrentFragment();
		if (currentFragment instanceof FragmentMap )
			checkIfFragmentIsMapAndOnMapReadySetUp(currentFragment,mMap,point);
	}
	private void checkIfFragmentIsMapAndOnMapReadySetUp(Fragment fragment,
														GoogleMap mMap,
														LatLng point) {
			if (point==null)
				logAnCallBackMapReady(fragment,mMap);
			else
				logAndCallBackMapClick(fragment,point);
		
	}
	private void logAndCallBackMapClick(Fragment fragment, LatLng point) {
		LogWrapper.d(Definitions.mainLogTag,
				 "onMapClick");	
		((FragmentMap)fragment).onMapClick(point);
		
	}
	private void logAnCallBackMapReady(Fragment fragment, GoogleMap mMap) {
		LogWrapper.d(Definitions.mainLogTag,
				 "onMapReady");	
		((FragmentMap)fragment).onMapReady(mMap);
	}
	public Fragment getFragment(int position) {
		return fragments.get(position);
	}
	public String getItemName(int position){
		Map<String, Object> item=optionList.get(position);
		return (String )item.get(Definitions.appName);
	}
	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}
	public ListView getDrawerList() {
		return drawerList;
	}
}
