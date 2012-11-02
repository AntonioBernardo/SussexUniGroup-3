//package uk.ac.sussex.asegr3.tracker.client.ui;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Point;
//
//import com.google.android.maps.GeoPoint;
//import com.google.android.maps.MapView;
//import com.google.android.maps.Overlay;
//
//class MapOverlay extends Overlay
//{
//  private GeoPoint pointToDraw;
//
//  public void setPointToDraw(GeoPoint point) {
//    pointToDraw = point;
//  }
//
//  public GeoPoint getPointToDraw() {
//    return pointToDraw;
//  }
//  
//  @Override
//  public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
//    super.draw(canvas, mapView, shadow);           
//
//    // convert point to pixels
//    Point screenPts = new Point();
//    mapView.getProjection().toPixels(pointToDraw, screenPts);
//
//    // add marker
////    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
//  //  canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 24, null);    
//    return true;
//  }
//} 
