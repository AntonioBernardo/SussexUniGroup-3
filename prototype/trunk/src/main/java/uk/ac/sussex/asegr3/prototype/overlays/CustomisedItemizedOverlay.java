package uk.ac.sussex.asegr3.prototype.overlays;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomisedItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> overlays=new ArrayList<OverlayItem>();
	private Context context;

	public CustomisedItemizedOverlay(Drawable defaultDrawable, Context context) {
		super(boundCenter(defaultDrawable));
		this.context=context;
		// TODO Auto-generated constructor stub
	}
	
	public void addOverlay(OverlayItem item){
		overlays.add(item);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i){
		return overlays.get(i);
	}
	
	@Override
	public int size(){
		return overlays.size();
	}
	
	@Override
	protected boolean onTap(int i){
		System.out.println(""+i);
		OverlayItem item=overlays.get(i);
		AlertDialog.Builder dialog= new AlertDialog.Builder(context);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

}
