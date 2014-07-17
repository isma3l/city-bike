package com.citybike.pantallainicio.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;

public class DialogInviteContacts extends DialogFragment {
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
		alert.setTitle("Invitar contactos");
		alert.setSingleChoiceItems(getNombreContactos(),0 ,null);
		
		//hardcodedddddd
		boolean [] isSelect = {false,false,false,false,false,false};
		alert.setMultiChoiceItems(getNombreContactos(), isSelect , 
				new OnMultiChoiceClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// TODO Auto-generated method stub						
					}
				});
		
		alert.setPositiveButton("Aceptar", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Dialog dialog = alert.create();
		return dialog;
	}
	
	
	// harcoded, deberia ir en otra clase que le provea esos datos 
	// de la memoria interna, etc
		private String[] getNombreContactos() {
			String[] nombres= {"Leo Messi", "Neymar da Silva", "Cristiano Ronaldo", "Fernando Torres", 
					"Wayne Rooney", "Sergio Agüero"};
			return nombres;
		}


	


	
}