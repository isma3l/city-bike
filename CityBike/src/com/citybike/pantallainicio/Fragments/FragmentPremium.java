package com.citybike.pantallainicio.Fragments;

import com.citybike.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPremium extends Fragment{
	public static FragmentEventos newInstance(Bundle args){
		FragmentEventos fragmentEventos=new FragmentEventos();
		if (args !=null) 
			fragmentEventos.setArguments(args);
		return fragmentEventos;
	}
	   @Override
	    public View onCreateView(
	        LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	 
	        return inflater.inflate(R.layout.layout_fragment_premium,
	        												container,
	        												false);
	    }
}
