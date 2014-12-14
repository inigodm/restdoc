/**
 * 
 */
package com.documentation.annotations.exceptions;

/**
 * @author inigo
 *
 */
public class NotARESTServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotARESTServiceException(String string) {
		super(string);
	}

	public NotARESTServiceException(String string, NullPointerException e) {
		super(string, e);
	}

}
