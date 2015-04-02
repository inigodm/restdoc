package com.restdoc.docbuilders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;
import com.documentation.model.DocField;
import com.documentation.model.DocSerMethod;
import com.documentation.model.DocSerParam;
import com.documentation.model.DocService;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;
import com.restdoc.docbuilders.abstracts.ServiceDocBuilder;
import com.restdoc.docbuilders.classdocbuilders.DTODocDirector;
import com.restdoc.docbuilders.classdocbuilders.ReflectionDTOBuilder;

/** Genera los datos de un servicio a partir de las etiquetas JAX-RS
 * @author inigo
 *
 */
public class ServiceDocJAXRSConcreteBuilder extends ServiceDocBuilder {
	Path path;
	Class<?> annotatedClass;
	DocService serviceDocument;
	Set<Class<?>> models;
	String[] dtopaths;
	
	public ServiceDocJAXRSConcreteBuilder(){
		super(Path.class);
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
			this.path = (Path) annotatedClass.getAnnotation(Path.class);
			this.dtoDocDirector = new DTODocDirector(AbstractContextReader.getContextReader().readAvailableDTODocGenerators());
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
		//serviceDocument.setDescription(ser.description());
		serviceDocument.setMethods(getMethodsData());
		//serviceDocument.setModelpath(ser.modelpath());
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
		ArrayList<Method> methods = getMethodsAnnotatedWith(clase, annotation);
		DocSerMethod methodDocument = null;
		for (Method method : methods) {
			methodDocument = buildMethodDoc(method, annotation);
			if (methodDocument != null){
				buffer.add(methodDocument);
			}
        }
		return buffer;
	}
	
	private DocSerMethod buildMethodDoc(Method method, Class<? extends Annotation> methodAnnotation){
		DocSerMethod methodDocument = new DocSerMethod();
		AnnotationsManager annManager = new AnnotationsManager(method, methodDocument);
    	ReflectionInfoBuilder refManager = new ReflectionInfoBuilder(methodDocument);
    	annManager.putAnnotationsInfo();
    	refManager.extractInfoFromReturnedType(method);
    	//annManager.putParamAnnotationsInfo();
    	return methodDocument;
	}
	
	public DocClass getClassDoc(String classname){
		return dtoDocDirector.buildDoc(classname);
	}

	public String[] getDtopaths() {
		return dtopaths;
	}

	public void setDtopaths(String[] dtopaths) {
		this.dtopaths = dtopaths;
	}

}


class ReflectionInfoBuilder{
	
	ReflectionDTOBuilder rdtob;
	DocSerMethod methodDocument;
	
	public ReflectionInfoBuilder(DocSerMethod methodDocument){
		rdtob = new ReflectionDTOBuilder();
		this.methodDocument = methodDocument;
	}
	
	public void extractInfoFromReturnedType(Method method){
		Class<?> returnedClass = method.getReturnType();
		methodDocument.setProducedObject(rdtob.buildDoc(returnedClass.getName()));
	}
}

class AnnotationsManager{
	Method method;
	DocSerMethod methodDocument = new DocSerMethod();
	HashMap<Class<?>, AnnotationInfoExtractor> methodAnnotationExtractors = new HashMap<Class<?>, AnnotationInfoExtractor>();
	HashMap<Class<?>, AnnotationInfoExtractor> methodParamAnnotationExtractors = new HashMap<Class<?>, AnnotationInfoExtractor>();
	public AnnotationsManager(Method method, DocSerMethod methodDocument){
		this.method = method;
		this.methodDocument = methodDocument;
		methodAnnotationExtractors.put(Path.class, new PathExtractor(methodDocument));
		methodAnnotationExtractors.put(Produces.class, new ProducesExtractor(methodDocument));
		methodAnnotationExtractors.put(Consumes.class, new ConsumesExtractor(methodDocument));
		methodAnnotationExtractors.put(GET.class, new GetExtractor(methodDocument));
		methodAnnotationExtractors.put(PUT.class, new PutExtractor(methodDocument));
		methodAnnotationExtractors.put(POST.class, new PostExtractor(methodDocument));
		methodAnnotationExtractors.put(DELETE.class, new DeleteExtractor(methodDocument));
		methodParamAnnotationExtractors.put(PathParam.class, new PathParamExtractor(methodDocument));
		//methodParamAnnotationExtractors.put(QueryParam.class, new QueryParamExtractor(methodDocument));
		//methodParamAnnotationExtractors.put(FormParam.class, new FormParamExtractor(methodDocument));
	}

	public void putParamInfo() {
		DocField field = new DocField();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		System.out.println("paramanns: " + parameterAnnotations.length);
		System.out.println("paramanns: " + method.getName());
		Class<?>[] classes = method.getParameterTypes();
		for (int i = 0; i < classes.length; i++){
			System.out.println(classes[i] + " param annotation " + parameterAnnotations[i]);
			putParamInfo(classes[i], parameterAnnotations[i]);
		}
	}

	private void putParamInfo(Class<?> paramType, Annotation[] annotations) {
		if (annotations.length == 0){
			return;
		}
		DocSerParam param = new DocSerParam()
		for (Annotation ann : annotations){
			putParamAnnotationInfo(ann, );
		//	param.setRestParamType();
		}
		//param.setParamType(paramType);
		//param.setName(name);
		
		//insertParamInfo
	}

	private void putParamAnnotationInfo(Annotation ann, Class<?> paramType) {
		
	}

	/** Extracts info from method's JAX-RS annotations 
	 */
	public void putAnnotationsInfo() {
		for (Annotation ann : method.getAnnotations()){
    		putAnnotationInfo(ann);
    	}
	}
	/** Extracts info from method's annotation if it's a JAX-RS annotation
	 * @param annotation
	 */
	private void putAnnotationInfo(Annotation annotation){
		if (methodAnnotationExtractors.containsKey(annotation.annotationType())){
			methodAnnotationExtractors.get(annotation.annotationType()).extractInfo(annotation);
		} else {
			System.out.println("No data to extract from " + annotation.annotationType());	
		}
	}
	
	public DocSerMethod getMethodDocument(){
		return methodDocument;
	}
}

abstract class AnnotationInfoExtractor{
	DocSerMethod methodDocument;
	public AnnotationInfoExtractor(DocSerMethod methodDocument){
		this.methodDocument = methodDocument;
	}
	
	public void setDocument(DocSerMethod methodDocument){
		this.methodDocument = methodDocument;
	}
	
	public void extractInfo(Annotation annotation){}
	public void extractInfo(Annotation annotation, Class<?> clazz){}
}

class PathExtractor extends AnnotationInfoExtractor{
	public PathExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}

	@Override
	public void extractInfo(Annotation annotation) {
		String value = ((Path)annotation).value();
		methodDocument.setPath(value);
	}
}

class GetExtractor extends AnnotationInfoExtractor{
	public GetExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}

	@Override
	public void extractInfo(Annotation annotation) {
		methodDocument.setMethod("GET");
	}
}

class PutExtractor extends AnnotationInfoExtractor{
	public PutExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}

	@Override
	public void extractInfo(Annotation annotation) {
		methodDocument.setMethod("PUT");
	}
}

class PostExtractor extends AnnotationInfoExtractor{
	public PostExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}

	@Override
	public void extractInfo(Annotation annotation) {
		methodDocument.setMethod("POST");
	}
}

class DeleteExtractor extends AnnotationInfoExtractor{
	public DeleteExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}

	@Override
	public void extractInfo(Annotation annotation) {
		methodDocument.setMethod("DELETE");
	}
}

class ProducesExtractor extends AnnotationInfoExtractor{
	Method method;
	public ProducesExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}

	@Override
	public void extractInfo(Annotation annotation) {
		String[] values = ((Produces)annotation).value();
		StringBuffer sb = new StringBuffer();
		for (String value : values){
			sb.append(value).append(" ");
		}
		methodDocument.setProducedMimetype(sb.toString());
	}
}

class ConsumesExtractor extends AnnotationInfoExtractor{
	public ConsumesExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}

	@Override
	public void extractInfo(Annotation annotation) {
		String[] values = ((Consumes)annotation).value();
		StringBuffer sb = new StringBuffer();
		for (String value : values){
			sb.append(value).append(" ");
		}
		methodDocument.setConsumedMimetype(sb.toString());
	}
}

class PathParamExtractor extends AnnotationInfoExtractor{

	public PathParamExtractor(DocSerMethod methodDocument) {
		super(methodDocument);
	}
	
	@Override
	public void extractInfo(Annotation annotation, Class<?> clazz){
		methodDocument.
	}
	
}