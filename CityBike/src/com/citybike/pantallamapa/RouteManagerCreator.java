package com.citybike.pantallamapa;

import android.os.Parcel;
import android.os.Parcelable;

public class RouteManagerCreator implements Parcelable.Creator<RouteManager> {

	@Override
	public RouteManager createFromParcel(Parcel source) {
		return new RouteManager(source);
	}

	@Override
	public RouteManager[] newArray(int size) {
		return new RouteManager[size];
	}

}
