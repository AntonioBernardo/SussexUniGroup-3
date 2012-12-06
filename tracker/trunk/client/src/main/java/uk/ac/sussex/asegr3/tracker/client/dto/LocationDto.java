package uk.ac.sussex.asegr3.tracker.client.dto;

import java.util.Collection;

public class LocationDto implements Comparable<LocationDto>{

	private final String username;
	private final Collection<CommentDto> comments;
	private final double lat;
	private final double lng;
	private final long timestamp;
	
	public LocationDto(String username, double lat, double lng, long timestamp, Collection<CommentDto> comments){
		this.lat = lat;
		this.lng = lng;
		this.timestamp = timestamp;
		this.username = username;
		this.comments = comments;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(getLat());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getLng());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (getTimestamp() ^ (getTimestamp() >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationDto other = (LocationDto) obj;
		if (Double.doubleToLongBits(getLat()) != Double.doubleToLongBits(other.getLat()))
			return false;
		if (Double.doubleToLongBits(getLng()) != Double.doubleToLongBits(other.getLng()))
			return false;
		if (getTimestamp() != other.getTimestamp())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocationDto [lat=" + lat + ", lng=" + lng + ", timestamp="
				+ timestamp + "]";
	}

	@Override
	public int compareTo(LocationDto another) {
		return (int)(this.getTimestamp() - another.getTimestamp());
	}

	public String getUsername() {
		return username;
	}

	public Collection<CommentDto> getComments() {
		return comments;
	}
	
	
}
