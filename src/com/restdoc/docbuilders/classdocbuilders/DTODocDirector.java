package com.restdoc.docbuilders.classdocbuilders;

import java.util.ArrayList;

import com.documentation.model.DocClass;

public class DTODocDirector {
	ArrayList<DTODocBuilder> builders = new ArrayList<DTODocBuilder>();
	
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

	public DTODocDirector(String[] buiderClasses) {
		for (String builder : buiderClasses){
			if (!isBuilderInList(builder)){
				addInstanceOfBuilder(builder);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addInstanceOfBuilder(String builderClassName){
		try {
			Class<DTODocBuilder> clazz = (Class<DTODocBuilder>) Class.forName(builderClassName);
			builders.add(clazz.newInstance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassCastException e){
			//No es un ServiceDocBuilder
			e.printStackTrace();
		}
	}
	
	public boolean isBuilderInList(String builder){
		for (DTODocBuilder b : builders){
			if (b.getClass().getName().equals(builder)){
				return true;
			}
		}
		return false;
	}
}
