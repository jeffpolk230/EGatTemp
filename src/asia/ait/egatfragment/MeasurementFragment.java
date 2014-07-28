package asia.ait.egatfragment;

import static android.provider.BaseColumns._ID;
import static asia.ait.egatfragment.CapacitorDbConstants.ALIAS;
import static asia.ait.egatfragment.CapacitorDbConstants.JOB_NUMBER;
import static asia.ait.egatfragment.CapacitorDbConstants.SERIAL_NUMBER;
import static asia.ait.egatfragment.CapacitorDbConstants.TABLE_NAME;

import java.util.Random;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MeasurementFragment extends Fragment implements OnClickListener{
	
	public CapacitorDbAdapter dbhelper;
	private Button takeMeasure;
	private Button addCapacitor;
	private Button delDB; //For testing purpose only
	private TextView measuringErrorRate;
	private TextView measuringCapValue;
	private TextView measuringCurrent;
	private TextView measuringVoltage;
	private TextView measuringFrequency;
	private EditText editJobNumber;
	private EditText editCapSerialNo;
	private EditText editAlias;
	private Spinner substationSpinner;
	private Spinner levelVoltageSpinner;
	private Spinner ratedSpinner;
	private ArrayAdapter substationAdapter;
	private ArrayAdapter voltAdapter;
	private ArrayAdapter ratedAdapter;
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle savedInstanceState){
		View v = inf.inflate(R.layout.measurement_fragment_temp, container, false);
		initView(v);
		dbhelper = new CapacitorDbAdapter(getActivity());
		dbhelper.open();
//		FOR TESTING: PLEASE REMOVE for normal use 
		dbhelper.deleteAll();
		dbhelper.insertTestCapacitors();

		show();
		return v;
	}

	public CapacitorDbAdapter getDB(){
		return this.dbhelper;
	}
	
	public void initView(View v){
		measuringErrorRate = (TextView) v.findViewById(R.id.measuringErrorRate);
		measuringCapValue = (TextView) v.findViewById(R.id.measuringCapValue);
		measuringCurrent = (TextView) v.findViewById(R.id.measuringCurrent);
		measuringVoltage = (TextView) v.findViewById(R.id.measuringVoltage);
		measuringFrequency = (TextView) v.findViewById(R.id.measuringFrequency);
		addCapacitor = (Button) v.findViewById(R.id.addCapacitor);
		takeMeasure = (Button) v.findViewById(R.id.takingMeasureButton);
		delDB = (Button) v.findViewById(R.id.delDB);
		delDB.setOnClickListener(this);
		takeMeasure.setOnClickListener(this);
		addCapacitor.setOnClickListener(this);
		
		editCapSerialNo = (EditText) v.findViewById(R.id.editCapSerialNo);
		editJobNumber = (EditText) v.findViewById(R.id.editJobNumber);
		editAlias = (EditText) v.findViewById(R.id.editAlias);
		
		substationSpinner = (Spinner) v.findViewById(R.id.substation);
		substationAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.substation_array, android.R.layout.simple_spinner_item);
		substationSpinner.setAdapter(substationAdapter);
		substationSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
            	Log.d("SOMETHING", "ItemSelected");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
               // TODO Auto-generated method stub
            }
        });
		
		levelVoltageSpinner = (Spinner) v.findViewById(R.id.levelVoltage);
		voltAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.levelVoltage_array, android.R.layout.simple_spinner_item);
		levelVoltageSpinner.setAdapter(voltAdapter);
		
		ratedSpinner = (Spinner) v.findViewById(R.id.rated);
		ratedAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.rated_array, android.R.layout.simple_spinner_item);
		ratedSpinner.setAdapter(ratedAdapter);
	}
	
//	/*
//	 *  Database section 
//	 */
//	public void openDB(){
//		dbhelper = new DbHelper(this.getActivity());
//	}
//	public void closeDB(){
//		dbhelper.close();
//	}
	
	private void add(){
//    	SQLiteDatabase db = dbhelper.getWritableDatabase();
//    	ContentValues values = new ContentValues();
//    	//for now, if user fail to provide full info, it cleans all field silently, need a popupwindow here
////    	if(!TextUtils.isEmpty(editJobNumber.getText()) 
////    			&& !TextUtils.isEmpty(editCapSerialNo.getText()) 
////    			&& !TextUtils.isEmpty(editAlias.getText()) ){
//    	values.put(JOB_NUMBER, editJobNumber.getText().toString());
//        values.put(SERIAL_NUMBER, editCapSerialNo.getText().toString());
//        values.put(ALIAS, editAlias.getText().toString());
//        db.insert(TABLE_NAME, null, values);
////    	}
//        show();
//    	cleanEditText();
		dbhelper.createCapacitor(editJobNumber.getText().toString(), substationSpinner.getSelectedItem().toString(), levelVoltageSpinner.getSelectedItem().toString(), ratedSpinner.getSelectedItem().toString(),
						editCapSerialNo.getText().toString(), editAlias.getText().toString(),
						measuringVoltage.getText().toString(), measuringFrequency.getText().toString(), measuringCurrent.getText().toString(), measuringCapValue.getText().toString(), measuringErrorRate.getText().toString());

	}
	
//	private void add(){
//		SQLiteDatabase db = dbhelper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put(CapacitorDbHelper.KEY_JOB_NUMBER, editJobNumber.getText().toString());
//		values.put(CapacitorDbHelper.KEY_SUBSTATION, substationSpinner.getSelectedItem().toString());
//		values.put(CapacitorDbHelper.KEY_LEVELVOLTAGE, levelVoltageSpinner.getSelectedItem().toString());
//		values.put(CapacitorDbHelper.KEY_RATED, ratedSpinner.getSelectedItem().toString());
//		values.put(CapacitorDbHelper.KEY_SERIAL_NUMBER, editCapSerialNo.getText().toString());
//		values.put(CapacitorDbHelper.KEY_ALIAS, editAlias.getText().toString());
//		db.insert(CapacitorDbHelper.SQLITE_TABLE, null, values);
//	}	
	
	private void killDB(){
//    	SQLiteDatabase db = dbhelper.getReadableDatabase();
//		dbhelper.delDB(db);
//		show();
		dbhelper.dropDB();
	}
	
    private Cursor getCursor(){
//    	SQLiteDatabase db = dbhelper.getReadableDatabase();
//    	String[] columns = {_ID, JOB_NUMBER, SERIAL_NUMBER, ALIAS};
//    	Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
//    	this.getActivity().startManagingCursor(cursor);
    	Cursor cursor = dbhelper.fetchAllCapacitors();
    	return cursor;
    }    

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.takingMeasureButton:
			genRandomMeasurements();
			break;
		case R.id.addCapacitor:
			add();
			break;
		case R.id.delDB:
			killDB();
		default:
			break;
		}
		show();
	}

	/*
	 *  Other Helper funcs
	 */
	private void cleanEditText(){
		editJobNumber.setText("");
		editCapSerialNo.setText("");
		editAlias.setText("");
	}
	
    private void show(){
//    	Cursor cursor = getCursor();
//    	StringBuilder resultData = new StringBuilder("Capacitors: \n"); 
//    	while(cursor.moveToNext()){
//    		int id = cursor.getInt(0);
//    		String name = cursor.getString(1);
//    		String tel = cursor.getString(2);
//    		String email = cursor.getString(3);
//    		
//    		resultData.append(id).append(": ");
//    		resultData.append(name).append(": ");
//    		resultData.append(tel).append(": ");
//    		resultData.append(email).append(": ");
//    		resultData.append("\n");
//    	}
//    	cpshow.setText(resultData);
    }
	public void genRandomMeasurements(){
		Random r = new Random();
		measuringVoltage.setText(String.valueOf(r.nextInt()%100));
		measuringCurrent.setText(String.valueOf(r.nextInt()%100));
		measuringCapValue.setText(String.valueOf(r.nextInt()%100));
		measuringErrorRate.setText(String.valueOf(r.nextInt()%100));
		measuringFrequency.setText(String.valueOf(r.nextInt()%100));
	}
}
