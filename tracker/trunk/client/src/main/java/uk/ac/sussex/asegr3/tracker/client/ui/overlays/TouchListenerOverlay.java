package uk.ac.sussex.asegr3.tracker.client.ui.overlays;


import uk.ac.sussex.asegr3.tracker.client.ui.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class TouchListenerOverlay extends Overlay {
	
	private Context context;
	private boolean active;
	private int indicator=0;
	
	public TouchListenerOverlay(Context context){
		
		this.context=context;
		this.active=false;
	}
	
	@Override
	public boolean onTouchEvent(android.view.MotionEvent e, MapView view){
		
//		if(active==false)
//		{HelloAndroidActivity activity=(HelloAndroidActivity)context;
//		
//		activity.addMapSymbols();
//		active=true;
//		}
//		else{
//			HelloAndroidActivity activity=(HelloAndroidActivity)context;
//			
//			activity.removeMapSymbols();
//			active=false;
//		}
//		
		return false;
		
	}
	
	@Override
	public boolean onTap(GeoPoint point, MapView view){
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure?").setPositiveButton("Post", null)
        .setNegativeButton("Cancel",null);
    
//        Button test = (Button) findViewById(R.id.button1);
        
    	LayoutInflater li = LayoutInflater.from(context);
    	View commentView = li.inflate(R.layout.commentdialog, null);

  

		  // set comment.xml to alertdialog builder
		  builder.setView(commentView);
		
		  final EditText userInput = (EditText) commentView
		    .findViewById(R.id.commentEditText);
		  
		  AlertDialog dialog = builder.create();
		     dialog.show();

		return false;
	}

}

