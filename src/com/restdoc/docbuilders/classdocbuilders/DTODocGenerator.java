package com.restdoc.docbuilders.classdocbuilders;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;

public class DTODocGenerator extends DTODocDirector{
	
	public DTODocGenerator(String[] buiderClasses) {
		super(buiderClasses);
	}

	@Override
	public void setupForService(Class<?> annotatedClass) {
		for (DTODocBuilder builder : builders){
			setupBuilderForService(builder, annotatedClass);
		}
	}
	
	private void setupBuilderForService(DTODocBuilder builder, Class<?> annotatedClass){
		try {
			builder.setupForService(annotatedClass);
		} catch (NotARESTServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DocClass buildDoc(String className) {
		DocClass res = null;
		for (DTODocBuilder builder : builders){
			res = builder.buildDoc(className);
			if (res != null){
				break;
			}
		}
		return res;
	}
}
