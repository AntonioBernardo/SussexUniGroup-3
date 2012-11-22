package uk.ac.sussex.asegr3.prototype;

import java.util.ArrayList;
import java.util.List;

import uk.ac.sussex.asegr3.prototype.dataobjects.Comment;
import uk.ac.sussex.asegr3.prototype.dataobjects.MapObject;
import uk.ac.sussex.asegr3.prototype.dataobjects.PersonPosition;
import uk.ac.sussex.asegr3.prototype.overlays.CommentItemizedOverlay;
import uk.ac.sussex.asegr3.prototype.overlays.CurrentPositionItemizedOverlay;
import uk.ac.sussex.asegr3.prototype.overlays.PositionsItemizedOverlay;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.DigitalClock;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class HelloAndroidActivity extends MapActivity {
	
	public static final int PICTURE_RESULT=200;
	
	private MapView mapView;
	
	private PositionsItemizedOverlay positionsOverlay;
	private CurrentPositionItemizedOverlay currentPositionOverlay;
	private CommentItemizedOverlay commentOverlay;
	
    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        DigitalClock clock = (DigitalClock) findViewById(R.id.digitalClock);
        
        LocationService locationService = new LocationServiceFactory().create(this);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!locationService.hasRequiredPermissions()) {
          Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          startActivity(intent);
        } 
        
        TouchListenerOverlay touchOverlay=new TouchListenerOverlay(this);
        
        mapView.getOverlays().add(touchOverlay);
    }
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addCommentsToMap(List<Comment> comments){
		
		if(comments!=null && !comments.isEmpty()){
			Drawable commentIcon=this.getResources().getDrawable(R.drawable.comment);
			
			if(commentOverlay==null)
				commentOverlay=new CommentItemizedOverlay(commentIcon, this);
			
	        for(Comment comment : comments){
	        	OverlayItem item=new OverlayItem(comment.getGeoPoint(), comment.getAuthors().get(0), comment.getTexts().get(0));
	    		commentOverlay.addOverlay(item);
	        	
	        }
	        
	        mapView.getOverlays().add(commentOverlay);
		}  
	}
	
	public void addCurrentPositionsToMap(List<PersonPosition> positions){
		
		if(positions!=null && !positions.isEmpty()){
			Drawable currentPositionIcon=this.getResources().getDrawable(R.drawable.current_position_icon);
			
			if(currentPositionOverlay==null)
				currentPositionOverlay=new CurrentPositionItemizedOverlay(currentPositionIcon, this);  
	        
	        for(PersonPosition position : positions){
	        	OverlayItem item=new OverlayItem(position.getGeoPoint(), position.getUserName(), "Random");
	    		currentPositionOverlay.addOverlay(item);
	        }
	        
	        mapView.getOverlays().add(currentPositionOverlay);
		}
	}
	
	public void addPersonPositionsToMap(List<PersonPosition> positions){
		
		if(positions!=null && !positions.isEmpty()){
			Drawable standardPositionIcon=this.getResources().getDrawable(R.drawable.friendly_position_icon);
			
			if(positionsOverlay==null)
				positionsOverlay=new PositionsItemizedOverlay(standardPositionIcon, this);
	        
	        for(PersonPosition position : positions){
	        	
	    		OverlayItem item=new OverlayItem(position.getGeoPoint(), position.getUserName(), "Random");
	    		positionsOverlay.addOverlay(item);
	        }
	        
	        mapView.getOverlays().add(positionsOverlay);
		}
	}
	
	public void hideCommentPositions(){
		mapView.getOverlays().remove(commentOverlay);
	}
	
	public void hideCurrentPositions(){
		mapView.getOverlays().remove(currentPositionOverlay);
	}
	
	public void hidePersonPositions(){
		mapView.getOverlays().remove(positionsOverlay);
	}
	
	public void removeCommentPositions(){
		commentOverlay=null;
	}
	
	public void removeCurrentPositions(){
		currentPositionOverlay=null;
	}
	
	public void removePersonPositions(){
		positionsOverlay=null;
	}
	
	public void addMapSymbols(){
		// Retrieving the current existing overlays
//		List<Overlay> overlays=mapView.getOverlays();
		
		
		// Defining the overlays (current position, all positions, comments)
        
        
//        float lat=51.533544f;
//        float lon=-0.479745f;
//        
//        GeoPoint testPoint=new GeoPoint((int)(lat* 1E6), (int) (lon*1E6));
//   
//        OverlayItem overlay=new OverlayItem(testPoint, "Hola Guapa!", "I'm waiting here for you");
//      
//      	positionsOverlay.addOverlay(overlay);
//      	
//      	overlays.add(positionsOverlay);
      	
//      	Drawable standardPositionIcon=this.getResources().getDrawable(R.drawable.friendly_position_icon);
//        positionsOverlay=new PositionsItemizedOverlay(standardPositionIcon, this);
        
//        for(int i=0; i<10;i++){
//        	
//          float lat=10.533544f;
//          float lon=-5.479745f;
//          
//          GeoPoint testPoint=new GeoPoint((int)(lat*i* 1E6), (int) (lon*i*1E6));
//     
//          OverlayItem overlay=new OverlayItem(testPoint, "Hola!" + i, "NR: " +i);
//        
//          positionsOverlay.addOverlay(overlay);
//
//        }
        
//        Drawable currentPositionIcon=this.getResources().getDrawable(R.drawable.current_position_icon);
//        currentPositionOverlay=new CurrentPositionItemizedOverlay(currentPositionIcon, this);  
//        
//        Drawable commentIcon=this.getResources().getDrawable(R.drawable.comment);
//        commentOverlay=new CommentItemizedOverlay(commentIcon, this);  
//        
//        for(MapObject o : initialiseTestData()){
//        	
//        	if(Comment.class.isInstance(o)){
        	
//        		Comment comment=(Comment)o;
//        		
//        		OverlayItem item=new OverlayItem(comment.getGeoPoint(), comment.getAuthors().get(0), comment.getTexts().get(0));
//        		commentOverlay.addOverlay(item);
//        	}
//        	else if(PersonPosition.class.isInstance(o)){
//        		
//        		PersonPosition personPosition=(PersonPosition)o;
//        		OverlayItem item=new OverlayItem(personPosition.getGeoPoint(), personPosition.getUserName(), "Random");
//        		
//        		if(personPosition.isCurrent()){
//        			currentPositionOverlay.addOverlay(item);
//        		}
//        		else{
//        			positionsOverlay.addOverlay(item);
//        		}
//        		
//        	} 
//        }
        
        
//        float lat=51.533544f;
//        float lon=-0.479745f;
//        GeoPoint testPoint=new GeoPoint((int)(lat* 1E6), (int) (lon*1E6));
//        
//        OverlayItem overlay=new OverlayItem(testPoint, "Hola Guapa!", "I'm waiting here for you");

        
//        customOverlay.addOverlay(overlay);
//        overlays.add(customOverlay);
//        overlays.add(positionsOverlay);
//        overlays.add(currentPositionOverlay);
//      overlays.add(commentOverlay);
        
		
	}
	
	private List<MapObject> initialiseTestData(){
		
		List<MapObject> objects=new ArrayList<MapObject>();
		
//		for(int i=0; i<10; i++){
//			
//			List<String> authors=new ArrayList<String>();
//			authors.add("Author " + i);
//			
//			List<String> texts=new ArrayList<String>();
//			texts.add("That's it: " +i);
//			
//			Comment comment=new Comment(i, "Comment" + i, authors,
//					texts, -40.00f * i, 50.01f *i);
//			objects.add(comment);
//		}
//		
		List<String> userNames=new ArrayList<String>();
		
		userNames.add("Sebastian");
		userNames.add("William");
		userNames.add("Andy");
		userNames.add("Toni");
		userNames.add("Preshit");
		userNames.add("Jeff");
		userNames.add("Ndi");
		userNames.add("Jonathon");
		userNames.add("Logan");
		userNames.add("Fritz");
		
		for(int i=0; i<10; i++){
			
			
			
			PersonPosition person;// =new PersonPosition(i, userNames.get(i), false, 100.00f *(i+1), -200.00f *i);
			
			if(i%3==0){
				 person=new PersonPosition(i, userNames.get(i), true, 80.00f *(i+1), -100.00f *i);
			}
			else{
				 person=new PersonPosition(i, userNames.get(i), false, 80.00f *(i+1), -100.00f *i);
			}
			objects.add(person);
		}
		
		
		
		return objects;
		
	}
	
	public void takePicture(View view){
		
		Intent intent=new Intent(this, TakePictureActivity.class);
		
		startActivity(intent);
	}
	
//	@Override 
//	public void onActivityResult(int requestCode, int resultCode, Intent data){
//		super.onActivityResult(requestCode, resultCode, data);
//		
//		if(resultCode==Activity.RESULT_OK){
//			if(requestCode==PICTURE_RESULT){
//				
//				
//			}
//			
//		}
//	}
	

}

