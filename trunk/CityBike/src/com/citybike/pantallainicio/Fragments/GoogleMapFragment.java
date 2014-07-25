package com.citybike.pantallainicio.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citybike.utils.Definitions;
import com.citybike.utils.LogWrapper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class GoogleMapFragment extends SupportMapFragment {
	private OnGoogleMapFragmentListener callBack=null;
	public static interface OnGoogleMapFragmentListener{
		void onMapReady(GoogleMap mMap);
	}
	public static GoogleMapFragment newInstance(Bundle args){
		GoogleMapFragment googleMapFragment=new GoogleMapFragment();
		if (args !=null) 
			googleMapFragment.setArguments(args);
		return googleMapFragment;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogWrapper.d(Definitions.GoogleMapFragLogTag,"onAttach");
		try {
            callBack = (OnGoogleMapFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName() + 
            		" debe implementar OnGoogleMapFragmentListener");
        }
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogWrapper.d(Definitions.GoogleMapFragLogTag,"onCreateView()");
		 View view = super.onCreateView(inflater, container, savedInstanceState);
	        if (callBack != null) {
	        	LogWrapper.d(Definitions.GoogleMapFragLogTag,
						"callback NO es null");
	            callBack.onMapReady(getMap());
	        }else 
	        	LogWrapper.d(Definitions.GoogleMapFragLogTag,
	        							"callback es null");
	        return view;
	}
	@Override
	public void onDetach() {
		LogWrapper.d(Definitions.GoogleMapFragLogTag,"onDetach");
	    callBack = null;
	    super.onDetach();
	}
	

}
