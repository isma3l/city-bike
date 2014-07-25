package com.citybike.pantallacontactos;

public class ContactModel {
	private String name;
	private String mail;
	private boolean selected;

	public ContactModel(String name, String mail) {
	    this.name = name;
	    this.mail = mail;
	    selected = false;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}
	
	public String getMail() {
	    return mail;
	}

	public boolean isSelected() {
	    return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
} 
