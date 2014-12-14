package com.restdoc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.documentation.model.DocMethod;
import com.documentation.model.DocSerMethod;
import com.documentation.model.DocService;
import com.documentation.model.DocServices;
import com.google.gson.Gson;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.mocks.ContextReaderMock;
import com.restdoc.services.RESTDocService;

public class RESTDocServiceTest {
	RESTDocService rest;
	ContextReaderMock ctx;
	String resp;
	DocServices respdoc;
	Gson gson = new Gson();
	@Before
	public void init(){
	  rest = new RESTDocService();
	  ctx = new ContextReaderMock();
	  AbstractContextReader.setContextReader(ctx);
	  resp = "{\"services\":[{\"path\":\"/rest-doc\",\"description\":\"REST services descriptions\",\"modelpath\":\"com.documentation.model\",\"methods\":[{\"method\":\"GET\",\"path\":\"all\",\"description\":\"Returns objects describing the services in this web application\",\"modelpath\":\"\",\"producedMimetype\":\"JSON\",\"producedObject\":{\"name\":\"DocService\",\"description\":\"Service description\",\"version\":\"\",\"fields\":[{\"name\":\"modelpath\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"description\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"methods\",\"type\":\"DocSerMethod\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"DocSerMethod\",\"description\":\"Service method description\",\"version\":\"\",\"fields\":[{\"name\":\"modelpath\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"consumedObject\",\"type\":\"DocObject\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"DocObject\"}},{\"name\":\"producedObject\",\"type\":\"DocObject\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"DocObject\"}},{\"name\":\"producedMimetype\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"consumedMimetype\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"description\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"method\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}},{\"name\":\"path\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}}]}},{\"name\":\"path\",\"type\":\"String\",\"description\":\"\",\"required\":\"\",\"size\":\"\",\"typeDesc\":{\"name\":\"String\"}}]},\"consumedMimetype\":\"\",\"consumedObject\":{\"name\":\"\"}}]}]}";
	  respdoc = gson.fromJson(resp, DocServices.class);
	}
	
	@Test
	public void globalTests() {
		DocServices res = rest.doGet();
		assertNotNull(res);
		System.out.print("no null" + res);
		for (int i = 0; i < res.getServices().size(); i++){
			DocService service = res.getServices().get(i);
			DocService servicetoTest = respdoc.getServices().get(i);
			assertEquals(service.getDescription(), servicetoTest.getDescription());
			assertEquals(service.getModelpath(), servicetoTest.getModelpath());
			assertEquals(service.getPath(), servicetoTest.getPath());
			for (int j=0; j < service.getMethods().size(); j++){
			    DocSerMethod method= service.getMethods().get(j);
			    DocSerMethod methodtoTest= servicetoTest.getMethods().get(j);
			    assertEquals(method.getConsumedMimetype(), methodtoTest.getConsumedMimetype());
			    assertEquals(method.getConsumedObject().getName(), methodtoTest.getConsumedObject().getName());
			    assertEquals(method.getDescription(), methodtoTest.getDescription());
			    assertEquals(method.getMethod(), methodtoTest.getMethod());
			    assertEquals(method.getModelpath(), methodtoTest.getModelpath());
			    assertEquals(method.getPath(), methodtoTest.getPath());
			    assertEquals(method.getProducedMimetype(), methodtoTest.getProducedMimetype());
			    assertEquals(method.getProducedObject().getName(), methodtoTest.getProducedObject().getName());
			}
		}
	}
	
}