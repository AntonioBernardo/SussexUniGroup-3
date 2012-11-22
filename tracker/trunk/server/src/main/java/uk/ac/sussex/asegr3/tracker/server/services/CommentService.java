package uk.ac.sussex.asegr3.tracker.server.services;

import uk.ac.sussex.asegr3.comment.server.dao.CommentDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.CommentDTO;




public class CommentService {
	
	private final CommentDao dao;
	
	public CommentService(CommentDao dao){
		this.dao = dao;
	}
	
	public void storeComment(String username,CommentDTO comment){
		// Do the database integration here...
		// Store location in database
		
		dao.insert(username, comment.getText(), comment.getLocationID(), comment.getImage());
	}
}