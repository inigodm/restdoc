package com.restdoc.docbuilders.classdocbuilders;

import com.documentation.model.DocClass;

public class DTODocGenerator extends DTODocDirector{
	
	public DTODocGenerator(String[] buiderClasses) {
		super(buiderClasses);
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
