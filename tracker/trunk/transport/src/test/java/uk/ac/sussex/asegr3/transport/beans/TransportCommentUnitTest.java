package uk.ac.sussex.asegr3.transport.beans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class TransportCommentUnitTest {

	private static final String TEST_COMMENT = "A test comment";
	private static final int TEST_LOC_ID = 33;
	private static final byte[] TEST_BYTES = new byte[]{0,1,2,3,4,6,7,8,9};
	private static final String TEST_POSTER = "poster";
	private static final long TEST_TIMESTAMP = 4356452;
	private TransportComment candidate;
	
	@Before
	public void before(){
		this.candidate = new TransportComment(TEST_POSTER, TEST_COMMENT, TEST_LOC_ID, TEST_BYTES, TEST_TIMESTAMP);
	}
	
	@Test
	public void givenComment_whenCallingGetText_thenCorrectTextReturned(){
		assertThat(candidate.getText(), is(equalTo(TEST_COMMENT)));
	}
	
	@Test
	public void givenComment_whenCallingGetLocationId_thenCorrectIdReturned(){
		assertThat(candidate.getLocationId(), is(equalTo(TEST_LOC_ID)));
	}
	
	@Test
	public void givenComment_whenCallingGetImage_thenCorrectBytesReturned(){
		assertThat(candidate.getImage(), is(equalTo(TEST_BYTES)));
	}
}
