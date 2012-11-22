package uk.ac.sussex.asegr3.prototype.mapobjects;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CommentMapItem extends OverlayItem{
	
	private ArrayList<String> authors;
	private ArrayList<String> texts;
	private ArrayList<String> dates;
	
	public CommentMapItem(GeoPoint point, String user, String text, ArrayList<String> authors, ArrayList<String> texts,
			ArrayList<String> dates){
		super(point, user, text);
		this.authors=authors;
		this.texts=texts;
		this.dates=dates;
	}

	public ArrayList<String> getAuthors() {
		return authors;
	}

	public ArrayList<String> getTexts() {
		return texts;
	}

	public ArrayList<String> getDates() {
		return dates;
	}

}
