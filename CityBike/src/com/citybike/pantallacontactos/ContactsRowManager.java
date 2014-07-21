package com.citybike.pantallacontactos;

public class ContactsRowManager {
	private String name;
	private boolean selected;

	public ContactsRowManager(String name) {
	    this.name = name;
	    selected = false;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public boolean isSelected() {
	    return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
} 
