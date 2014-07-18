package com.citybike.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.Resources;

public class ParserCSV {
	
	private static Resources recursos;
	
	public static void cargarParser(Resources recursos){
		ParserCSV.recursos = recursos;
	}
	
	public static ArrayList<String[]> parsearArchivo(String nombreArchivo, int cantCampos){
		BufferedReader br = null;
		ArrayList<String[]> lineas = new ArrayList<String[]>();
		
		try{
			InputStreamReader isr = new InputStreamReader( recursos.getAssets().open(nombreArchivo) );
			br = new BufferedReader( isr );
			
			String linea = null;
			String[] campos;
			while( (linea = br.readLine()) != null ){
				campos = linea.split(";");
				lineas.add(campos);
			}
			br.close();
		}catch(Exception e){
			System.out.println("Error al parsear archivo");
		}
		
		return lineas;
	}
}
