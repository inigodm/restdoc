package com.documentation.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.restdoc.annotations.ModelMethod;
import com.restdoc.annotations.ModelClass;
@XmlRootElement
@ModelClass(name="DocObject", description="Container of objects")
public class DocClasses extends DocClass{
	List<DocClass> objects;

	/**
	 * @return the objects
	 */
	@ModelMethod(name="objects", type="DocObject[]")
	public List<DocClass> getObjects() {
		return objects;
	}

	/**
	 * @param objects the objects to set
	 */
	public void setObjects(List<DocClass> objects) {
		this.objects = objects;
	}
}
