package com.citybike.pantallainicio.Fragments;

import java.util.Arrays;

import com.citybike.R;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SelectionFragment extends Fragment {
	public static SelectionFragment newInstance(Bundle args){
		SelectionFragment selectionFragment=new SelectionFragment();
		if (args !=null) 
			selectionFragment.setArguments(args);
		return selectionFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_pantalla_inicio, 
	            					container,
	            					false);
	    return view;
	}

}
