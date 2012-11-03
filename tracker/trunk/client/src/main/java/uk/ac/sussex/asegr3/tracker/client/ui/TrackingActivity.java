package uk.ac.sussex.asegr3.tracker.client.ui;

import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.DigitalClock;
import android.widget.Toast;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class TrackingActivity extends MapActivity implements MapViewProvider{

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

        MapView mapView = getMap();
        mapView.setBuiltInZoomControls(true);
        // enable Street view by default
        
        DigitalClock clock = (DigitalClock) findViewById(R.id.digitalClock);
        
        LocationService locationService = new LocationServiceFactory().create(this, this, AndroidLogger.INSTANCE, AsyncTask.SERIAL_EXECUTOR);
        
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

	@Override
	public MapView getMap() {
		return (MapView) findViewById(R.id.mapview);
	}

}
