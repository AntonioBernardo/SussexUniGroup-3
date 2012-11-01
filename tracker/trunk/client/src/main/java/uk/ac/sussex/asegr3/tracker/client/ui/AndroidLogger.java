package uk.ac.sussex.asegr3.tracker.client.ui;

import android.util.Log;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

public class AndroidLogger {

	public static final Logger INSTANCE = new Logger(){

		@Override
		public void debug(Class<?> className, String message) {
			Log.d(className.getName(), message);
		}

		@Override
		public void warn(Class<?> className, String message) {
			Log.w(className.getName(), message);
		}

		@Override
		public void error(Class<?> className, String message) {
			Log.e(className.getName(), message);
		}
		
	};

}
