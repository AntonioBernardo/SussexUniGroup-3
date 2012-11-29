package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.List;

import uk.ac.sussex.asegr3.tracker.client.dataobject.Comment;
import uk.ac.sussex.asegr3.tracker.client.dataobject.PersonPosition;
import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.ui.overlays.CommentItemizedOverlay;
import uk.ac.sussex.asegr3.tracker.client.ui.overlays.CurrentPositionItemizedOverlay;
import uk.ac.sussex.asegr3.tracker.client.ui.overlays.PositionsItemizedOverlay;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapViewManager {
	
	private MapView mapView;
	private Context context;
	
	private PositionsItemizedOverlay positionsOverlay;
	private CurrentPositionItemizedOverlay currentPositionOverlay;
	private CommentItemizedOverlay commentOverlay;
	
	public MapViewManager(MapView mapView, Context context){
		this.mapView=mapView;
		this.context=context;
		
	}
	
public void addCommentsToMap(List<Comment> comments){
		
		if(comments!=null && !comments.isEmpty()){
			Drawable commentIcon=context.getResources().getDrawable(R.drawable.comment);
			
			if(commentOverlay==null)
				commentOverlay=new CommentItemizedOverlay(commentIcon, context);
			
	        for(Comment comment : comments){
	        	OverlayItem item=new OverlayItem(comment.getGeoPoint(), comment.getAuthors().get(0), comment.getTexts().get(0));
	    		commentOverlay.addOverlay(item);
	        	
	        }
	        
	        mapView.getOverlays().add(commentOverlay);
		}  
	}
	
	public void addCurrentPositionsToMap(List<PersonPosition> positions){
		
		if(positions!=null && !positions.isEmpty()){
			Drawable currentPositionIcon=context.getResources().getDrawable(R.drawable.current_position_icon);
			
			if(currentPositionOverlay==null)
				currentPositionOverlay=new CurrentPositionItemizedOverlay(currentPositionIcon, context);  
	        
	        for(PersonPosition position : positions){
	        	OverlayItem item=new OverlayItem(position.getGeoPoint(), position.getUserName(), "Random");
	    		currentPositionOverlay.addOverlay(item);
	        }
	        
	        mapView.getOverlays().add(currentPositionOverlay);
		}
	}
	
	public void addSomePointsToMap(List<LocationDto> locations){
		
		if(locations!=null && !locations.isEmpty()){
			Drawable currentPositionIcon=context.getResources().getDrawable(R.drawable.current_position_icon);
		
			if(currentPositionOverlay==null)
				currentPositionOverlay=new CurrentPositionItemizedOverlay(currentPositionIcon, context);  
	        
	        for(LocationDto position : locations){
	        	GeoPoint geoPoint=new GeoPoint((int)(position.getLat()*1E6), (int)(position.getLng()*1E6));
	        	OverlayItem item=new OverlayItem(geoPoint, new String(""+position.getTimestamp()), new String("Random"));
//	        	OverlayItem item=new OverlayItem(position.getGeoPoint(), position.getUserName(), "Random");
	    		currentPositionOverlay.addOverlay(item);
	        }
	        
	        mapView.getOverlays().add(currentPositionOverlay);
			
		}
		
	}
	
	public void addPersonPositionsToMap(List<PersonPosition> positions){
		
		if(positions!=null && !positions.isEmpty()){
			Drawable standardPositionIcon=context.getResources().getDrawable(R.drawable.friendly_position_icon);
			
			if(positionsOverlay==null)
				positionsOverlay=new PositionsItemizedOverlay(standardPositionIcon, context);
	        
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

}
