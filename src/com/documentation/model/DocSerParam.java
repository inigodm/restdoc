/**
 * 
 */
package com.documentation.model;

/**
 * @author inigo
 *
 */
public class DocSerParam {
	private String name;
	private String restParamType;
	private DocClass paramType;
	
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
}
