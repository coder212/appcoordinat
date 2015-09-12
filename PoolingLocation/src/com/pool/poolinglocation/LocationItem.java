package com.pool.poolinglocation;

public class LocationItem {
	
	private String number;
	private String latitude;
	private String longitude;
	private String altitude;
	
	public LocationItem(String latitude, String longitude, String altitude, String number){
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.number = number;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAltitude() {
		return altitude;
	}
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	

}
