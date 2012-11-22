package uk.ac.sussex.asegr3.tracker.client.dataobject;


public class PersonPosition extends MapObject {
	
	private double id;
	private String userName;
	private boolean current;
	
	public PersonPosition(double id, String userName, boolean current, double latt, double lon){
		super(latt, lon);
		this.id=id;
		this.userName=userName;
		this.current=current;
	}

	public double getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}
	
	public boolean isCurrent(){
		return current;
	}
	
	
	

}

