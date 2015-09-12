package com.pool.poolinglocation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class LocationActivity extends Activity {

	private TextView textLat, textLon, textAlt;
	private double lat, lon, alt;
	Animation a;
	LocationManager locationManager;

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
		a = AnimationUtils.loadAnimation(this, R.anim.zoomout);
		a.reset();
		textLat = (TextView) findViewById(R.id.lattext);
		setAnimationLat();
		textLon = (TextView) findViewById(R.id.lontext);
		setAnimationLon();
		textAlt = (TextView) findViewById(R.id.alttext);
		setAnimationAlt();

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2,
				locationListener);

	}

	private void setAnimationAlt() {
		textAlt.clearAnimation();
		textAlt.setAnimation(a);
	}

	private void setAnimationLon() {
		textLon.clearAnimation();
		textLon.setAnimation(a);
	}

	private void setAnimationLat() {
		textLat.clearAnimation();
		textLat.setAnimation(a);
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
			
			if((lat >0) || (lon >0) || (alt > 0 )){
				
				ContentValues value = new ContentValues();
				value.put("Latitude_row", lat);
				value.put("Longitude_row", lon);
				value.put("Altitude_row", alt);
				LocationActivity.this.getContentResolver().insert(Uri.parse("content://com.pool.poolinglocation.databaseprovider/ele"), value);
				
				textLat.setText(String.valueOf(lat));
				setAnimationLat();
				textLon.setText(String.valueOf(lon));
				setAnimationLon();
				textAlt.setText(String.valueOf(alt));
				setAnimationAlt();
				
			}

			// Log.d(TAG, "Latitude: " + String.valueOf(latitude));
			// Log.d(TAG, "Longitude: " + String.valueOf(longitude));
			// /Log.d(TAG, "Altitude: " + String.valueOf(altitude));


		}
	};

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
			startActivity(new Intent(LocationActivity.this,ListActivity.class));
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
