/**
 * 
 */
package com.restdoc.docbuilders.classdocbuilders;

import java.util.ArrayList;
import java.util.Set;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;
import com.documentation.model.DocClasses;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;

/**
 * @author inigo
 *
 */
public abstract class DTODocBuilder {
	/**
	 * Classes that will be scanned to documentate
	 */
	protected Set<Class<?>> models;
	protected ArrayList<String> buildedDoc = new ArrayList<String>();
	String[] modelPaths;
	
	public DTODocBuilder(){
		AbstractContextReader ctxReader = AbstractContextReader.getContextReader();
		modelPaths = ctxReader.readPathsToModels();
	}
	
	/**
	 * Build the documentation that will be server for a DTO class
	 * @param className
	 * @return
	 */
	public DocClass buildDoc(String className) {
		if (buildedDoc.contains(className)){
			return fillPrimitiveOrJavaObject(className);
		}
		buildedDoc.add(className);
		boolean isAnArray = false;
		String classNameClean = className;
		if (className.endsWith("[]")){
			isAnArray = true;
			classNameClean = className.substring(0, className.length() - 2);
		}
		return createDoc(classNameClean, isAnArray);
	}

	private DocClass createDoc(String classname, boolean isAnArray){
		try {
			for (String path : modelPaths){
				if (classname.startsWith(path)){
					Class<?> c = ClassLoader.getSystemClassLoader().loadClass(classname);
					if (isAnArray){
						return fillArrayDoc(c);
					}
					return fillClassDoc(c);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return fillPrimitiveOrJavaObject(classname);
	}

	/** Method to fill the build the info that must be extracted from java API class. 
	 * We usually don't have to do any reflections on this 
	 * @param annArray the array to extract info from
	 * @return
	 */
	protected DocClass fillPrimitiveOrJavaObject(String clase){
		DocClass obj = new DocClass();
		obj.setName(clase);
		return obj;
	}
	
	/** Method to fill the build the info that must be extracted from each array of classes
	 * @param annArray the array to extract info from
	 * @return
	 */
	protected DocClasses fillArrayDoc(Class<?> annClass){
		DocClasses container = new DocClasses();
		ArrayList<DocClass> aux = new ArrayList<DocClass>();
		aux.add(fillClassDoc(annClass));
		container.setObjects(aux);
		return container;
	}
	
	/** Method to fill the build the info that must be extracted from each class
	 * @param annArray the array to extract info from
	 * @return
	 */
	protected abstract DocClass fillClassDoc(Class<?> objectClass);
		
	/**
	 * Initializes the builder to be used with the given class.
	 * Basically initializes the models that must be scanned to get info from them.
	 * @param annotatedClass
	 * @throws NotARESTServiceException 
	 */
	//protected abstract void setupForService(Class<?> annotatedClass) throws NotARESTServiceException;
}
