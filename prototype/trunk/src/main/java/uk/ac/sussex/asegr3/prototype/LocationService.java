package uk.ac.sussex.asegr3.prototype;

import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationService implements LocationListener{

	private final LocationManager locationManager;
	
	public LocationService(LocationManager locationManager){
        
        this.locationManager = locationManager;
	}
	
	public boolean hasRequiredPermissions(){
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
			locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	
	public void registerService(){
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, this);
	}

	@Override
	public void onLocationChanged(Location arg0) {
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
