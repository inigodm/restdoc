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
		String[] generators = ((String) context.getProperty("com.restdoc.generators")).split(";");
		if (generators == null || generators.length == 0){
			return new String[]{"com.restdoc.docbuilders.ServiceDocRESTDOCConcreteBuilder"};
		}
		return generators;
	}

	@Override
	public String[] readPathsToDTOs() {
		String path = ((String) context.getProperty("com.restdoc.pathstoDTOs"));
		if (path == null || path.length() == 0){
			return new String[]{};
		}
		return path.split(";");
	}
	
	@Override
	public String[] readAvailableDTODocGenerators() {
		String generators = ((String) context.getProperty("com.restdoc.dtogenerators"));
		if (generators == null || generators.length() == 0){
			return new String[]{"com.restdoc.docbuilders.classdocbuilders.RESTDocDTOBuilder"};
		}
		return generators.split(";");
	}
}
