package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.List;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;

public interface FetchLocationCallBack {
	
	public void processFetchLocations(List<LocationDto> locations);
	
	public void processFetchFailed(Exception e);

}
