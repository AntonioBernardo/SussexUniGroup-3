package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportComment {

	public static final String TEXT_TAG = "text";
	public static final String LOCATION_ID_TAG = "locationId";
	public static final String IMAGE_TAG = "image";
	public static final String TIMESTAMP_TAG = "timestamp";
	public static final String POSTER_TAG = "poster";
	
	@XmlElement(name=TEXT_TAG, required=true)
	private String text;
	
	@XmlElement(name=POSTER_TAG, required=true)
	private String poster;
	
	@XmlElement(name=LOCATION_ID_TAG, required=true)
	private int location_id;
	
	@XmlElement(name=IMAGE_TAG, required=false)
	private byte[] image;
	
	@XmlElement(name=TIMESTAMP_TAG, required=true)
	private long timestamp;
	
	public TransportComment(){
		
		this("", "", 1, new byte[]{}, 1);
	
	}
	public TransportComment(String poster, String text, int location_id, byte[] b, long timestamp){
		this.setPoster(poster);
		this.setText(text);
		this.setLocation_ID(location_id);
		this.setImage(b);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLocationId() {
		return location_id;
	}

	public void setLocation_ID(int location_id) {
		this.location_id = location_id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
}
