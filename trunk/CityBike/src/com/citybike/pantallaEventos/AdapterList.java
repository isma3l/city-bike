package com.citybike.pantallaEventos;

import java.util.List;

import com.citybike.R;
import com.citybike.pantallainicio.Fragments.FragmentEventos;
import com.citybike.utils.LogWrapper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterList extends BaseAdapter {
	private int idLayoutView;
	private Context context;

	public AdapterList(Fragment fragment) {
		super();
		context = fragment.getActivity();
		idLayoutView = R.layout.element_list_events;
	}
	
    public AdapterList(Context context, int idLayoutView, List<Evento> inputs) {
        super();
        this.context = context;
        this.idLayoutView = idLayoutView;
    }
    
	@Override
	public int getCount() {
		return Eventos.myEvents.size();
	}

	@Override
	public Object getItem(int position) {		
		return Eventos.myEvents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(idLayoutView, null);
		}
		onInput(Eventos.myEvents.get(position), convertView);
		return convertView;
	}   
	
	public void onInput (Evento input, View view) {
		TextView title, content;
		ImageView imagen;

		if(input != null) {
			if((title = (TextView)view.findViewById(R.id.id_text_title_events)) != null ) {
				title.setText(input.getTitle());
			}
			if((content = (TextView)view.findViewById(R.id.id_text_content_events)) != null ) {
				content.setText(input.getDate());
			}
			if((imagen = (ImageView)view.findViewById(R.id.id_image_event)) != null) {
				imagen.setImageResource(input.getIdImage());
			}
		}
	}

}
