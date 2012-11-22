package uk.ac.sussex.asegr3.tracker.client.ui.overlays;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CommentItemizedOverlay extends ItemizedOverlay<OverlayItem>{
	
	private ArrayList<OverlayItem> overlayItems;
	private Context context;
	
	public CommentItemizedOverlay(Drawable defaultMarker, Context context){
		super(boundCenter(defaultMarker));
		this.context=context;
		overlayItems=new ArrayList<OverlayItem>();
		
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
		AlertDialog.Builder dialog= new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		
		return true;
	}

}
