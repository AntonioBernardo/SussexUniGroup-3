package uk.ac.sussex.asegr3.tracker.client.dataobject;

import com.google.android.maps.GeoPoint;

public abstract class MapObject {
	
	private GeoPoint point;
	
	public MapObject(double latt, double lon){
		point=new GeoPoint((int) (latt * 1E6), (int) (lon*1E6));
	}
	
	public GeoPoint getGeoPoint(){
		return point;
	}

}
