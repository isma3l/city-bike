package com.citybike.pantallainicio.Fragments;

import com.citybike.R;
import com.citybike.pantallaEventos.AdapterList;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentEventos extends ListFragment {
	
	public static FragmentEventos newInstance(Bundle args){
	

		FragmentEventos fragmentEventos=new FragmentEventos();
		if (args !=null) 
			fragmentEventos.setArguments(args);
		return fragmentEventos;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view =  inflater.inflate(R.layout.layout_fragment_eventos, container, false);
		setListAdapter(new AdapterList(this));
		return view;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	      //Toast.makeText(((Fragment)callBack).getActivity(), Integer.toString(position),Toast.LENGTH_SHORT).show();
			
	}
	
}





