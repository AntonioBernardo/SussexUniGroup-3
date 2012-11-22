package uk.ac.sussex.asegr3.tracker.server.domainmodel;

public class LocationDTO {
	
	private String username;
	private double latitude;
	private double longitude;
	private long timestamp;
	
	public LocationDTO(String username,double latitude, double longitude, long timeStamp){
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setTimestamp(timeStamp);
		this.setUsername(username);
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
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
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
		LocationDTO other = (LocationDTO) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}
}
