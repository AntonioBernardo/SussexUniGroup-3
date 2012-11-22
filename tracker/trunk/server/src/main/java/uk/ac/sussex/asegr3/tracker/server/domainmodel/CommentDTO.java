package uk.ac.sussex.asegr3.tracker.server.domainmodel;

public class CommentDTO {

	private String text;
	private int locationID;
	private byte[] image;
	
	
	public  CommentDTO( String text, int locationID, byte[] image){
		this.setText(text);
		this.setLocationID(locationID);
		this.setImage(image);
		
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
	
	
}
