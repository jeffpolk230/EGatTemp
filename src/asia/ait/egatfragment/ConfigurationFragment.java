package asia.ait.egatfragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ConfigurationFragment extends Fragment{
	private ImageView configuration_image;
	private TextView title;
	private Resources r;
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		r = getActivity().getResources();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.configuration_fragment,container, false);
		configuration_image = (ImageView) view.findViewById(R.id.configuration_image);

		
		Bundle bundle = this.getArguments();
		String configure_title = bundle.getString(InstallationFragment.TAG_CONFIGURE_KEY);
		String configure_image = bundle.getString(InstallationFragment.TAG_CONFIGURE_IMAGE);
		int id = getActivity().getResources().getIdentifier("asia.ait.egatfragment:drawable/"+configure_image, null, null);
		configuration_image.setImageResource(id);
		
		RelativeLayout holder = (RelativeLayout) view.findViewById(R.id.imageHolder);
		
		try{
		String[] positions = r.getStringArray(r.getIdentifier(configure_image,"array", getActivity().getPackageName()));

		holder.addView(createLabel(getActivity(), 
						"Cap1", 
						Integer.parseInt(positions[0].split(";")[0]), 
						Integer.parseInt(positions[0].split(";")[1]), 
						20));
		holder.addView(createLabel(getActivity(), 
				"Cap2", 
				Integer.parseInt(positions[1].split(";")[0]), 
				Integer.parseInt(positions[1].split(";")[1]), 
				20));
		} catch (Resources.NotFoundException e){
			
			holder.addView(createLabel(getActivity(), 
					"Could NOT find coordinates in XML file!", 
					20,
					30,
					20));
			
			Log.d("ResourceMissed", "Resource File misMatched");
			e.printStackTrace();
		}
		
//		title = (TextView) view.findViewById(R.id.configuration_title);
//		title.setText(configure_title);
//		TextView onImage = (TextView) view.findViewById(R.id.onImageTitle);
//		onImage.setText(configure_title);
//		onImage.setX(10);
//		onImage.setY(10);
//		onImage.setTextColor(Color.BLUE);
		
		

//		TextView label1 = new TextView(this.getActivity());
//		
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)LayoutParams.WRAP_CONTENT, (int)LayoutParams.WRAP_CONTENT);
//        
//		String[] positions = r.getStringArray(R.array.label_location); 
//		params.leftMargin=Integer.parseInt(positions[0].split(";")[0]);
//        params.topMargin=Integer.parseInt(positions[0].split(";")[1]);
//        
//        //String[] xys = r.getString(R.string.configure_115kv60unit).split(";");
//        		
//		label1.setText("Add-in Label");
//		label1.setTextSize((float)20);
//		label1.setLayoutParams(params);
//		holder.addView(label1);
		
		
		return view;
	}
	
	
	// This method is in charge of creating a label and returns it.
	private View createLabel(Activity activity, String text, int x, int y, int size){
		TextView label = new TextView(activity);
		label.setText(text);
		label.setTextColor(Color.BLUE);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)LayoutParams.WRAP_CONTENT, (int)LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        label.setLayoutParams(params);
        
		return label;
	}

	// A method is charge of reading specific XML associated with each individual image
	// Readin should be X-Y of each box from Box0 to last?
	
	
}
