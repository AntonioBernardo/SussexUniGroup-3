package uk.ac.sussex.asegr3.tracker.client.location;


import java.util.List;

import uk.ac.sussex.asegr3.tracker.client.ui.R;

import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class DisplayUserIdLocation extends MapActivity {

	MapView mapview;
	MapController mc;
	GeoPoint p;

	/** Called when the activity is first created. **/

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mapview = (MapView) findViewById(R.id.mapview);
		((MapView) mapview).setBuiltInZoomControls(true);
		mapview.setSatellite(true);
		mapview.setTraffic(true);

		mc = mapview.getController();
		String coordinates[] = { "1.352566007", "103.78921587" };
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);

		p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

		mc.animateTo(p);
		mc.setZoom(13);
		
		//-- Add a location marker--

		MapOverlay mapOverlay=new MapOverlay();
		List<Overlay>listofOverlays=mapview.getOverlays();
		listofOverlays.clear();
		listofOverlays.add(mapOverlay);


	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;

		// ...
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private class MapOverlay extends com.google.android.maps.Overlay {

		public boolean draw(Canvas canvas, MapView mapview, boolean shadow,
				long when) {

			super.draw(canvas, mapview, shadow);

						// -- translate the geopoint to screen pixels--

			Point ScreenPts = new Point();
			mapview.getProjection().toPixels(p, ScreenPts);

							// --add the maker--

		//	Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				//	R.drawable.pushpin);
			//canvas.drawBitmap(bmp, ScreenPts.x, ScreenPts.y - 50, null);
			return true;
		}

	}
}

