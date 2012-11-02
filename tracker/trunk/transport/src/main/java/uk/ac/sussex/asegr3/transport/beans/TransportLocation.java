package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportLocation {
	
	private double lattitude;
	private double longitude;
	private long timeStamp;
	
	public TransportLocation(){
		
		this(0.0, 0.0, 0L);
	}
	
	public TransportLocation(double lattitude, double longitude, long timeStamp){
		this.setLattitude(lattitude);
		this.setLongitude(longitude);
		this.setTimeStamp(timeStamp);
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
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
