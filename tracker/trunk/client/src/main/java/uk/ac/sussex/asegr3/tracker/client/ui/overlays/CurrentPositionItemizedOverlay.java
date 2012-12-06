package uk.ac.sussex.asegr3.tracker.client.ui.overlays;

import java.util.ArrayList;

import uk.ac.sussex.asegr3.tracker.client.ui.R;
import uk.ac.sussex.asegr3.tracker.client.ui.UiRegister;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CurrentPositionItemizedOverlay extends ItemizedOverlay<OverlayItem> {	

	private ArrayList<OverlayItem> overlayItems;
	private Context context;
	//private Activity activity;
	private AddCommentCallback callback;
	
	public CurrentPositionItemizedOverlay(Drawable defaultMarker, Context context, AddCommentCallback callback){
		super(boundCenter(defaultMarker));
		this.context=context;
		this.overlayItems=new ArrayList<OverlayItem>();
		this.callback=callback;
		
		
	}
	
	public void addOverlay(OverlayItem item){
		overlayItems.add(item);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i){
		return overlayItems.get(i);
	}
	
	@Override
	public int size(){
		return overlayItems.size();
	}
	
	@Override
	protected boolean onTap(int i){
		OverlayItem item=overlayItems.get(i);
		
		
		//code to display when clicking on position  
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setNegativeButton("Cancel", null);
		builder.setMessage("Please choose one of the options").setTitle(R.string.app_name);
		
		
		LayoutInflater inflater = LayoutInflater.from(context);
		final View optionsView = inflater.inflate(R.layout.click_main, null);
		
		builder.setView(optionsView);

		
		AlertDialog dialog = builder.create();		
		dialog.show();
		
	 Button first = (Button) optionsView.findViewById(R.id.button1);
		 first.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	               // Intent myIntent = new Intent(view.getContext(), UiRegister.class);
	              //  activity.startActivity(myIntent);
	            	 Toast.makeText(optionsView.getContext(),
								"Loading.....",
								Toast.LENGTH_SHORT).show();
	            }

	        });
		
		//In case we need t o display the user's name
//		dialog.setMessage(item.getSnippet());
		
		return true;
	}
	
}

