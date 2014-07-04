package com.citybike.listaMenuDesplegable;

import java.util.List;
import java.util.ArrayList;
import com.citybike.R;
 
/*
 * clase harcodeada
 */
public class ValoresMenu {
	public final static List<Entrada_Lista> itemsMenu = new ArrayList<Entrada_Lista>() {
		private static final long serialVersionUID = 1L;
		{
			add(new Entrada_Lista(R.drawable.profile,"Mi Perfil"));
			add(new Entrada_Lista(R.drawable.contactos,"Mi Contactos"));
			add(new Entrada_Lista(R.drawable.ic_mapa,"Mapa"));
			add(new Entrada_Lista(R.drawable.eventos,"Eventos"));
			add(new Entrada_Lista(R.drawable.circuitos,"Circuitos"));
			add(new Entrada_Lista(R.drawable.dolar,"Ser Premium"));
		}
	};
		
	public List<Entrada_Lista> getItemsMenu() {
		return itemsMenu;
	}
	
	public static String getTitlePosicion(int position) {
		return itemsMenu.get(position).getTitulo();
	}
}
