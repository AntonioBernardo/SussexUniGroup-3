package uk.ac.sussex.asegr3.prototype;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

public class LocationServiceFactory {

	public LocationService create(Activity activity) {
		LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		
		return new LocationService(manager);
	}

}
