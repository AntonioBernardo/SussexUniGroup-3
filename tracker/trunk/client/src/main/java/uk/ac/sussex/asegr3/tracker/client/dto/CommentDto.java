package uk.ac.sussex.asegr3.tracker.client.dto;

public class CommentDto {

	private final String poster;
	private final String comment;
	private final long timeStamp;
	
	public CommentDto(String poster, String comment, long timeStamp){
		this.poster = poster;
		this.comment = comment;
		this.timeStamp = timeStamp;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public String getComment() {
		return comment;
	}

	public String getPoster() {
		return poster;
	}
}
