package com.citybike.listaMenuDesplegable;

/*
 * clase para guardar el contenido de cada item del menu desplegable
 */

public class Entrada_Lista {
    private int idImagen;
    private String titulo;    

    public Entrada_Lista (int idImagen, String titulo) {
        this.idImagen = idImagen;
        this.titulo = titulo;
    }


    public String getTitulo() {
    	return titulo;
    }

    public int getIdImagen() {
        return idImagen;
    }
}
