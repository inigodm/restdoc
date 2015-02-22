package com.restdoc.docbuilders.abstracts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocService;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.docbuilders.classdocbuilders.DTODocDirector;
import com.sun.jersey.api.NotFoundException;

public abstract class ServiceDocBuilder {
	protected DTODocDirector dtoDocDirector;
	/**
	 * Used to determine which annotation will mark a class like a REST service that this 
	 * ServiceDocBuilder is able to scan for info
	 */
	protected Class<? extends Annotation> annotationToLocateInClasses;
	
	/** Obtains or generates docs for setted service
	 * @return
	 */
	public abstract DocService getServiceDocForClass(Class<?> annotatedClass) throws NotARESTServiceException;
	
	/** Builder to generate documentation from a REST service
	 * @param pathAnn
	 * @param restServiceAnn
	 */
	protected ServiceDocBuilder(Class<? extends Annotation> restServiceAnn){
		dtoDocDirector = new DTODocDirector(AbstractContextReader.getContextReader().readAvailableDTODocGenerators());
		annotationToLocateInClasses = restServiceAnn;
	}
	
	/** Method to build documentation for a given package.It search for REST service annotations in that package
	 * and returns generated doc.
	 * 
	 * @param packag
	 * @return
	 */
	public Collection<? extends DocService> buildDocForPackage(String packag) {
		List<Class<?>> clases = getAllRESTServicesFromPackage(packag);
		List<DocService> serv = new ArrayList<DocService>();
		DocService aux;
		for (Class<?> clase : clases){
			// se ha comprobado que tiene la anotacion en el getRESTServiceClass
			aux = buildDocForClass(clase);
			if (aux != null){
				serv.add(aux);
			}
		}
		return serv;
	}
	
	/** Method to build documentation for a given class. To do it it search for an annotations of a REST service 
	 * in given class and generates apropiate documentation
	 * @param clazz
	 * @return
	 */
	public DocService buildDocForClass(Class<?> clazz){
		DocService res = null;
		try {
			res = getServiceDocForClass(clazz);
		} catch (NotARESTServiceException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/** Scans a package to search classes which are REST services.
	 * @param <T>
	 * @param packag
	 * @return
	 */
	public <T extends Annotation> List<Class<?>> getAllRESTServicesFromPackage(String packageToScan){
		Reflections services = new Reflections(packageToScan);
		// obtenemos todos los servicios
		Set<Class<?>> annotated = services.getTypesAnnotatedWith(annotationToLocateInClasses);
		List<Class<?>> result = new ArrayList<Class<?>>();
		for (Class<?> clase : annotated){
			if (clase.getAnnotation(annotationToLocateInClasses) != null){
				result.add(clase);
			}
		}
		if (result.size() < 1){
			System.out.println("ERROR 404: No existe ningun servicio disponible");
			throw new NotFoundException();
		}
		return result;
	}
	
	/**
	 * @param clase
	 * @param classMethod
	 * @return
	 */
	public ArrayList<Method> getMethodsAnnotatedWith(Class<?> clase, Class<? extends Annotation> classMethod){
		Method[] methodsAux = clase.getMethods();
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Method method : methodsAux){
			if (method.getAnnotation(classMethod) != null){
				methods.add(method);
			}
		}
		return methods;
	}
}

