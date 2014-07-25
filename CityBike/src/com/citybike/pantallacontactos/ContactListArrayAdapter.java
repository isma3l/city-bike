package com.citybike.pantallacontactos;

import java.util.List;

import com.citybike.R;

import android.widget.ArrayAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class ContactListArrayAdapter extends ArrayAdapter<ContactModel> {

	  private final List<ContactModel> list;
	  private final Fragment context;

	  public ContactListArrayAdapter(Fragment context, List<ContactModel> list) {
		  super(context.getActivity() , R.layout.contacts_row_layout, list);
		  this.context = context;
		  this.list = list;
	  }

	  static class ViewHolder {
		  protected TextView text;
		  protected CheckBox checkbox;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  View view = null;
		  if (convertView == null) {
		      LayoutInflater inflator = context.getActivity().getLayoutInflater();
		      view = inflator.inflate(R.layout.contacts_row_layout , null);
		      final ViewHolder viewHolder = new ViewHolder();
		      viewHolder.text = (TextView) view.findViewById(R.id.label);
		      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
		      viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		    	  @Override
		    	  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		    		  ContactModel element = (ContactModel) viewHolder.checkbox.getTag();
		              element.setSelected(buttonView.isChecked());
		    	  }
	          });
		      view.setTag(viewHolder);
		      viewHolder.checkbox.setTag(list.get(position));
		  } else {
			  view = convertView;
			  ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		  }
		  ViewHolder holder = (ViewHolder) view.getTag();
		  holder.text.setText(list.get(position).getName());
		  holder.checkbox.setChecked(list.get(position).isSelected());
		  return view;
	}
}
