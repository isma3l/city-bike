package com.citybike.pantallainicio;

import android.graphics.Color;

import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.LatLng;

public class CircuitManager {
	private PolylineOptions poliLine;	
	
	public void createCircuit() {
		poliLine = new PolylineOptions();
		poliLine.width(6);
		poliLine.color(Color.BLUE);
	}

	public void add(LatLng point) {		
		poliLine.add(point);			
	}

	public PolylineOptions getCircuit() {
		return poliLine;
	}
	
	public boolean circuit() {
		return (isCreated() && poliLine.getPoints().size() > 0)? true: false;
	}
	
	public boolean isCreated() {
		return (poliLine != null)?true:false;
	}
	
	public void clear() {
		if(isCreated()) {
			poliLine.getPoints().clear();
		}
	}
	
}
