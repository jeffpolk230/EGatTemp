package asia.ait.egatfragment;

import static android.provider.BaseColumns._ID;
import static asia.ait.egatfragment.CapacitorDbConstants.TABLE_NAME;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CapacitorDbAdapter {

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
	
	// Readings from measurement
	public static final String KEY_MEASURINGVOLTAGE = "measuringVoltage";
	public static final String KEY_FREQUENCY = "frequency";
	public static final String KEY_CURRENT = "current";
	public static final String KEY_CAPACITORVALUE = "capacitorValue";
	public static final String KEY_ERROR = "error";
	
	
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
			  KEY_SERIAL_NUMBER + "," + // This ought to be unique, right?
			  KEY_ALIAS + "," +
			  // Measuring part, should enforce some limits?
			  KEY_MEASURINGVOLTAGE + "," +
			  KEY_FREQUENCY + "," +
			  KEY_CURRENT + "," +
			  KEY_CAPACITORVALUE + "," +
			  KEY_ERROR + ");" ;

	// DBHelper Class, used to be separated, now integrated as private class
	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		 DatabaseHelper(Context context) {
		   super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
		  public void delDB(SQLiteDatabase db)
			{
				final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
				db.execSQL(DROP_TABLE);
				this.onCreate(db);
			}
	 }
	
	// Adapter helping functions
	// Handles CRUD and some testing inputs
	
	 private DatabaseHelper mDbHelper;
	 public SQLiteDatabase mDb;
	 public final Context mCtx;
	 
	public CapacitorDbAdapter(Context context){this.mCtx = context;}
	public CapacitorDbAdapter open() throws SQLException{
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	public void close(){
		if(mDbHelper != null)
			mDbHelper.close();
	}
	public long createCapacitor(String jobNumber,
								String sub,
								String voltage,
								String rated,
								String serial,
								String alias,
								String measuringVolt,
								String frequency,
								String current,
								String capacitorValue,
								String error)
	{
		ContentValues value = new ContentValues();
		value.put(KEY_JOB_NUMBER, jobNumber);
		value.put(KEY_SUBSTATION, sub);
		value.put(KEY_LEVELVOLTAGE, voltage);
		value.put(KEY_RATED, rated);
		value.put(KEY_SERIAL_NUMBER, serial);
		value.put(KEY_ALIAS, alias);
		value.put(KEY_MEASURINGVOLTAGE, measuringVolt);
		value.put(KEY_FREQUENCY, frequency);
		value.put(KEY_CURRENT, current);
		value.put(KEY_CAPACITORVALUE, capacitorValue);
		value.put(KEY_ERROR, error);
		
		long output = mDb.insert(SQLITE_TABLE, null, value);
		return output;
	}
	public boolean deleteAll(){
		return mDb.delete(SQLITE_TABLE, null,null) > 0;
	}
	public void dropDB(){
		mDbHelper.delDB(mDb);
	}
	public Cursor fetchAllCapacitors(){
		String[] columns = new String[]{_ID, KEY_JOB_NUMBER, KEY_SUBSTATION, KEY_LEVELVOLTAGE, KEY_RATED,
								KEY_SERIAL_NUMBER, KEY_ALIAS, KEY_MEASURINGVOLTAGE, KEY_FREQUENCY, KEY_CURRENT, KEY_CAPACITORVALUE, KEY_ERROR};
		Cursor cursor = mDb.query(SQLITE_TABLE, columns, null, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		return cursor;
	}
	
	public void insertTestCapacitors(){
		createCapacitor("jobNumber","SubStation", "LevelVoltage","rated","serial","alias","measuringVolt","frequency","current","capacitorValue","error");
		createCapacitor("11-9-0833","BangkokNorth", "230","235","388-221","C12","220v","50","2.237","27.5324","1.67");
		createCapacitor("egat-23-0113","Phuket", "115","130","380-220","C1","220v","50","2.237","27.5324","1.67");
		createCapacitor("ait-testing-999","Tart", "230","235","009-222","C102","220v","50","2.237","27.5324","1.67");
		createCapacitor("11-9-0833","Rayong", "115","120","119-083","C23","220v","50","2.237","27.5324","1.67");
	}
	
	// TO-DO Fetch by jobNumber
	 public Cursor fetchCountriesByJobNumber(String inputText) throws SQLException {
		  Log.w(TAG, inputText);
		  Cursor mCursor = null;
		  String[] columns = new String[]{_ID, KEY_JOB_NUMBER, KEY_SUBSTATION, KEY_LEVELVOLTAGE, KEY_RATED,
					KEY_SERIAL_NUMBER, KEY_ALIAS, KEY_MEASURINGVOLTAGE, KEY_FREQUENCY, KEY_CURRENT, KEY_CAPACITORVALUE, KEY_ERROR};
		  if (inputText == null  ||  inputText.length () == 0)  {
			  mCursor = mDb.query(SQLITE_TABLE, columns, null , null, null, null, null);
		  }
		  else {
		   mCursor = mDb.query(true, SQLITE_TABLE, columns, 
				   KEY_JOB_NUMBER + " like '%" + inputText + "%'", null,
		     null, null, null, null);
		  }
		  if (mCursor != null) {
		   mCursor.moveToFirst();
		  }
		  return mCursor;
		 
	}
	// TO-DO Fetch by Capacitor Serial number (Unique)
	 public Cursor fetchCountriesBySerialNumber(String inputText) throws SQLException {
		  Log.w(TAG, inputText);
		  Cursor mCursor = null;
		  String[] columns = new String[]{_ID, KEY_JOB_NUMBER, KEY_SUBSTATION, KEY_LEVELVOLTAGE, KEY_RATED,
					KEY_SERIAL_NUMBER, KEY_ALIAS, KEY_MEASURINGVOLTAGE, KEY_FREQUENCY, KEY_CURRENT, KEY_CAPACITORVALUE, KEY_ERROR};
		  if (inputText == null  ||  inputText.length () == 0)  {
			  mCursor = mDb.query(SQLITE_TABLE, columns, null , null, null, null, null);
		  }
		  else {
		   mCursor = mDb.query(true, SQLITE_TABLE, columns, 
				   KEY_SERIAL_NUMBER + " like '%" + inputText + "%'", null,
		     null, null, null, null);
		  }
		  if (mCursor != null) {
		   mCursor.moveToFirst();
		  }
		  return mCursor;
		 
	}
	// TO-DO delete selected one
	public void deleteSelected(String serialNumber){
		mDb.delete(SQLITE_TABLE, KEY_SERIAL_NUMBER + "="+ serialNumber , null);
	}
	// TO-DO update
	// TO-DO Fetch by other filters?
}
