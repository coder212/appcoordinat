package com.pool.poolinglocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAMA = "locate.db";
	public static final int DATABASE_VERSION = 2;
	
	public final String ID_ROW = "Id";
	public final String LATITUDE_ROW = "Latitude_row";
	public final String LONGITUDE_ROW = "Longitude_row";
	public final String ALTITUDE_ROW = "Altitude_row";
	
	public final String LOCATION_TABLE_NAME = "Location";
	
	private final String CREATE_TABLE_LOCATION = "create table "+LOCATION_TABLE_NAME+"("
			+ID_ROW+" integer primary key, "+LATITUDE_ROW+" real not null, "+LONGITUDE_ROW+" real not null, "+ALTITUDE_ROW+" real not null);";
	
	private final String ONUPGRADE_DB = "drop table if exists "+LOCATION_TABLE_NAME;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAMA, null, DATABASE_VERSION);
		// TODOs Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODOs Auto-generated method stub
		db.execSQL(CREATE_TABLE_LOCATION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldDb, int newDb) {
		// TODOs Auto-generated method stub
		db.execSQL(ONUPGRADE_DB);
		onCreate(db);
	}

}
