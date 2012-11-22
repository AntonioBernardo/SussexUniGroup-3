package uk.ac.sussex.asegr3.tracker.client.sytem;

import java.io.Serializable;

import android.net.NetworkInfo;

public interface NetworkInfoProvider extends Serializable{

	NetworkInfo getNetworkInfo();
}
