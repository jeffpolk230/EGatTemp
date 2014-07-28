package asia.ait.egatfragment;

import static asia.ait.egatfragment.CapacitorDbConstants.*;
import static android.provider.BaseColumns._ID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	private final static String DATABASE_NAME = "egat.db";
	private final static int DATABASE_VERSION = 1;
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
								  _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
								  JOB_NUMBER + " CHAR, " +
								  SERIAL_NUMBER + " CHAR, " +
								  ALIAS + " CHAR);"; 
		db.execSQL(INIT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

	public void delDB(SQLiteDatabase db)
	{
		final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(DROP_TABLE);
		this.onCreate(db);
	}

}
