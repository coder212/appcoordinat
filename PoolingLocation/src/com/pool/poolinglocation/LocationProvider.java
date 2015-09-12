package com.pool.poolinglocation;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class LocationProvider extends ContentProvider {

	public static final Uri BASE_URI = Uri.parse("content://com.pool.poolinglocation.databaseprovider/ele");
	public static final String Base = "com.pool.poolinglocation.databaseprovider";
	private static final int ALL_ROWS = 1;
	private static final int LATITUDE = 2;
	private static final int LATITUDE_DELETE = 3;
	private static final int LATITUDE_FILTER = 4;
	private static final String CALLER_IS_SYNC_ADAPTER = "location";
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Base, "ele", ALL_ROWS);
		uriMatcher.addURI(Base, "ele/lst/*", LATITUDE);
		uriMatcher.addURI(Base, "ele/del", LATITUDE_DELETE);
		uriMatcher.addURI(Base, "ele/filter/*", LATITUDE_FILTER);
	}
	
	private boolean CallerIsSyncAdapter(Uri uri){
		final String is_sync_adapter = uri.getQueryParameter(CALLER_IS_SYNC_ADAPTER);
		return is_sync_adapter != null && !is_sync_adapter.equals("0");
	}
	
	private DatabaseHelper locatedb;
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODOs Auto-generated method stub
		int UriType = uriMatcher.match(uri);
		SQLiteDatabase db = locatedb.getWritableDatabase();
		int rowsDeleted = 0;
		switch (UriType){
		    case ALL_ROWS:
			    rowsDeleted = db.delete(locatedb.LOCATION_TABLE_NAME,selection,selectionArgs);
			    break;
		    case LATITUDE_DELETE:
			    rowsDeleted = db.delete(locatedb.LOCATION_TABLE_NAME,locatedb.LATITUDE_ROW+"= '"+selection+"'", selectionArgs);
			    break;
	 	    default :
			    throw new IllegalArgumentException("Unknown Uri : "+uri);
		}
		getContext().getContentResolver().notifyChange(uri,null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri arg0) {
		// TODOs Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODOs Auto-generated method stub
		Log.d("ListProvider","firsttime");
		int uritype = uriMatcher.match(uri);
		SQLiteDatabase db = locatedb.getWritableDatabase();
		long id = 0;
		switch(uritype){
		    case ALL_ROWS:
		    	id = db.insert(locatedb.LOCATION_TABLE_NAME, null, values);
		        break;
		    default :
		    	throw new IllegalArgumentException("unKnown uri : "+uri);
		}
		getContext().getContentResolver().notifyChange(uri,null,!CallerIsSyncAdapter(uri));
		return Uri.parse("element"+"/"+id);
	}

	@Override
	public boolean onCreate() {
		// TODOs Auto-generated method stub
		locatedb = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// TODOs Auto-generated method stub
		Log.d("ListProvider",uri.toString());
		SQLiteQueryBuilder queryB = new SQLiteQueryBuilder();
		checkColumns(projection);
		queryB.setTables(locatedb.LOCATION_TABLE_NAME);
		int uriType = uriMatcher.match(uri);
		Log.d("ListProvider", "uriType "+uriType);
		switch (uriType) {
		case ALL_ROWS:
			
			break;
		
		case LATITUDE:
			
			queryB.appendWhere(locatedb.LATITUDE_ROW+"= '"+uri.getLastPathSegment()+"'");
			break;
		
		case LATITUDE_FILTER:
			
			queryB.appendWhere(locatedb.LATITUDE_ROW+" LIKE '%"+uri.getLastPathSegment()+"%'");
			break;

		default:
			throw new IllegalArgumentException("Unknown uri : "+uri);
		}
		SQLiteDatabase db = locatedb.getReadableDatabase();
		Cursor cursor = queryB.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	private void checkColumns(String[] projection) {
		// TODOs Auto-generated method stub
		String[] avail = {locatedb.LATITUDE_ROW,locatedb.LONGITUDE_ROW,locatedb.ALTITUDE_ROW};
		if (projection != null){
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availColumns = new HashSet<String>(Arrays.asList(avail));
			if(!availColumns.containsAll(requestedColumns)){
				throw new IllegalArgumentException("proyeksi database tak dikenal.");
			}
		}
		
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODOs Auto-generated method stub
		return 0;
	}

}
