package uk.ac.sussex.asegr3.prototype;

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
    	View commentView = li.inflate(R.layout.comments, null);

  

		  // set comment.xml to alertdialog builder
		  builder.setView(commentView);
		
		  final EditText userInput = (EditText) commentView
		    .findViewById(R.id.editText1);
		  
		  AlertDialog dialog = builder.create();
		     dialog.show();
		
		 
//		HelloAndroidActivity activity=(HelloAndroidActivity)context;
//		
//		if(indicator%6==5){
//			activity.hidePersonPositions();
//			activity.removePersonPositions();
//		}
//		else if(indicator%5==4){
//			activity.hideCurrentPositions();
//			activity.removeCurrentPositions();
//		}
//		else if(indicator%4==3){
//			activity.hideCommentPositions();
//			activity.removeCommentPositions();
//		}
//		else if(indicator%3==0){
//			List<Comment> comments=new ArrayList<Comment>();
//			
//			ArrayList<String>texts=new ArrayList<String>();
//			texts.add("Test text");
//			
//			ArrayList<String> authors=new ArrayList<String>();
//			authors.add("Author test");
//			
//			Comment comment=new Comment(indicator, "Test comment", 
//					authors, texts, point.getLatitudeE6()/1E6, point.getLongitudeE6()/1E6);
//			
//			comments.add(comment);
//			
//			activity.addCommentsToMap(comments);
//		}
//		else if (indicator%3==1){
//			
//			List<PersonPosition> positions=new ArrayList<PersonPosition>();
//			
//			PersonPosition position=new PersonPosition(indicator, "User Test", 
//					false, point.getLatitudeE6()/1E6, point.getLongitudeE6()/1E6);
//			
//			positions.add(position);
//			
//			activity.addPersonPositionsToMap(positions);
//			
//		}
//		else if(indicator%3==2){
//			List<PersonPosition> positions=new ArrayList<PersonPosition>();
//			
//			PersonPosition position=new PersonPosition(indicator, "User Test", 
//					true, point.getLatitudeE6()/1E6, point.getLongitudeE6()/1E6);
//			
//			positions.add(position);
//			
//			activity.addCurrentPositionsToMap(positions);
//			
//		}
//		
//		
//		indicator++;
		return false;
	}

}
