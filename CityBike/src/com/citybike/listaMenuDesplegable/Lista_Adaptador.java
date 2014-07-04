package com.citybike.listaMenuDesplegable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.citybike.R;


public class Lista_Adaptador extends BaseAdapter {
    private List<Entrada_Lista> entradas;
    private int R_layout_IdView;
    private Context contexto;

    public Lista_Adaptador(Context contexto, int R_layout_IdView, List<Entrada_Lista> entradas) {
        super();
        this.contexto = contexto;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        onEntrada (entradas.get(posicion), view);
        return view;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int posicion) {
        return entradas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    /** 
     * @param entrada es una instancia de Entrada_lista y será asociada a la view.
     *  La entrada es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */
    public void onEntrada(Object entrada, View view) {        
        TextView descripcion = (TextView) view.findViewById(R.id.id_titulo_list);
        ImageView imagen = (ImageView) view.findViewById(R.id.id_imagen_list);

        if(descripcion != null) {
            descripcion.setText(((Entrada_Lista)entrada).getTitulo());
        }
        if(imagen != null) {
        	imagen.setImageResource(((Entrada_Lista)entrada).getIdImagen());
        
        }
    }
    
}
