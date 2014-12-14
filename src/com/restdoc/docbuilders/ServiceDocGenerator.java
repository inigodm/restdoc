package com.restdoc.docbuilders;

import java.util.ArrayList;
import java.util.Collection;

import com.documentation.model.DocService;
import com.restdoc.docbuilders.abstracts.ServiceDocBuilder;
import com.restdoc.docbuilders.abstracts.ServiceDocDirector;

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
