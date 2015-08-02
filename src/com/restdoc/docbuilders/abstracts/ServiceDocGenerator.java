package com.restdoc.docbuilders.abstracts;

import java.util.ArrayList;
import java.util.Collection;

import com.documentation.model.DocClass;
import com.documentation.model.DocService;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.docbuilders.ServiceDocBuilder;
import com.restdoc.docbuilders.classdocbuilders.DTODocDirector;

public class ServiceDocGenerator extends ServiceDocDirector{

	public ServiceDocGenerator(String[] buiderClasses) {
		super(buiderClasses);
	}

	@Override
	public Collection<? extends DocService> generateDocForPackage(String packag) {
		ArrayList<DocService> res = new ArrayList<DocService>();
		for (ServiceDocBuilder builder : builders){
			res.addAll(builder.buildDocForPackage(packag));
		}
		return res;
	}
	
}
