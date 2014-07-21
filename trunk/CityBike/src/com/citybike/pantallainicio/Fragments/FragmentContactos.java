package com.citybike.pantallainicio.Fragments;

import java.util.ArrayList;
import java.util.List;

import com.citybike.R;
import com.citybike.pantallacontactos.ContactListArrayAdapter;
import com.citybike.pantallacontactos.ContactsRowManager;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FragmentContactos extends ListFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	        
        View rootView = inflater.inflate(R.layout.layout_fragment_contactos, container, false);
                
        ArrayAdapter<ContactsRowManager> adapter = new ContactListArrayAdapter(this, getModel());

        setListAdapter(adapter);
        return rootView;
    }
	  
	//Contactos hardcodeados - después habrá que tomarlos de algún archivo que haga de BD
	private List<ContactsRowManager> getModel() {
		List<ContactsRowManager> list = new ArrayList<ContactsRowManager>();
		list.add(get("Contacto1"));
		list.add(get("Contacto2"));
		list.add(get("Contacto3"));
		list.add(get("Contacto4"));
		list.add(get("Contacto5"));
		//list.get(1).setSelected(true);
		return list;
	}

	private ContactsRowManager get(String s) {
		return new ContactsRowManager(s);
	}

	   
	/*@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(getActivity(), "Click ListItem Number " + position, Toast.LENGTH_SHORT).show();
	}*/
}
