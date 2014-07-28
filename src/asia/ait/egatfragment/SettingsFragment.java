package asia.ait.egatfragment;

import java.util.Locale;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsFragment extends Fragment {
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.settings_fragment,container, false);
		ToggleButton toggle = (ToggleButton) v.findViewById(R.id.languageToggle);
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if (isChecked) {
		        	Locale locale = new Locale("en_US");
					Locale.setDefault(locale);
					Configuration config = new Configuration();
					config.locale = locale;
					getActivity().getBaseContext().getResources().updateConfiguration(config,
							getActivity().getBaseContext().getResources().getDisplayMetrics());
//					Log.d("LOCALE", getActivity().getBaseContext().getResources().getConfiguration().locale.toString());
		        } else {
		        	Locale locale = new Locale("th");
					Locale.setDefault(locale);
					Configuration config = new Configuration();
					config.locale = locale;
					getActivity().getBaseContext().getResources().updateConfiguration(config,
							getActivity().getBaseContext().getResources().getDisplayMetrics());
//					Log.d("LOCALE", getActivity().getBaseContext().getResources().getConfiguration().locale.toString());
		        }
		    }
		});
		return v;
	}

}
