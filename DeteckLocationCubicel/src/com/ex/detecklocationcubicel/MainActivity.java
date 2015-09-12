package com.ex.detecklocationcubicel;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button btn;
	private LocationManager lm;
	
	public final double r = 6378.137; //radius of earth
	
	public final double lat1 = -7.45696642;
	public final double lat2 = -7.45689794;
	public final double lat3 = -7.4568824;
	public final double lat4 = -7.45688775;
	public final double lon1 = 110.70075186;
	public final double lon2 = 110.70053327;
	public final double lon3 = 110.70054854;
	public final double lon4 = 110.70056564;
	private double lon,lat;
	private Animation anim;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODOs Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		anim = AnimationUtils.loadAnimation(this, R.anim.translate_and_spin);
		anim.reset();
		
		btn = (Button)findViewById(R.id.buttonLogin);
		btn.setText("Log In");
		hideButton();
		//btn.setVisibility(View.INVISIBLE);
		
		lm =(LocationManager)getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locListen);
	}
	
	LocationListener locListen = new LocationListener() {
		
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODOs Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String arg0) {
			// TODOs Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String arg0) {
			// TODOs Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location loc) {
			// TODOs Auto-generated method stub
			lon = loc.getLongitude();
			lat = loc.getLatitude();
			double dlat1,dlat2,dlat3,dlat4,dlon1,dlon2,dlon3,dlon4,a1,a2,a3,a4,c1,c2,c3,c4;
			dlat1 = (lat1 -lat)* Math.PI/180;
			dlat2 = (lat2 - lat)* Math.PI/180;
			dlat3 = (lat3 - lat)* Math.PI/180;
			dlat4 = (lat4 - lat)* Math.PI/180;
			dlon1 = (lon1 -lon)* Math.PI/180;
			dlon2 = (lon2 -lon)* Math.PI/180;
			dlon3 = (lon3 -lon)* Math.PI/180;
			dlon4 = (lon4 -lon)* Math.PI/180;
			a1 = Math.sin(dlat1/2)* Math.sin(dlat1/2)+ Math.cos(lat * Math.PI/180)* 
					Math.cos(lat1 * Math.PI/180)* Math.sin(dlon1/2)* Math.sin(dlon1/2);
			a2 = Math.sin(dlat2/2)* Math.sin(dlat2/2)+ Math.cos(lat * Math.PI/180)* 
					Math.cos(lat2 * Math.PI/180)* Math.sin(dlon2/2)* Math.sin(dlon2/2);
			a3 = Math.sin(dlat3/2)* Math.sin(dlat3/2)+ Math.cos(lat * Math.PI/180)* 
					Math.cos(lat3 * Math.PI/180)* Math.sin(dlon3/2)* Math.sin(dlon3/2);
			a4 = Math.sin(dlat4/2)* Math.sin(dlat4/2)+ Math.cos(lat * Math.PI/180)* 
					Math.cos(lat4 * Math.PI/180)* Math.sin(dlon4/2)* Math.sin(dlon4/2);
			c1 = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1-a1));
			c2 = 2 * Math.atan2(Math.sqrt(a2), Math.sqrt(1-a2));
			c3 = 2 * Math.atan2(Math.sqrt(a3), Math.sqrt(1-a3));
			c4 = 2 * Math.atan2(Math.sqrt(a4), Math.sqrt(1-a4));
			double periksa1 = c1 * r * 1000; //meter 
			double periksa2 = c2 * r * 1000;
			double periksa3 = c3 * r * 1000;
			double periksa4 = c4 * r * 1000;
			if((periksa1 <= 4)||(periksa2 <= 4)||
			(periksa3 <= 4)||(periksa4 <= 4)){
				showTheButton();
			}else{
				
				hideButton();
				
			}
			
		}
	

	};
	
	private void hideButton() {
			// TODOs Auto-generated method stub
		btn.setVisibility(View.INVISIBLE);
		//btn.clearAnimation();
		//btn.setAnimation(anim);
		}
	private void showTheButton() {
		// TODOs Auto-generated method stub
		btn.setVisibility(View.VISIBLE);
		btn.clearAnimation();
		btn.setAnimation(anim);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODOs Auto-generated method stub
		super.onPause();
		lm.removeUpdates(locListen);
	}
	
	
	
}
