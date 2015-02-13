package com.restdoc.mocks;

import com.restdoc.contextreaders.abstracts.AbstractContextReader;

public class ReflectionContextReaderMock extends AbstractContextReader {

	@Override
	public String[] readPackagesToDocumentate() {
		return new String[] {"com.restdoc.services"};
	}
	
	@Override
	public String[] readAvailableDTODocGenerators() {
		System.out.println("Using ReflectionDTOBuilder as DTO documentation builder");
		return new String[] {"com.restdoc.docbuilders.classdocbuilders.ReflectionDTOBuilder"};
	}

	@Override
	public String[] readAvailableDocGenerators() {
		System.out.println("Using ServiceDocJAXRSConcreteBuilder as service documentation builder");
		return new String[]{"com.restdoc.docbuilders.ServiceDocJAXRSConcreteBuilder"};
	}

	@Override
	public String[] readPathsToModels() {
		return new String[]{"com.documentation.model"};
	}
}