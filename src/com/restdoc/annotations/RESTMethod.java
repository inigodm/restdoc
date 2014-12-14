package com.restdoc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RESTMethod {
	/**value : XML|JSON|XMLJSON,com.to.your.model.package
	 * @return
	 */
	public String consumedObject() default "";
	/**value : XML|JSON|XMLJSON,com.to.your.model.package
	 * @return
	 */
	public String producedObject() default "";
	public String description() default "";
	public String modelpath() default "";
	public String productedMimetype() default "";
	public String consumedMimetype() default "";
}
