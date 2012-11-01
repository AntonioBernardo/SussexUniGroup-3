package uk.ac.sussex.asegr3.tracker.client.ui;

import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.DigitalClock;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class TrackingActivity extends MapActivity{

	 private static final int DEFAULT_PROXIMITY_DISTANCE = 100;

	/**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        DigitalClock clock = (DigitalClock) findViewById(R.id.digitalClock);
        
        LocationService locationService = new LocationService((LocationManager) this.getSystemService(Context.LOCATION_SERVICE), DEFAULT_PROXIMITY_DISTANCE);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!locationService.hasRequiredPermissions()) {
          Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          startActivity(intent);
        } 
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
