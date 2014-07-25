package com.citybike.pantallainicio.Fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.citybike.MainActivity;
import com.citybike.R;
import com.citybike.pantallacontactos.ContactListArrayAdapter;
import com.citybike.pantallacontactos.ContactModel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentContactos extends ListFragment{
	
	ArrayAdapter<ContactModel> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
    	inflater.inflate(R.menu.menu_opciones_contactos, menu);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.agregar:
    		showAddContactPopUp();
    		break;
    	case R.id.eliminar:
    		removeSelectedContacts();
    	default:
    		break;
    	}

    	return true;
    } 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	        
        View view = inflater.inflate(R.layout.layout_fragment_contactos, container, false);
        adapter = new ContactListArrayAdapter(this, getModel());
        setListAdapter(adapter);       
        return view;
    }
	
	private void removeSelectedContacts(){
		ArrayList<ContactModel> forRemove = new ArrayList<ContactModel>();
		for(int i = 0; i < adapter.getCount(); i++){
			if( adapter.getItem(i).isSelected() ){
				forRemove.add( adapter.getItem(i) );
			}
		}
		applyRemovals(forRemove);
	}
	
	//Contactos hardcodeados - despu�s habr� que tomarlos de alg�n archivo que haga de BD
	private List<ContactModel> getModel() {
		List<ContactModel> list = new ArrayList<ContactModel>();
		
		//Restauro el estado de la lista de contactos
	    SharedPreferences contacts = getActivity().getSharedPreferences(MainActivity.CONTACTSDATABASE, 0);
	    @SuppressWarnings("unchecked")
	    HashMap<String, String> contactsHash = (HashMap<String, String>) contacts.getAll();
	    
	    for( String key : contactsHash.keySet() ){
	    	list.add( get(contactsHash.get(key), key) );
	    }
	    
		/*list.add(get("Contacto1"));
		list.add(get("Contacto2"));
		list.add(get("Contacto3"));
		list.add(get("Contacto4"));
		list.add(get("Contacto5"));*/
		
		return list;
	}

	private void showAddContactPopUp() {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this.getActivity());
		helpBuilder.setTitle("Agregar contacto");
		helpBuilder.setMessage("Ingrese el mail del usuario que desea agregar");
		final EditText input = new EditText(this.getActivity());
		input.setSingleLine();
		input.setText("");
		helpBuilder.setView(input);
		helpBuilder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		    	addContact( input.getText().toString() );
		    }
		});

		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();
	}
	
	//Obtengo las shared preferences USERSDATABASE y consulto si existe el usuario
	private void addContact(String userMail){
		SharedPreferences registeredUsers = getActivity().getSharedPreferences(MainActivity.USERSDATABASE, 0);
		if( !registeredUsers.contains(userMail) ){
			Toast.makeText(getActivity(), "El usuario no existe", Toast.LENGTH_SHORT).show();
		}else{
			SharedPreferences contacts = getActivity().getSharedPreferences(MainActivity.CONTACTSDATABASE, 0);
			SharedPreferences.Editor editor = contacts.edit();
			String userName = registeredUsers.getString(userMail, "");
		    editor.putString(userMail, userName );
		    //Comiteo los cambios al archivo
		    editor.commit();
		    
			adapter.add( get( userName, userMail ) );		
		}
	}
	
	private void applyRemovals(List<ContactModel> contacts){
		//Elimino los contactos del archivo de contactos
		SharedPreferences contactsDB = getActivity().getSharedPreferences(MainActivity.CONTACTSDATABASE, 0);
		SharedPreferences.Editor editor = contactsDB.edit();
		
		for(ContactModel contact : contacts ){
			editor.remove( contact.getMail() );
			adapter.remove(contact); //esta linea elimina los contactos del adapter
		}
		editor.commit();
	}
	
	private ContactModel get(String name, String mail) {
		return new ContactModel(name, mail);
	}
	
	public static FragmentContactos newInstance(Bundle args){
		FragmentContactos fragmentContactos=new FragmentContactos();
		if (args !=null) 
			fragmentContactos.setArguments(args);
		return fragmentContactos;
	}
	   
	/*@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Toast.makeText(getActivity(), "Click ListItem Number " + position, Toast.LENGTH_SHORT).show();
	}*/
}
