package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumer;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.location.LocationCache;
import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

public class LocationServiceFactory {
	
	private static final int DEFAULT_PROXIMITY_DISTANCE = 5;//5 meters
	private static final int DEFAULT_CACHE_LIMIT = 1000;
	private static final int DEFAULT_BATCH_SIZE = 5;
	private static final long DEFAULT_FLUSH_TIME = TimeUnit.MINUTES.toMillis(1);
	private static final float DEFAULT_ACCURANCY_REQUIREMENT = 4;// 4 meters accurancy only

	public LocationService create(Activity activity, HttpTransportClientApi api, MapViewProvider mapViewProvider,  Logger logger, Executor executor,
			FetchLocationCallBack fetchLocationCallBack, MapViewManager mapViewManager) {
		
		if (api == null){
			throw new IllegalArgumentException("No api has been authenticated");
		}
		LocationService service = new LocationService((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE), DEFAULT_PROXIMITY_DISTANCE, logger,
				api, AsyncTask.SERIAL_EXECUTOR, fetchLocationCallBack, DEFAULT_ACCURANCY_REQUIREMENT);
		LocationCache locationCache = new LocationCache(DEFAULT_CACHE_LIMIT, DEFAULT_BATCH_SIZE, DEFAULT_FLUSH_TIME, api, logger, executor);
		
		UiUpdater uiUpdater = new UiUpdater(mapViewProvider, mapViewManager, api);
		
		service.registerListener(locationCache);
		service.registerListener(uiUpdater);
		
		service.start();
		
		return service;
	}

	public static class NoOpConsumer implements BatchLocationConsumer {

		@Override
		public boolean processBatch(LocationBatch batch) {
			// no op
			
			return true;
		}

		@Override
		public boolean isReady() {
			return false;
		}
		
	}

}
