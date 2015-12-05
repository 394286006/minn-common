package p.minn.common.exception;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.apache.commons.codec.DecoderException;
/**
 * 
 * @author minn
 * @QQ:3942986006
 *
 */
public class Exceptions {

	public static RuntimeException unchecked(GeneralSecurityException e) {
		// TODO Auto-generated method stub
		return new RuntimeException(e.getMessage());
	}

	public static RuntimeException unchecked(DecoderException e) {
		// TODO Auto-generated method stub
		return new RuntimeException(e.getMessage());
	}

	public static RuntimeException unchecked(UnsupportedEncodingException e) {
		// TODO Auto-generated method stub
		return new RuntimeException(e.getMessage());
	}

}
