package uk.ac.sussex.asegr3.prototype;

import com.google.android.maps.MapActivity;

import android.os.Bundle;

public class HelloAndroidActivity extends MapActivity {
	
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
    }
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

