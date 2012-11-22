package uk.ac.sussex.asegr3.prototype.dataobjects;

import java.util.List;

public class Comment extends MapObject {
	
	private String name;
	private double id;
	private List<String> authors;
	private List<String> texts;
	
	public Comment(double id, String name, List<String> authors, List<String> texts,double latt, double lon){
		super(latt, lon);
		this.id=id;
		this.name=name;
		this.authors=authors;
		this.texts=texts;
	}

	public String getName() {
		return name;
	}

	public double getId() {
		return id;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public List<String> getTexts() {
		return texts;
	}
	
	

}
