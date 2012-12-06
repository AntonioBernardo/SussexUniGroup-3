package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.List;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApiFactory;
import uk.ac.sussex.asegr3.tracker.client.ui.overlays.AddCommentCallback;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class TrackingActivity extends MapActivity implements MapViewProvider, FetchLocationCallBack, AddCommentCallback {


	private static final int PERMISSION_GRANTED = 1;
	
	private MapViewManager mapViewManager;
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		MapView mapView = getMap();
		mapView.setBuiltInZoomControls(true);
		// enable Street view by default

		HttpTransportClientApi api = HttpTransportClientApiFactory.getCurrentApi();
		DigitalClock clock = (DigitalClock) findViewById(R.id.digitalClock);
		Intent trackingIntent = getIntent();
		
		

		// TextView txtName = (TextView) findViewById(R.id.TextViewDisplay1);
		// TextView txtEmail = (TextView) findViewById(R.id.TextViewDisplay2);
		Intent i = getIntent();
		
		this.mapViewManager=new MapViewManager(getMap(), this, this);

		final LocationService locService=new LocationServiceFactory().create(
				this, api, this, AndroidLogger.INSTANCE,
				AsyncTask.SERIAL_EXECUTOR, this, mapViewManager);
		
		
		
		
		Button next = (Button) findViewById(R.id.button);

		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				locService.forceUpdateLocation();

				
			}
		});
		
		locService.getNearbyLocations();
		
		Button exit = (Button) findViewById(R.id.button2);

		exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				finish();

				
			}
		});
		
		
		Button comment = (Button) findViewById(R.id.button1);

		comment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				Intent myIntent = new Intent(getBaseContext(), UiAddComment.class);
		         startActivity(myIntent);

				
			}
		});
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
	
		return false;
	}

	@Override
	public MapView getMap() {
		return (MapView) findViewById(R.id.mapview);
	}
	
	public MapViewManager getMapViewManager(){
		return this.mapViewManager;
	}

	@Override
	public void processFetchLocations(List<LocationDto> locations) {
		System.out.println("In processFetchLocations");
		System.out.println("Location size" + locations.size());
		
		mapViewManager.addSomePointsToMap(locations);
		
	}

	@Override
	public void processFetchFailed(Exception e) {
		throw new RuntimeException(e);
		
	}

	@Override
	public void addComment() {
		
		 Intent myIntent = new Intent(getBaseContext(), UiViewComment.class);
         this.startActivity(myIntent);
	}

	@Override
	public void viewComments() {
		// TODO Auto-generated method stub
		
         
         Intent myIntent = new Intent(getBaseContext(), UiAddComment.class);
         this.startActivity(myIntent);
		
	}

	@Override
	public void viewUsers() {
		
		 Intent myIntent = new Intent(getBaseContext(), UiViewUsers.class);
         this.startActivity(myIntent);
	}
	
	
	


}
