package uk.ac.sussex.asegr3.prototype.overlays;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CurrentPositionItemizedOverlay extends ItemizedOverlay<OverlayItem> {	

	private ArrayList<OverlayItem> overlayItems=new ArrayList<OverlayItem>();
	private Context context;
	
	public CurrentPositionItemizedOverlay(Drawable defaultMarker, Context context){
		super(boundCenter(defaultMarker));
		this.context=context;
		
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
