package uk.ac.sussex.asegr3.tracker.server.domainmodel;

public class LocationDTO {
	
	
	private double latitude;
	private double longitude;
	private long timeStamp;
	
	
	public LocationDTO(double latitude, double longitude, long timeStamp){
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setTimeStamp(timeStamp);
		
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


	public long getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
