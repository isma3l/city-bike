package com.citybike.pantallainicio.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		 View view = super.onCreateView(inflater, container, savedInstanceState);
	        if (callBack != null) {
	            callBack.onMapReady(getMap());
	        }
	        return view;
	}
	@Override
	public void onDetach() {
	    callBack = null;
	    super.onDetach();
	}
	

}
