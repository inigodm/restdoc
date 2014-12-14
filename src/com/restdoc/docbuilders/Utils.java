/**
 * 
 */
package com.restdoc.docbuilders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
/**
 * @author inigo
 *
 */
public class Utils {
	
	/**
	 * @param clase
	 * @param classMethod
	 * @return
	 */
	public static ArrayList<Method> getMethodsAnnotatedWith(Class<?> clase, Class<? extends Annotation> classMethod){
		Method[] methodsAux = clase.getMethods();
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Method method : methodsAux){
			if (method.getAnnotation(classMethod) != null){
				methods.add(method);
			}
		}
		return methods;
	}
}
