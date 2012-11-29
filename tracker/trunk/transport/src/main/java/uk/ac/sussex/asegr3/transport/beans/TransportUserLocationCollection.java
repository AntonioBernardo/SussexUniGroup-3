package uk.ac.sussex.asegr3.transport.beans;

import java.util.Collection;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="batch")
public class TransportUserLocationCollection {

	public static final String LOCATIONS_TAG = "locations";
	
	@XmlElement(name=LOCATIONS_TAG, required=true)
	private Collection<TransportUserLocation> locations;
	
	public TransportUserLocationCollection(){
		locations = new LinkedList<TransportUserLocation>();
	}

	public Collection<TransportUserLocation> getLocations() {
		return locations;
	}

	public void setLocations(Collection<TransportUserLocation> locations) {
		this.locations = locations;
	}
}
