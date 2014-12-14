/**
 * 
 */
package com.restdoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author inigo
 *
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ModelMethod {
	public String name();
	public String type();
	public String description() default "";
	public String required() default "";
	public String size() default "";
}
