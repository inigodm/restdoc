package com.restdoc.docbuilders.abstracts;

import java.util.ArrayList;
import java.util.Collection;

import com.documentation.model.DocService;
import com.restdoc.docbuilders.ServiceDocBuilder;

public abstract class ServiceDocDirector {
	protected ArrayList<ServiceDocBuilder> builders = new ArrayList<ServiceDocBuilder>();
	
	public abstract Collection<? extends DocService> generateDocForPackage(String packag);
	
	protected ServiceDocDirector(String[] buiderClasses) {
		for (String builder : buiderClasses){
			if (!isBuilderInList(builder)){
				addInstanceOfBuilder(builder);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void addInstanceOfBuilder(String builderClassName){
		try {
			Class<ServiceDocBuilder> clazz = (Class<ServiceDocBuilder>) Class.forName(builderClassName);
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
		for (ServiceDocBuilder b : builders){
			if (b.getClass().getName().equals(builder)){
				return true;
			}
		}
		return false;
	}
}
