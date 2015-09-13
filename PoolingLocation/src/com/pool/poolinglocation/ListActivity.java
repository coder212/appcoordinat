package com.pool.poolinglocation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {

	ListView lv;
	TextView tv;
	Button bt;
	LocationAdapter adapter;
	String[] projection= {"Latitude_row","Longitude_row","Altitude_row","State_id"};
	private DatabaseHelper locationDb;
	Animation a;
	private ArrayList<LocationItem> data = new ArrayList<LocationItem>();
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listlayout);
		locationDb = new DatabaseHelper(this);
		a = AnimationUtils.loadAnimation(this, R.anim.turba);
		a.reset();
		tv = (TextView)findViewById(R.id.emptydata);
		bt = (Button)findViewById(R.id.erase_all);
		bt.setText("Erase All Data");
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODOs Auto-generated method stub
				ListActivity.this.getContentResolver().delete(Uri.parse("content://com.pool.poolinglocation.databaseprovider/ele"),null,null);
				adapter.notifyDataSetChanged();
			}
		});
		bt.clearAnimation();
		bt.setAnimation(a);
		lv = (ListView)findViewById(R.id.live);
		adapter = new LocationAdapter(this, R.layout.row_layout, data);
		lv.setAdapter(adapter);
		lv.clearAnimation();
		lv.setAnimation(a);
		if(exists()){
			
			if(data.size()>0){
				data.clear();
			}
			tv.setVisibility(View.INVISIBLE);
			setList();
			
		}else{
			
			tv.clearAnimation();
			tv.setAnimation(a);
			
		}
		adapter.notifyDataSetChanged();
		
	}

	private void setList(){
		
		Uri url = Uri.parse("content://com.pool.poolinglocation.databaseprovider/ele");
		Cursor cursor = ListActivity.this.getContentResolver().query(url, projection, null, null, null);
		cursor.moveToFirst();
		int i=1;
		do {

			double lat = cursor.getDouble(cursor.getColumnIndex("Latitude_row"));
			double lon = cursor.getDouble(cursor.getColumnIndex("Longitude_row"));
			double alt = cursor.getDouble(cursor.getColumnIndex("Altitude_row"));
			String latS = String.valueOf(lat);
			Log.d("Kontak", latS);
			String lonS = String.valueOf(lon);
			Log.d("Kontak", lonS);
			String altS = String.valueOf(alt);
			String nomer = Integer.toString(i)+".";
			data.add(new LocationItem(latS, lonS, altS, nomer));
			i++;

		} while (cursor.moveToNext());
		adapter.notifyDataSetChanged();
		cursor.close();
	}
	
	private boolean exists(){
		SQLiteDatabase db = locationDb.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from Location", null);
		if(cursor !=null && cursor.getCount()>0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODOs Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODOs Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.getlocation:
			startActivity(new Intent(ListActivity.this,LocationActivity.class));
			ListActivity.this.finish();
			return true;
		case R.id.listlocation:
			startActivity(new Intent(ListActivity.this,ListActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	

}
