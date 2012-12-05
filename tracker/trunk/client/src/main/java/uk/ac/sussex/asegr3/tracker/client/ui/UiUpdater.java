package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.Arrays;

import android.content.Context;
import android.widget.Toast;

import com.google.android.maps.MapView;

import uk.ac.sussex.asegr3.tracker.client.dataobject.PersonPosition;
import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.service.LocationUpdateListener;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;

public class UiUpdater implements LocationUpdateListener {
	
	private final MapViewProvider mapViewProvider;
	private final MapViewManager mapViewManager;
	private final HttpTransportClientApi api;
	
	public UiUpdater(MapViewProvider mapViewProvider, MapViewManager mapViewManager, HttpTransportClientApi api){
		this.mapViewProvider = mapViewProvider;
		this.mapViewManager = mapViewManager;
		this.api = api;
		
	}

	@Override
	public void notifyNewLocation(LocationDto location) {
		MapView mapView = mapViewProvider.getMap();
		   
		   //add textbox
		mapViewManager.hideCurrentPositions();
		mapViewManager.addCurrentPositionsToMap(Arrays.asList(new PersonPosition(1, api.getLoggedInUser(), true, location.getLat(), location.getLng())));
		   
		   
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
