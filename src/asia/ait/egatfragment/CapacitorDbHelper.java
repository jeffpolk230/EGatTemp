package asia.ait.egatfragment;

import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*
 * Combining Constents and SQLiteOpenHelper
 * This should serve as final version, DbHelper is rather quick dirty version.
 * 2014 July 8th
 * Jeff C.
 * 
 * */
public class CapacitorDbHelper extends SQLiteOpenHelper {

	private final static String TAG = "CapacitorDbHelper";
	
	// Defining some basic common strings
	private final static String DATABASE_NAME = "egat_cbank.db";
	public static final String SQLITE_TABLE = "capacitors";
	private final static int DATABASE_VERSION = 1;
	
	// This is the place you should look for change if you want to expand DB functions
	public static final String KEY_JOB_NUMBER = "jobNumber";
	public static final String KEY_SUBSTATION = "substation";
	public static final String KEY_LEVELVOLTAGE = "levelVoltage";
	public static final String KEY_RATED = "rated";
	public static final String KEY_SERIAL_NUMBER = "serialNumber";
	public static final String KEY_ALIAS = "alias";
	
	// Don't touch this String if you don't know what's it about!
	// This is still normal SQLite, if you want to use Full-Text-Search please look FTS3 version
	// For now all fields are free from limit, Contact EGat if they ever want some. This shouldn't be an issue unless using FTS3
	private static final String DATABASE_CREATE =
			  "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
			  _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			  KEY_JOB_NUMBER + "," +
			  KEY_SUBSTATION + "," +
			  KEY_LEVELVOLTAGE + "," +
			  KEY_RATED + "," +
			  KEY_SERIAL_NUMBER + "," +
			  KEY_ALIAS + ");" ;
	
	
	// Functions starting from here!
	
	public CapacitorDbHelper(Context context){
		super(context,DATABASE_NAME , null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w(TAG, DATABASE_CREATE);
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				     + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
		onCreate(db);
	}
	

}
