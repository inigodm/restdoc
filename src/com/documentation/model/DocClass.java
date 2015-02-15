package com.documentation.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.restdoc.annotations.ModelMethod;
import com.restdoc.annotations.ModelClass;

@XmlRootElement
@ModelClass(name="DocObject", description="Container of objects")
public class DocClass {
	private String name;
	private String description;
	private String version;
	private List<DocField> fields;
	private List<DocClass> generics;
	//private String packagePath;
	/**
	 * @return the name
	 */
	@ModelMethod(name="name", type="String", description="Name shown")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	@ModelMethod(name="description", type="String", description="description shown")
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
	 * @return the version
	 */
	@ModelMethod(name="version", type="String", description="version")
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the fields
	 */
	@ModelMethod(name="fields", type="DocObjField[]")
	public List<DocField> getFields() {
		return fields;
	}
	/**
	 * @param arrayList the fields to set
	 */
	public void setFields(List<DocField> arrayList) {
		this.fields = arrayList;
	}
	public List<DocClass> getGenerics() {
		return generics;
	}
	public void setGenerics(List<DocClass> generics) {
		this.generics = generics;
	}
}
