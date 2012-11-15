package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumer;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.location.LocationCache;
import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClient;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

public class LocationServiceFactory {
	
	private static final int DEFAULT_PROXIMITY_DISTANCE = 100;
	private static final int DEFAULT_CACHE_LIMIT = 1000;
	private static final int DEFAULT_BATCH_SIZE = 10;
	private static final long DEFAULT_FLUSH_TIME = TimeUnit.MINUTES.toMillis(1);

	public LocationService create(Activity activity, MapViewProvider mapViewProvider,  Logger logger, Executor executor) {
		
		LocationService service = new LocationService((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE), DEFAULT_PROXIMITY_DISTANCE, logger);
		NetworkInfoProvider networkInfoProvider = createNetworkInfoProvider(activity);
		BatchLocationConsumer consumer = new HttpTransportClient("localhost", logger, networkInfoProvider);
		LocationCache locationCache = new LocationCache(DEFAULT_CACHE_LIMIT, DEFAULT_BATCH_SIZE, DEFAULT_FLUSH_TIME, consumer, logger, executor);
		
		UiUpdater uiUpdater = new UiUpdater(mapViewProvider);
		
		service.registerListener(locationCache);
		service.registerListener(uiUpdater);
		
		service.start();
		
		return service;
	}
	
	private NetworkInfo getNetworkInfo(Activity activity) {
		 ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		 return connectivityManager.getActiveNetworkInfo();
	}
	
	private NetworkInfoProvider createNetworkInfoProvider(final Activity activity){
		return new NetworkInfoProvider(){

			@Override
			public NetworkInfo getNetworkInfo() {
				return LocationServiceFactory.this.getNetworkInfo(activity);
			}
			
		};
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
