package com.citybike.pantallaEventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.citybike.R;

/*
 * clase que contiene  la informacion de todos los eventos
 * esta hardcodeada
 */
public class Eventos {
	public static List<Evento> myEvents = new ArrayList<Evento>();
	public static Map<String, Evento> mapEvents = new HashMap<String, Evento>();
	
	static {
		myEvents.add(new Evento("0",R.drawable.evento1_,"Urban City Bike","05.08.2014 | 11hr"));
		myEvents.add(new Evento("1",R.drawable.event2__,"Red Bull Holy Bike","19.09.2014 | 15hr"));
		
	}
	

}
