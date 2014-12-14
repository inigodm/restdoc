package com.documentation.model;



import javax.xml.bind.annotation.XmlRootElement;

import com.restdoc.annotations.ModelMethod;
import com.restdoc.annotations.ModelClass;

@XmlRootElement
@ModelClass(name="DocSerMethod", description="Service method description")
public class DocSerMethod {
	private String method;
	private String path;
	private String description;
	private String modelpath;
	private String producedMimetype;
	/**value : XML|JSON|XMLJSON,com.to.your.model.package
	 * @return
	 */
	private DocClass producedObject;
	private String consumedMimetype;
	/**value : XML|JSON|XMLJSON,com.to.your.model.package
	 * @return
	 */
	private DocClass consumedObject;
	/**
	 * @return the consumedObject
	 */
	@ModelMethod(name="consumedObject", type="DocObject")
	public DocClass getConsumedObject() {
		return consumedObject;
	}
	/**
	 * @param consumedObject the consumedObject to set
	 */
	public void setConsumedObject(DocClass consumedObject) {
		this.consumedObject = consumedObject;
	}
	/**
	 * @return the producedObject
	 */
	@ModelMethod(name="producedObject", type="DocObject")
	public DocClass getProducedObject() {
		return producedObject;
	}
	/**
	 * @param producedObject the producedObject to set
	 */
	public void setProducedObject(DocClass producedObject) {
		this.producedObject = producedObject;
	}
	/**
	 * @return the method
	 */
	@ModelMethod(name="method", type="String")
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
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
	 * @return the producedMimetype
	 */
	@ModelMethod(name="producedMimetype", type="String")
	public String getProducedMimetype() {
		return producedMimetype;
	}
	/**
	 * @param producedMimetype the producedMimetype to set
	 */
	public void setProducedMimetype(String producedMimetype) {
		this.producedMimetype = producedMimetype;
	}
	/**
	 * @return the consumedMimetype
	 */
	@ModelMethod(name="consumedMimetype", type="String")
	public String getConsumedMimetype() {
		return consumedMimetype;
	}
	/**
	 * @param consumedMimetype the consumedMimetype to set
	 */
	public void setConsumedMimetype(String consumedMimetype) {
		this.consumedMimetype = consumedMimetype;
	}
}
