package com.restdoc.docbuilders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;
import com.documentation.model.DocSerMethod;
import com.documentation.model.DocService;
import com.restdoc.annotations.RESTMethod;
import com.restdoc.annotations.RESTService;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.docbuilders.abstracts.ServiceDocBuilder;
import com.restdoc.docbuilders.classdocbuilders.DTODocGenerator;

/** Genera los datos de un servicio
 * @author inigo
 *
 */
public class ServiceDocRESTDOCConcreteBuilder extends ServiceDocBuilder {
	RESTService ser;
	String path;
	Class<?> annotatedClass;
	DocService serviceDocument;
	Set<Class<?>> models;
	String[] dtopaths;
	
	public ServiceDocRESTDOCConcreteBuilder(){
		super(RESTService.class);
	}
	
	@Override
	public DocService buildDocForClass(Class<?> clazz){
		DocService res = null;
		try {
			setupForClass(clazz);
			res = getServiceDocumentation();
		} catch (NotARESTServiceException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public DocService getServiceDocForClass(Class<?> annotatedClass)
			throws NotARESTServiceException {
		setupForClass(annotatedClass);
		return getServiceDocumentation();
	}
	
	public void setupForClass(Class<?> annotatedClass) throws NotARESTServiceException {
		try {
			this.annotatedClass = annotatedClass;
			this.serviceDocument = new DocService();
			this.ser = (RESTService) annotatedClass.getAnnotation(annotationToLocateInClasses);
			this.path = ser.path();
			this.dtoDocGenerator = new DTODocGenerator(AbstractContextReader.getContextReader().readAvailableDTODocGenerators());
			this.dtoDocGenerator.setupForService(annotatedClass);
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new NotARESTServiceException("La clase "+ annotatedClass.getName()+ " no se ha podido leer como @RESTService", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.documentation.annotations.utils.ServiceDocGenerator#getServiceDocumentation()
	 */
	public DocService getServiceDocumentation(){
		serviceDocument.setPath(path);
		serviceDocument.setDescription(ser.description());
		serviceDocument.setMethods(getMethodsData());
		serviceDocument.setModelpath(ser.modelpath());
		return serviceDocument;
	}
	
	/** Obtiene la informacion de los metodos marcados como @GET, @POTS, @PUT y @DELETE del servicio
	 * @return
	 */
	private ArrayList<DocSerMethod> getMethodsData() {
		ArrayList<DocSerMethod> result = new ArrayList<DocSerMethod>();
		ArrayList<Method> methods = getMethodsAnnotatedWith(annotatedClass, RESTMethod.class);
		insertMethodsAnnotatedDoc(result, methods);
		return result;
	}
	
	/**Obtiene la informacion de los metodos marcados como classMethod de clase y la guarda en el buffer
	 * @param buffer
	 * @param classMethod
	 * @param clase
	 * @return
	 */
	private ArrayList<DocSerMethod> insertMethodsAnnotatedDoc(ArrayList<DocSerMethod> buffer,ArrayList<Method> methods){
		DocSerMethod methodDocument = null;
		for (Method method : methods) {
			methodDocument = buildMethodDoc(method);
			if (methodDocument != null){
				buffer.add(methodDocument);
			}
        }
		return buffer;
	}
	
	private DocSerMethod buildMethodDoc(Method method){
		RESTMethod mets = method.getAnnotation(RESTMethod.class);
    	DocSerMethod methodDocument = null;
    	if (mets != null){
    		methodDocument = new DocSerMethod();
    		methodDocument.setMethod(mets.httpMethod());
    		methodDocument.setDescription(mets.description());
    		methodDocument.setPath(mets.path());
    		methodDocument.setModelpath(mets.modelpath());
    		methodDocument.setProducedMimetype(mets.productedMimetype());
    		methodDocument.setProducedObject(getClassDoc(mets.producedObject()));
    		methodDocument.setConsumedMimetype(mets.consumedMimetype());
    		methodDocument.setConsumedObject(getClassDoc(mets.consumedObject()));
    		}
    	return methodDocument;
	}
	
	public DocClass getClassDoc(String classname){
		return dtoDocGenerator.buildDoc(classname);
	}

	public String[] getDtopaths() {
		return dtopaths;
	}

	public void setDtopaths(String[] dtopaths) {
		this.dtopaths = dtopaths;
	}
}