package com.citybike.pantallainicio.Fragments;

import java.util.Arrays;

import com.citybike.R;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SplashFragment extends Fragment{
	
	public static SplashFragment newInstance(Bundle args){
		SplashFragment splashFragment=new SplashFragment();
		if (args !=null) 
			splashFragment.setArguments(args);
		return splashFragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_login, 
				container,
				false);
		
return view;
	}
}
