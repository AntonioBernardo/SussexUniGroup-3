package uk.ac.sussex.asegr3.tracker.server;


public class SystemClock implements Clock {

	@Override
	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

}
