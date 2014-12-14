package com.restdoc.docbuilders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

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
public class ServiceDocJAXRSConcreteBuilder extends ServiceDocBuilder {
	RESTService ser;
	Path path;
	Class<?> annotatedClass;
	DocService serviceDocument;
	Set<Class<?>> models;
	String[] dtopaths;
	
	public ServiceDocJAXRSConcreteBuilder(){
		super(Path.class, RESTService.class);
	}
	
	@Override
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
	public void setupForClass(Class<?> annotatedClass) throws NotARESTServiceException {
		try {
			this.annotatedClass = annotatedClass;
			this.serviceDocument = new DocService();
			this.ser = (RESTService) annotatedClass.getAnnotation(annotationToLocateInClasses);
			this.path = (Path) annotatedClass.getAnnotation(annotationPath);
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
		serviceDocument.setPath(path.value());
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
		insertMethodsAnnotatedDoc(result, GET.class, annotatedClass);
		insertMethodsAnnotatedDoc(result, PUT.class, annotatedClass);
		insertMethodsAnnotatedDoc(result, POST.class, annotatedClass);
		insertMethodsAnnotatedDoc(result, DELETE.class, annotatedClass);
		return result;
	}
	
	/**Obtiene la informacion de los metodos marcados como classMethod de clase y la guarda en el buffer
	 * @param buffer
	 * @param classMethod
	 * @param clase
	 * @return
	 */
	private ArrayList<DocSerMethod> insertMethodsAnnotatedDoc(ArrayList<DocSerMethod> buffer,Class<? extends Annotation> annotation, Class<?> clase){
		ArrayList<Method> methods = Utils.getMethodsAnnotatedWith(clase, annotation);
		DocSerMethod methodDocument = null;
		for (Method method : methods) {
			methodDocument = buildMethodDoc(method, annotation);
			if (methodDocument != null){
				buffer.add(methodDocument);
			}
        }
		return buffer;
	}
	
	private DocSerMethod buildMethodDoc(Method method, Class<? extends Annotation> annotation){
		//TODO: Esto obtiene el path... debiera estar ya
    	String strpath = "/";
    	if ((path = method.getAnnotation(Path.class)) != null){
        	strpath = path.value();
    	}
    	RESTMethod mets = method.getAnnotation(RESTMethod.class);
    	DocSerMethod methodDocument = null;
    	if (mets != null){
    		methodDocument = new DocSerMethod();
    		methodDocument.setMethod(annotation.getSimpleName());
    		methodDocument.setDescription(mets.description());
    		methodDocument.setPath(strpath);
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