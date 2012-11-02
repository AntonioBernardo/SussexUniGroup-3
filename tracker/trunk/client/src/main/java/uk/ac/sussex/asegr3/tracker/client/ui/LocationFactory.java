package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumer;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.location.LocationCache;
import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

public class LocationFactory {
	

	 private static final int DEFAULT_PROXIMITY_DISTANCE = 100;
	 private static final int DEFAULT_CACHE_LIMIT = 1000;
	 private static final int DEFAULT_BATCH_SIZE = 10;
	 private static final long DEFAULT_FLUSH_TIME = TimeUnit.MINUTES.toMillis(1);

	public LocationService create(Activity activity, MapViewProvider mapViewProvider, Logger logger) {
		
		LocationService service = new LocationService((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE), DEFAULT_PROXIMITY_DISTANCE, logger);
		
		BatchLocationConsumer consumer = new NoOpConsumer();
		LocationCache locationCache = new LocationCache(DEFAULT_CACHE_LIMIT, DEFAULT_BATCH_SIZE, DEFAULT_FLUSH_TIME, consumer, logger);
		
		service.registerListener(locationCache);
		
		UiUpdater uiUpdater = new UiUpdater(mapViewProvider);
		service.registerListener(uiUpdater);
		
		service.start();
		return service;
	}
	
	public static class NoOpConsumer implements BatchLocationConsumer {

		@Override
		public void processBatch(LocationBatch batch) {
			// no op
		}

		@Override
		public boolean isReady() {
			return false;
		}
		
	}

}
