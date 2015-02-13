/**
 * 
 */
package com.documentation.model;

import java.util.List;

import com.restdoc.annotations.ModelMethod;
import com.restdoc.annotations.ModelClass;

/**
 * @author inigo
 *
 */
@ModelClass(name="DocServices", description="Container of services")
public class DocServices {
	List<DocService> services;

	/**
	 * @return the services
	 */
	@ModelMethod(name="services", type="DocService[]")
	public List<DocService> getServices() {
		return services;
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(List<DocService> services) {
		this.services = services;
	}
}
