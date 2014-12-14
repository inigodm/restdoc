/**
 * 
 */
package com.restdoc.docbuilders.classdocbuilders;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;

/**
 * @author inigo
 *
 */
public interface DTODocBuilder {
	/**
	 * Build the documentation that will be server for a DTO class
	 * @param className
	 * @return
	 */
	DocClass buildDoc(String className);

	/**
	 * Initializes the builder to be used with the given class
	 * @param annotatedClass
	 * @throws NotARESTServiceException 
	 */
	void setupForService(Class<?> annotatedClass) throws NotARESTServiceException;
}
