package com.restdoc.docbuilders.abstracts;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocService;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.docbuilders.classdocbuilders.DTODocGenerator;
import com.sun.jersey.api.NotFoundException;

public abstract class ServiceDocBuilder {
	protected DTODocGenerator dtoDocGenerator;
	/**
	 * Used to determine which annotation will mark a class like a REST service that this 
	 * ServiceDocBuilder is able to scan for info
	 */
	protected Class<? extends Annotation> annotationToLocateInClasses;
	/**
	 * Path of the service.
	 */
	@Deprecated
	protected Class<? extends Annotation> annotationPath;
	
	/** Builder to generate documentation from a REST service
	 * Not use: Depends too much from JAX-RS.
	 * @param pathAnn
	 * @param restServiceAnn
	 */
	@Deprecated
	protected ServiceDocBuilder(Class<? extends Annotation> pathAnn, Class<? extends Annotation> restServiceAnn){
		dtoDocGenerator = new DTODocGenerator(AbstractContextReader.getContextReader().readAvailableDTODocGenerators());
		annotationPath = pathAnn;
		annotationToLocateInClasses = restServiceAnn;
	}
	/** Builder to generate documentation from a REST service
	 * @param pathAnn
	 * @param restServiceAnn
	 */
	protected ServiceDocBuilder(Class<? extends Annotation> restServiceAnn){
		dtoDocGenerator = new DTODocGenerator(AbstractContextReader.getContextReader().readAvailableDTODocGenerators());
		annotationToLocateInClasses = restServiceAnn;
	}
	/** Obtiene la informacion del servicio que con el que se ha inicializado
	 * @return
	 */
	public abstract void setupForClass(Class<?> annotatedClass) throws NotARESTServiceException;
	
	/** Method to build documentation for a given package.It search for REST service annotations in that package
	 * and returns generated doc.
	 * 
	 * @param packag
	 * @return
	 */
	public abstract Collection<? extends DocService> buildDocForPackage(String packag);
	
	/** Method to build documentation for a given class. To do it it search for an annotations of a REST service 
	 * in given class and generates apropiate documentation
	 * @param clazz
	 * @return
	 */
	public abstract DocService buildDocForClass(Class<?> clazz) ;
	
	/** Scans a package to search classes which are REST services.
	 * @param <T>
	 * @param packag
	 * @return
	 */
	public <T extends Annotation> List<Class<?>> getAllRESTServicesFromPackage(String packageToScan){
		Reflections services = new Reflections(packageToScan);
		// obtenemos todos los servicios
		Set<Class<?>> annotated = services.getTypesAnnotatedWith(annotationToLocateInClasses);
		List<Class<?>> result = new ArrayList<>();
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
}

