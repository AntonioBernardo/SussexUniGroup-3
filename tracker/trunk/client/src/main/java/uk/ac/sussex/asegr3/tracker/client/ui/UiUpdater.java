package uk.ac.sussex.asegr3.tracker.client.ui;

import android.content.Context;
import android.widget.Toast;

import com.google.android.maps.MapView;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.service.LocationUpdateListener;

public class UiUpdater implements LocationUpdateListener {
	
	private final MapViewProvider mapViewProvider;
	
	public UiUpdater(MapViewProvider mapViewProvider){
		this.mapViewProvider = mapViewProvider;
		
	}

	@Override
	public void notifyNewLocation(LocationDto location) {
		MapView mapView = mapViewProvider.getMap();
		   
		   //add textbox
		   
		   
		   Toast.makeText(mapView.getContext(), 
			          "Latitude: " + location.getLat() + 
			          " Longitude: " + location.getLng(), 
			          1000000).show();
			      
		   mapView.refreshDrawableState();
		  
	}

	private Context getBaseContext() {
		return mapViewProvider.getMap().getContext();
	}

}
