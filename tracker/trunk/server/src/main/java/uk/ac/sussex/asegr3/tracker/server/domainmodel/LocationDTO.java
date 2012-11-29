package uk.ac.sussex.asegr3.tracker.server.domainmodel;

import java.util.Collection;

public class LocationDTO {
	
	private int id;
	private String username;
	private double latitude;
	private double longitude;
	private long timestamp;
	private Collection<CommentDTO> comments;
	
	public LocationDTO(int id, String username,double latitude, double longitude, long timeStamp, Collection<CommentDTO> comments){
		this.setId(id);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setTimestamp(timeStamp);
		this.setUsername(username);
		this.setComments(comments);
	}


	private void setId(int id) {
		this.id = id;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timeStamp) {
		this.timestamp = timeStamp;
	}



	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((comments == null) ? 0 : comments.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LocationDTO))
			return false;
		LocationDTO other = (LocationDTO) obj;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	public Collection<CommentDTO> getComments() {
		return comments;
	}


	public void setComments(Collection<CommentDTO> comments) {
		this.comments = comments;
	}


	public int getId() {
		return id;
	}
}
