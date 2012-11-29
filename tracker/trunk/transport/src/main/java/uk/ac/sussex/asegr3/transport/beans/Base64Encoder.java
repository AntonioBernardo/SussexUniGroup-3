package uk.ac.sussex.asegr3.transport.beans;

import java.io.Serializable;

public interface Base64Encoder extends Serializable{

	String encode(byte[] bytes);
	
	byte[] decode(String code);
}
