package asia.ait.egatfragment;

import static android.provider.BaseColumns._ID;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_ALIAS;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_CAPACITORVALUE;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_CURRENT;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_ERROR;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_FREQUENCY;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_JOB_NUMBER;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_LEVELVOLTAGE;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_MEASURINGVOLTAGE;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_RATED;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_SERIAL_NUMBER;
import static asia.ait.egatfragment.CapacitorDbAdapter.KEY_SUBSTATION;

import java.util.Locale;

import android.app.Fragment;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

//public class CapacitorListingFragment extends Fragment {
//	
//	private DbHelper dbhelper;
//	private TextView capacitorListing;
//	private ListView listCapacitor;
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//		View view = inflater.inflate(R.layout.capacitor_listing_fragment, container, false);  
//		initView(view);
//		openDB();
//
//		showInList();
//		return view;  
//	}
//	public void initView(View v){
//		listCapacitor = (ListView) v.findViewById(R.id.listCapactior);
//	}
//	public void openDB(){
//		dbhelper = new DbHelper(this.getActivity());
//	}
//	public void closeDB(){
//		dbhelper.close();
//	}
//    private Cursor getCursor(){
//    	SQLiteDatabase db = dbhelper.getReadableDatabase();
//    	String[] columns = {_ID, JOB_NUMBER, SERIAL_NUMBER, ALIAS};
//    	Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
//    	this.getActivity().startManagingCursor(cursor);
//    	return cursor;
//    }    
//	
//    private void showInList(){
//    	
//    	Cursor cursor = getCursor();
//    	
//    	String[] from = {_ID, JOB_NUMBER, SERIAL_NUMBER, ALIAS};
//    	int[] to = {R.id.txtId, R.id.txtJobName, R.id.txtSerialNumber, R.id.txtAlias};
//    	
//    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.data_item, cursor, from, to);
//    	listCapacitor.setAdapter(adapter);
////    	listCapacitor.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//    }
//
//}
public class CapacitorListingFragment extends Fragment {
	public CapacitorDbAdapter dbHelper;
	private SimpleCursorAdapter dataAdapter;
	private ListView listCapacitor;
	private EditText myFilter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.capacitor_listing_fragment, container, false);
		listCapacitor = (ListView) view.findViewById(R.id.listCapactior);
		myFilter = (EditText) view.findViewById(R.id.myFilter);
		dbHelper = new CapacitorDbAdapter(getActivity());
		dbHelper.open();
		displayListView();
		
		return view;
	}

	private void displayListView(){
		Cursor cursor = dbHelper.fetchAllCapacitors();
		String[] columns = new String[]{_ID, KEY_JOB_NUMBER, KEY_SUBSTATION, KEY_LEVELVOLTAGE, KEY_RATED,
				KEY_SERIAL_NUMBER, KEY_ALIAS, KEY_MEASURINGVOLTAGE, KEY_FREQUENCY, KEY_CURRENT, KEY_CAPACITORVALUE, KEY_ERROR};
		int[] to = new int[]{R.id.txtId, R.id.txtJobName, R.id.txtSubstation, R.id.txtLevelVoltage, R.id.txtRated,
							 R.id.txtSerialNumber, R.id.txtAlias, R.id.txtMeasuringVoltage, R.id.txtFrequncy, R.id.txtCurrent, R.id.txtCapacitorValue, R.id.txtError};
		dataAdapter = new SimpleCursorAdapter(
			    getActivity(), R.layout.data_item, 
			    cursor, 
			    columns, 
			    to, 0);
		listCapacitor.setAdapter(dataAdapter);
		myFilter.addTextChangedListener(new TextWatcher() {
			 
			   public void afterTextChanged(Editable s) {
			   }
			 
			   public void beforeTextChanged(CharSequence s, int start, 
			     int count, int after) {
			   }
			 
			   public void onTextChanged(CharSequence s, int start, 
			     int before, int count) {
			    dataAdapter.getFilter().filter(s.toString());
			   }
			  });
		dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
	         public Cursor runQuery(CharSequence constraint) {
	             return dbHelper.fetchCountriesByJobNumber(constraint.toString());
	         }
	     });
	}
}
