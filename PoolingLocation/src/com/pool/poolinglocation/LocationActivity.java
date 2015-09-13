package com.pool.poolinglocation;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends Activity {

	private double lat, lon, alt;
	private Button login;
	String[] projection= {"Latitude_row","Longitude_row","Altitude_row","State_id"};
	ArrayList<LocationItem> data = new ArrayList<LocationItem>();
	public final double r = 6378.137; //radius of earth
	LocationManager locationManager;
	private DatabaseHelper locationDb;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		locationDb = new DatabaseHelper(this);

		login = (Button)findViewById(R.id.buttonLogin);
		login.setVisibility(View.INVISIBLE);
		login.setText("Log In");
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2,
				locationListener);

	}

	LocationListener locationListener = new LocationListener() {
		public void onProviderDisabled(String arg0) {
			// TODOs Auto-generated method stub
		}

		public void onProviderEnabled(String arg0) {
			// TODOs Auto-generated method stub
		}

		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODOs Auto-generated method stub
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODOs Auto-generated method stub
			lat = location.getLatitude();
			lon = location.getLongitude();
			alt = location.getAltitude();
			
				
				if(exists()){
					Uri url = Uri.parse("content://com.pool.poolinglocation.databaseprovider/ele");
					Cursor cursor = LocationActivity.this.getContentResolver().query(url, projection, null, null, null);
					cursor.moveToLast();
					int test = cursor.getInt(cursor.getColumnIndex("State_id"));
					if(test == 1){
						createAlertBuilder(2);
					}else if(test == 2){
						createAlertBuilder(3);
					}else if(test == 3){
						createAlertBuilder(4);
					}else if(test == 4){
						Log.d("PAAR", "check in this ");
						setList();
					    double lat_acuan1=7,lat_acuan2=7,lat_acuan3=7,lat_acuan4=7,lon_acuan1=7,lon_acuan2=7,lon_acuan3=7,lon_acuan4=7;
						for(LocationItem item : data){
							int tn = Integer.valueOf(item.getNumber());
							Log.d("PAAR", "number: " + tn);
							if(tn == 1){
								lat_acuan1 = Double.valueOf(item.getLatitude());
								lon_acuan1 = Double.valueOf(item.getLongitude());
								Log.d("PAAR", "latAcuan1 " +lat_acuan1+" lonacuan1"+lon_acuan1 );
							}else if(tn == 2){
								lat_acuan2 = Double.valueOf(item.getLatitude());
								lon_acuan2 = Double.valueOf(item.getLongitude());
							}else if(tn == 3){
								lat_acuan3 = Double.valueOf(item.getLatitude());
								lon_acuan3 = Double.valueOf(item.getLongitude());
							}else if(tn == 4){
								lat_acuan4 = Double.valueOf(item.getLatitude());
								lon_acuan4 = Double.valueOf(item.getLongitude());
							}
						}
						Log.d("PAAR", "Latitude: " +lat_acuan1);
						if((getDistance(lat_acuan1, lon_acuan1) <= 6)||(getDistance(lat_acuan2, lon_acuan2)<= 6)||(getDistance(lat_acuan3, lon_acuan3)<= 6)||(getDistance(lat_acuan4, lon_acuan4)<= 6)){
							login.setVisibility(View.VISIBLE);
						}else{
							login.setVisibility(View.INVISIBLE);
						}
						
					}else{
						
					}
				}else{
					
					createAlertBuilder(1);
					
				}

			 Log.d("PAAR", "Latitude: " + String.valueOf(lat));
			 Log.d("PAAR", "Longitude: " + String.valueOf(lon));
			 Log.d("PAAR", "Altitude: " + String.valueOf(alt));


		}
	};
	
	private void insertValues(double lat, double lon, double alt, int state){
		
		ContentValues value = new ContentValues();
		value.put("Latitude_row", lat);
		value.put("Longitude_row", lon);
		value.put("Altitude_row", alt);
		value.put("State_id", state);
		LocationActivity.this.getContentResolver().insert(Uri.parse("content://com.pool.poolinglocation.databaseprovider/ele"), value);
		
	}
	
private void setList(){
		
		Uri url = Uri.parse("content://com.pool.poolinglocation.databaseprovider/ele");
		Cursor cursor = LocationActivity.this.getContentResolver().query(url, projection, null, null, null);
		cursor.moveToFirst();
		do {

			double lat = cursor.getDouble(cursor.getColumnIndex("Latitude_row"));
			double lon = cursor.getDouble(cursor.getColumnIndex("Longitude_row"));
			double alt = cursor.getDouble(cursor.getColumnIndex("Altitude_row"));
			int state = cursor.getInt(cursor.getColumnIndex("State_id"));
			String latS = String.valueOf(lat);
			Log.d("Kontak", latS);
			String lonS = String.valueOf(lon);
			Log.d("Kontak", lonS);
			String altS = String.valueOf(alt);
			String nomer = String.valueOf(state);
			data.add(new LocationItem(latS, lonS, altS, nomer));

		} while (cursor.moveToNext());
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
	
	private double getDistance(double lat_acuan, double lon_acuan){
		double dlat1,dlon1,a1,c1;
		dlat1 = (lat_acuan -lat)* Math.PI/180;
		dlon1 = (lon_acuan -lon)* Math.PI/180;
		a1 = Math.sin(dlat1/2)* Math.sin(dlat1/2)+ Math.cos(lat * Math.PI/180)* 
				Math.cos(lat_acuan * Math.PI/180)* Math.sin(dlon1/2)* Math.sin(dlon1/2);
		c1 = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1-a1));
		double periksa = c1 * r * 1000; //meter 
		return periksa;
	}
	
	private void createAlertBuilder(int state) {
		AlertDialog.Builder alert = new AlertDialog.Builder(LocationActivity.this);

		final int savestate = state;
		//alert.setMessage("akan menyimpan untuk koordinat ke-"+savestate);
		final TextView vie = new TextView(LocationActivity.this);
		vie.setText("akan menyimpan untuk koordinat ke-"+savestate);
		alert.setTitle("Save");
		alert.setView(vie);
		alert.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// What ever you want to do with the value

						insertValues(lat, lon, alt, savestate);
						
						
						dialog.cancel();
					}

				});

		alert.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// what ever you want to do with No option.
						dialog.cancel();
					}
				});

		alert.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODOs Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODOs Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.getlocation:
			startActivity(new Intent(LocationActivity.this,LocationActivity.class));
			LocationActivity.this.finish();
			return true;
		case R.id.listlocation:
			startActivity(new Intent(LocationActivity.this,ListActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODOs Auto-generated method stub
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODOs Auto-generated method stub
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,
				2, locationListener);
	}

}
