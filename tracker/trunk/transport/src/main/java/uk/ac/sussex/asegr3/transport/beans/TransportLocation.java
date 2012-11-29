package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportLocation {
	
	public static final String LATTITUDE_TAG = "lattitude";
	public static final String LONGITUDE_TAG = "longitude";
	public static final String TIMESTAMP_TAG = "timestamp";
	public static final String ID_TAG = "id";
	
	@XmlElement(name=LATTITUDE_TAG, required=true)
	private double lattitude;
	
	@XmlElement(name=LONGITUDE_TAG, required=true)
	private double longitude;
	
	@XmlElement(name=TIMESTAMP_TAG, required=true)
	private long timestamp;
	
	@XmlElement(name=ID_TAG, required=false)
	private int id;
	
	public TransportLocation(){
		
		this(1, 0.0, 0.0, 0L);
	}
	
	public TransportLocation(int id, double lattitude, double longitude, long timeStamp){
		this.setId(id);
		this.setLattitude(lattitude);
		this.setLongitude(longitude);
		this.setTimestamp(timeStamp);
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

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timeStamp) {
		this.timestamp = timeStamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}
