package uk.ac.sussex.asegr3.tracker.client.location;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;

/**
 * Domain object to represent a number of batches. The ordering of the iterable is assumed to be
 * ordered by time created and is not enforced.
 * @author andrewhaines
 *
 */
public class LocationBatch {

	private final Iterable<LocationDto> locations;
	private final int batchNum;
	
	public LocationBatch(Iterable<LocationDto> locations, int batchNum){
		this.locations = locations;
		this.batchNum = batchNum;
	}

	public Iterable<LocationDto> getLocations() {
		return locations;
	}

	public int getBatchNum() {
		return batchNum;
	}
}
