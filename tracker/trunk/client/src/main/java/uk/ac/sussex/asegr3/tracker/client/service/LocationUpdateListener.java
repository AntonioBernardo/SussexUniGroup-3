package uk.ac.sussex.asegr3.tracker.client.service;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;

public interface LocationUpdateListener {

	void notifyNewLocation(LocationDto location);
}
