package uk.ac.sussex.asegr3.tracker.server.domainmodel;

public class CommentDTO {

	private String username;
	private String text;
	private int locationID;
	private byte[] image;
	private long timestamp;
	
	public  CommentDTO(String username, String text, int locationID, byte[] image, long timestamp){
		this.setText(text);
		this.setLocationID(locationID);
		this.setImage(image);
		this.setUsername(username);
		this.setTimestamp(timestamp);
	}
	
	public String getUsername(){
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
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
}
