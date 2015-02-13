package com.restdoc.contextreaders;

import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.sun.jersey.api.core.ResourceConfig;

public class DefaultContextReader extends AbstractContextReader {
	ResourceConfig context;
	
	public DefaultContextReader(ResourceConfig context) {
		this.context = context;
	}
	
	@Override
	public String[] readPackagesToDocumentate() {
		return ((String) context.getProperty("com.restdoc.packages")).split(";");
	}

	@Override
	public String[] readAvailableDocGenerators() {
		return getProperty("com.restdoc.generators", new String[]{"com.restdoc.docbuilders.ServiceDocRESTDOCConcreteBuilder"});
	}

	@Override
	public String[] readPathsToModels() {
		return getProperty("com.restdoc.models", new String[]{});
	}
	
	@Override
	public String[] readAvailableDTODocGenerators() {
		return getProperty("com.restdoc.dtogenerators", new String[]{"com.restdoc.docbuilders.classdocbuilders.RESTDocDTOBuilder"});
	}
	
	private String[] getProperty(String property, String[] defaultValue){
		String prop = getPropertyFromContext(property);
		if (prop == null || prop.length() == 0){
			return defaultValue;
		}
		return prop.split(";");		
	}

	private String getPropertyFromContext(String property){
		return ((String) context.getProperty(property));
	}
}
