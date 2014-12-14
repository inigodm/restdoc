package com.documentation.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.restdoc.annotations.ModelMethod;
import com.restdoc.annotations.ModelClass;

@XmlRootElement
@ModelClass(name="DocService", description="Service description")
public class DocService {
	private String path;
	private String description;
	private String modelpath;
	private List<DocSerMethod> methods;
	/**
	 * @return the path
	 */
	@ModelMethod(name="path", type="String")
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the description
	 */
	@ModelMethod(name="description", type="String")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the modelpath
	 */
	@ModelMethod(name="modelpath", type="String")
	public String getModelpath() {
		return modelpath;
	}
	/**
	 * @param modelpath the modelpath to set
	 */
	public void setModelpath(String modelpath) {
		this.modelpath = modelpath;
	}
	/**
	 * @return the methods
	 */
	@ModelMethod(name="methods", type="DocSerMethod")
	public List<DocSerMethod> getMethods() {
		return methods;
	}
	/**
	 * @param methods the methods to set
	 */
	public void setMethods(List<DocSerMethod> methods) {
		this.methods = methods;
	}
}
