package com.citybike.pantallamapa;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class RouteManager implements Parcelable{
	private LatLng origin;
	private LatLng destination;
	public static final RouteManagerCreator CREATOR= new RouteManagerCreator(); 
	public RouteManager(){
		origin = null;
		destination = null;
	}
	public RouteManager(Parcel source){
		origin= source.readParcelable(LatLng.class.getClassLoader());
		destination= source.readParcelable(LatLng.class.getClassLoader());
	}
	public void addDirection(LatLng direction){
		if(origin == null){
			origin = direction;
		}else if (destination == null){
			destination = direction;
		}
	}
	
	public boolean isRouteCompleted(){
		return origin != null && destination != null;
	}
	
	public void clear(){
		origin = null;
		destination = null;
	}
	
	public LatLng getOrigin(){
		return origin;
	}
	
	public LatLng getDestination(){
		return destination;
	}
	
	public String getRouteQueryURL(){
		if( !isRouteCompleted() ){
			return null;
		}
		
		String baseURL = "http://maps.googleapis.com/maps/api/directions/";
		String output = "json";
		String orig = "origin=" + origin.latitude + "," + origin.longitude;
		String dest = "destination=" + destination.latitude + "," + destination.longitude;
		String sensor = "sensor=false";
		String mode = "mode=bicycling";
		//String waypoints = "waypoints=optimize:true|" + origin.latitude + "," ;
		
		String url = baseURL + output + "?" + orig + "&" + dest + "&" + sensor /*+ "&" + mode*/;
		return url;
	}

	@Override
	public int describeContents() {
		return  CONTENTS_FILE_DESCRIPTOR;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(origin,flags);
		dest.writeParcelable(destination,flags);
	}
}
