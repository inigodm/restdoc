package com.restdoc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.mocks.ContextReaderMock;
import com.restdoc.services.RESTDocService;

public class RESTDocServiceTest {
	RESTDocService rest;
	ContextReaderMock ctx;
	String resp;
	@Before
	public void init(){
	  rest = new RESTDocService();
	  ctx = new ContextReaderMock();
	  AbstractContextReader.setContextReader(ctx);
	  resp = "{\"services\":[{\"path\":\"/rest-doc\",\"description\":\"REST services descriptions\",\"modelpath\":\"com.documentation.model\",\"methods\":[{\"method\":\"GET\",\"path\":\"all\",\"description\":\"Returns objects describing the services in this web application\",\"modelpath\":\"\",\"producedMimetype\":\"JSON\",\"producedObject\":{\"name\":\"DocService\",\"description\":\"Service description\",\"version\":\"\",\"fields\":[{\"name\":\"modelpath\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"description\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"methods\",\"type\":\"DocSerMethod\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"DocSerMethod\",\"description\":\"Service method description\",\"version\":\"\",\"fields\":[{\"name\":\"modelpath\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"consumedObject\",\"type\":\"DocObject\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"DocObject\"}},{\"name\":\"producedObject\",\"type\":\"DocObject\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"DocObject\"}},{\"name\":\"producedMimetype\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"consumedMimetype\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"description\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"method\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"path\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}}]}},{\"name\":\"path\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}}]},\"consumedMimetype\":\"\",\"consumedObject\":{\"name\":\"\"}}]}]}";
	}
	
	@Test
	public void globalTests() {
		assertNotNull(rest.doGet().toString());
	}
	
}