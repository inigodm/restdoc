package com.restdoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RESTService {
	public String description() default "";
	public String modelpath() default "";
	/** Path of the service: the URL in which the service will be
	 * @return
	 */
	public String path() default "";
}
