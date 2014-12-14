package com.restdoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RESTMethod {
	/**value : Name of model class consumed
	 * @return
	 */
	public String consumedObject() default "";
	/**Name of model class produced
	 * @return
	 */
	public String producedObject() default "";
	public String description() default "";
	/** Package in which there will be the models
	 * @return
	 */
	public String modelpath() default "";
	public String productedMimetype() default "";
	public String consumedMimetype() default "";	
	/** HTTP method: GET, PUT, POST or DELETE
	 * @return
	 */
	public String httpMethod() default "GET";
	public String path() default "/";
}
