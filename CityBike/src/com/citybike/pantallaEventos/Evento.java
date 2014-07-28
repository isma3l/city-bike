package com.citybike.pantallaEventos;

public class Evento {
	private String id;
	private int idImage;
	private String title;
	private String date;
	
	
	public String getId() {
		return id;
	}


	public int getIdImage() {
		return idImage;
	}


	public String getTitle() {
		return title;
	}


	public String getDate() {
		return date;
	}


	public Evento(String id, int idImage, String title, String date) {
		super();
		this.id = id;
		this.idImage = idImage;
		this.title = title;
		this.date = date;
	}

}
