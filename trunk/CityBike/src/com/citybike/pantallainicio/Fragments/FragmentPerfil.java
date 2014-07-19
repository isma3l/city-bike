package com.citybike.pantallainicio.Fragments;

import com.citybike.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPerfil extends Fragment{
	private final String LOG_TAG = "test";   
	
	@Override
	public View onCreateView(
			LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.v(LOG_TAG, "dentro del oncreateview del fragment 1");
		return inflater.inflate(R.layout.layout_fragment_perfil,
														container,
														false);
	}
}
