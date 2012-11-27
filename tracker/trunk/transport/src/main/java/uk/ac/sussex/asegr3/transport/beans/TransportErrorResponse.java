package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used to return errors to client
 * @author andrewhaines
 *
 */
@XmlRootElement
public class TransportErrorResponse {

	public static enum ErrorCode {
		INVALID_API_REQUEST,
		INVALID_CREDENTIALS, 
		USER_ALREADY_EXISTS
	}

	public static final String ERROR_CODE = "errorCode";

	public static final String MESSAGE_TAG = "message";
	
	@XmlElement(name=ERROR_CODE, required=true)
	private ErrorCode errorCode;
	
	@XmlElement(name=MESSAGE_TAG, required=true)
	private String message;
	
	
	public TransportErrorResponse(ErrorCode errorCode, String message){
		setErrorCode(errorCode);
		setMessage(message);
	}
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
