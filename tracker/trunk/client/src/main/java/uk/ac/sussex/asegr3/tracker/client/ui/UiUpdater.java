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
		   
		   //add marker
//		   MapOverlay mapOverlay = new MapOverlay();
//		      mapOverlay.setPointToDraw(point);
//		      List<Overlay> listOfOverlays = mapView.getOverlays();
//		      listOfOverlays.clear();
//		      listOfOverlays.add(mapOverlay);
//		      
//		      String address = ConvertPointToLocation(point);
//		      Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();
		   
	}

	private Context getBaseContext() {
		return mapViewProvider.getMap().getContext();
	}

}
