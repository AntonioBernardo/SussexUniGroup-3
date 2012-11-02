package uk.ac.sussex.asegr3.tracker.client.ui;

import org.junit.Before;
import org.junit.Test;

import android.os.Bundle;
import android.test.ActivityUnitTestCase;

public class TrackingActivityAndroidUnitTest extends ActivityUnitTestCase<TrackingActivity>{

	public TrackingActivityAndroidUnitTest() {
		super(TrackingActivity.class);
	}

	private TrackingActivity candidate;
	
	@Before
	public void before(){
		candidate = getActivity();
	}
	
	@Test
	public void givenDefaultTrackingActivity_whenCallingOnCreate_thenActivityStarted(){
		candidate.onCreate(new Bundle());
	}

}
