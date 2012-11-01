package uk.ac.sussex.asegr3.tracker.client.service;

import java.util.LinkedList;
import java.util.List;

//import org.mockito.cglib.proxy.CallbackGenerator.Context;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationService implements LocationListener {

	private final List<LocationUpdateListener> listeners;
	private final LocationManager locationManager;
	private final int proximityDistance;

//	GeoPoint touchedPoint;
//	Drawable d;
//	MyLocationOverlay compass;
//	MapController controller;
	
	
	public LocationService(LocationManager locationManager, int proximityDistance) {
		this.listeners = new LinkedList<LocationUpdateListener>();
		this.locationManager = locationManager;
		this.proximityDistance = proximityDistance;
	}

	
	public void start() {
		// register this with location manager providers
		
	//	locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		
		//place a pinpoint at the location
		
		//d = getResource().getDrawable(R.drawable.icon);
		//OverLayItem overlayitem = new OverLayItem (touchedPoint,"Here!");
	//GeoPoint myPoint = new GeoPoint();
		
		
		
	}

	public void registerListener(LocationUpdateListener listener) {
		this.listeners.add(listener);
	}

	private void doLocationFiltering(Location location) {

		// logic here to filter
		// need to work out how to best optimize location approximation

		boolean locationValid = false;
		location = findGoodAccuracy(location);
		Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		//check if doesn't receive empty coordinates
		// possible implementation would be to discard negative coordinates??

		if (location != null) {

			// this if will check if last and current location are close to each other by 100 meters
			//or use the eclideoum equation
			if (lastKnownLocation != null){
				if (lastKnownLocation.distanceTo(location) > proximityDistance) {

					locationValid = true;
				}
			} else{
				locationValid = true; // we dont have a last location so this must be valid.
			}
		}

		if (locationValid) {
			notifyListeners(new LocationDto(location.getLatitude(),
					location.getLongitude(), location.getTime()));
		}
	}

	// this method will notify when a valid location have been added
	private void notifyListeners(LocationDto location) {
		for (LocationUpdateListener listener : listeners) {
			listener.notifyNewLocation(location);
		}
	}

	
	
	//test the accuracy
	private Location findGoodAccuracy(Location location) {
		return location;
	}
	
	
	
	@Override
	public void onLocationChanged(Location location) {
		doLocationFiltering(location);

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}


	public boolean hasRequiredPermissions() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
				locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
}
