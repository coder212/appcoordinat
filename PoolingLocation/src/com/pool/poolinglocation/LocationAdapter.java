package com.pool.poolinglocation;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LocationAdapter extends ArrayAdapter<LocationItem> {
	
	private Activity context;
	private int resId;
	RowHandle rowH;
	private ArrayList<LocationItem> data = new ArrayList<LocationItem>();

	public LocationAdapter(Activity context, int resource,ArrayList<LocationItem> data) {
		super(context, resource, resource,data);
		// TODOs Auto-generated constructor stub
		this.context = context;
		this.resId = resource;
		this.data = data;
		
	}
	
	
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODOs Auto-generated method stub
		View row = convertView;
		rowH = null;
		if(row==null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(resId, parent, false);
			rowH = new RowHandle();
			rowH.numb = (TextView)row.findViewById(R.id.no_row);
			rowH.lat = (TextView)row.findViewById(R.id.lat_row);
			rowH.longi = (TextView)row.findViewById(R.id.lon_row);
			rowH.alti = (TextView)row.findViewById(R.id.alti_row);
			
			row.setTag(rowH);
			
		}else{
			rowH = (RowHandle)row.getTag();
		}
		
		LocationItem item = data.get(position);
		rowH.numb.setText(item.getNumber());
		rowH.lat.setText("Lat   : "+item.getLatitude());
		rowH.longi.setText("Lon : "+item.getLongitude());
		rowH.alti.setText("Alti : "+item.getAltitude());
		
		return row;
	}



	static class RowHandle{
		TextView numb,lat,longi,alti;
	}

}
