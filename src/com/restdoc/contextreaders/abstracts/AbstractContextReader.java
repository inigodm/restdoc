package com.restdoc.contextreaders.abstracts;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.sun.jersey.api.core.ResourceConfig;

public abstract class AbstractContextReader {
	static AbstractContextReader ctxReader;
	
	public abstract String[] readPackagesToDocumentate();
	public abstract String[] readAvailableDocGenerators();
	public abstract String[] readPathsToDTOs();
	public abstract String[] readAvailableDTODocGenerators();
	
	public static AbstractContextReader initContextReader(ResourceConfig context, String contextClass){
		if (ctxReader == null){
			try {
				Constructor<?> cons= (Constructor<?>) Class.forName(contextClass).getConstructor(ResourceConfig.class);
				ctxReader = (AbstractContextReader) cons.newInstance(context);
			} catch (NoSuchMethodException | SecurityException
					| ClassNotFoundException | InstantiationException
					| IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				System.err.println("ERROR: No se ha podido obtener el context reader " + e.getMessage());
				e.printStackTrace();
			}
		}
		return ctxReader;
	}
	
	public static AbstractContextReader getContextReader(){
		return ctxReader;
	}
	
	public static void setContextReader(AbstractContextReader context){
		ctxReader = context;
	}
}
