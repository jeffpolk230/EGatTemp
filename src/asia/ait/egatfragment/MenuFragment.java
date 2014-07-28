package asia.ait.egatfragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends Fragment implements OnItemClickListener {
	
	private ListView menuList;
	private ArrayAdapter<String> adapter;

	private String[] menuItems;
	private boolean isTwoPane;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		Resources r = activity.getResources();
		menuItems = r.getStringArray(R.array.menu_array);
		adapter = new ArrayAdapter<String>(activity, R.layout.list_white_text,R.id.list_content,menuItems);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.menu_fragment,container, false);
		menuList = (ListView) v.findViewById(R.id.menu_list);
		menuList.setAdapter(adapter);
		menuList.setOnItemClickListener(this);
		return v;
	}

	
	//Dynamic 
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if(getActivity().findViewById(R.id.details_layout)!=null)
			isTwoPane = true;
		else{
			isTwoPane = false;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(isTwoPane){
			Fragment fragment = null;
			if(position == 0){
				fragment = new MeasurementFragment();
			} else if (position == 1){
				fragment = new CapacitorListingFragment();
			} else if (position == 2){
				fragment = new InstallationFragment();
			} else if (position == 4){
				fragment = new SettingsFragment();
			} else // Temp redirect to Display
			{
				fragment = new DisplayFragment();
			}
			getFragmentManager().beginTransaction().replace(R.id.details_layout, fragment).commit();
		} else {
			Intent intent = null;
			if(position == 0){
				intent = new Intent(getActivity(), MeasurementActivity.class);
			}else if (position ==1){
				intent = new Intent(getActivity(), DisplayActivity.class);
			}
			startActivity(intent);
		}
	}
	
}
