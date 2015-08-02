/**
 * 
 */
package com.documentation.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author inigo
 *
 */
public class DocSerParam {
	public static String PATHPARAM = "PathParam";
	private String name;
	private String restParamType;
	private DocClass paramType;
	private Map<String, String> annotationKeyValues;
	
	public DocClass getParamType() {
		return paramType;
	}
	
	public void setParamType(DocClass paramType) {
		this.paramType = paramType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRestParamType() {
		return restParamType;
	}
	
	public void setRestParamType(String restParamType) {
		this.restParamType = restParamType;
	}

	public void putAnnotation(String key, String value) {
		if (annotationKeyValues == null){
			annotationKeyValues = new LinkedHashMap<String, String>();
		}
		annotationKeyValues.put(key, value);
	}

	public Map<String, String> getAnnotationKeyValues() {
		return annotationKeyValues;
	}

	public void setAnnotationKeyValues(Map<String, String> annotationKeyValues) {
		this.annotationKeyValues = annotationKeyValues;
	}
}
