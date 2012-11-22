package uk.ac.sussex.asegr3.tracker.server.services;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.comment.server.dao.CommentDao;
import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.CommentDTO;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceUnitTest {

	private static final String TEST_USERNAME = "testuser";
	private static final String TEST_TEXT = "testtext";
	private static final int TEST_LOCATIONID = 34435;
	private static final byte[] TEST_IMAGE = new byte[]{1,2,3,4};

	private CommentService candidate;
	
	@Mock
	private CommentDao locationDaoMock;
	
	@Mock
	private CommentDao commentDaoMock;
	
	private CommentDTO comment;
	
	@Before
	public void before(){
		
		candidate=new CommentService(commentDaoMock);
		comment=new CommentDTO(TEST_TEXT, TEST_LOCATIONID, TEST_IMAGE);
	}
	
	@Test
	public void givenComment_whenCallingStoreComment_thenDaoInvokedCorrectly(){
		candidate.storeComment(TEST_USERNAME, comment);
		verify(commentDaoMock, times(1)).insert(TEST_USERNAME,TEST_TEXT, TEST_LOCATIONID, TEST_IMAGE);
		
	}

}
