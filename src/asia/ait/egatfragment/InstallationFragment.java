package asia.ait.egatfragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InstallationFragment extends Fragment implements OnItemClickListener{
	private ListView configurations;
	private ArrayAdapter<String> adapter;
	private String[] configs;
	public static final String TAG_CONFIGURE_KEY = "CONFIGURE_KEY";
	public static final String TAG_CONFIGURE_IMAGE = "CONFIGURE_TITLE";
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		Resources r = activity.getResources();
		configs = r.getStringArray(R.array.configuration_array);
//		Log.d("ATTACHED", configs.toString());
		adapter = new ArrayAdapter<String>(activity, R.layout.list_gray_text,R.id.list_content,configs);
		
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.installation_fragment, container, false);  
		initView(view);
		return view;  
	}
	private void initView(View v){
		configurations = (ListView) v.findViewById(R.id.configurations);
//		if (configurations == null)
//				Log.d("NULLConf", "NULL");
		configurations.setAdapter(adapter);
		configurations.setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// Small Trick here, using configuration_array[position] to retrieve title
		String[] title_array = getActivity().getResources().getStringArray(R.array.configuration_array);
		Log.d("Title",configTitleHelper(title_array[position]));
		Bundle bundle = new Bundle();
		bundle.putString(TAG_CONFIGURE_KEY,title_array[position]);
		bundle.putString(TAG_CONFIGURE_IMAGE, configTitleHelper(title_array[position]));
		Fragment frag = new ConfigurationFragment();
		frag.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(this.getId(), frag).commit();
		
	}
	
	private String configTitleHelper(String fullName){
		String imageName;
		imageName = "configure_"+fullName.split("\\s+")[0].toLowerCase()+fullName.split("\\s+")[1].toLowerCase();
		return imageName;
	}
}
