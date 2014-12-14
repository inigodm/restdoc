package com.restdoc.mocks;

import com.restdoc.contextreaders.abstracts.AbstractContextReader;

public class ContextReaderMock extends AbstractContextReader {

	@Override
	public String[] readPackagesToDocumentate() {
		return new String[] {"com.restdoc.services"};
	}
	
	@Override
	public String[] readAvailableDTODocGenerators() {
		return new String[] {"com.restdoc.docbuilders.classdocbuilders.RESTDocDTOBuilder"};
	}

	@Override
	public String[] readAvailableDocGenerators() {
		return new String[]{"com.restdoc.docbuilders.ServiceDocRESTDOCConcreteBuilder"};
	}

	@Override
	public String[] readPathsToDTOs() {
		// TODO Auto-generated method stub
		return null;
	}
}