package uk.ac.sussex.asegr3.transport.beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="batch")
public class TransportLocationBatch {
	
	public static final String LOCATIONS_TAG = "locations";
	
	@XmlElement(name=LOCATIONS_TAG, required=true)
	private Collection<TransportLocation> locations;

	
	public TransportLocationBatch(){
		
		this(new ArrayList<TransportLocation>());
	}
	
	
	public TransportLocationBatch(Collection<TransportLocation> locations){
		this.setLocations(locations);
	}

	
	public Collection<TransportLocation> getLocations() {
		return locations;
	}

	
	public void setLocations(Collection<TransportLocation> locations) {
		this.locations = locations;
	}
	
	public void addLocation(TransportLocation location){
		locations.add(location);
	}
	
	public void removeLocation(TransportLocation location){
		locations.remove(location);
	}
	
	
	
	

}
