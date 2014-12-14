package com.restdoc.docbuilders.classdocbuilders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;
import com.documentation.model.DocClasses;
import com.documentation.model.DocField;
import com.restdoc.annotations.ModelClass;
import com.restdoc.annotations.ModelMethod;
import com.restdoc.annotations.RESTService;

public class RESTDocDTOBuilder implements DTODocBuilder{
	Set<Class<?>> models;
	
	@Override
	public void setupForService(Class<?> annotatedClass) throws NotARESTServiceException{
		RESTService ser = (RESTService) annotatedClass.getAnnotation(RESTService.class);
		Reflections model = getModelFromServiceAnnotation(ser);
		this.models = model.getTypesAnnotatedWith(ModelClass.class);
	}
	
	@Override
	public DocClass buildDoc(String className) {
		boolean isAnArray = false;
		String classNameClean;
		if (className.endsWith("[]")){
			isAnArray = true;
			classNameClean = className.substring(0, className.length() - 2);
		}else{
			classNameClean = className;
		}
		for (Class<?> annClass : models){
			if (annClass.getSimpleName().equals(classNameClean)){
				if (isAnArray){
					return fillArrayDoc(annClass);
				}
				return fillClassDoc(annClass);
			}
		}
		return fillPrimitiveOrJavaObject(classNameClean);
	}
	
	private DocClasses fillArrayDoc(Class<?> annClass){
		DocClasses container = new DocClasses();
		ArrayList<DocClass> aux = new ArrayList<DocClass>();
		aux.add(fillClassDoc(annClass));
		container.setObjects(aux);
		return container;
	}
	
	/** Rellena la informacion del objeto con la que se encuentra en las anotaciones de la clase que se le pasa como parametro
	 * @param objectClass
	 * @return un objeto con toda la informacion recopilada
	 */
	private DocClass fillClassDoc(Class<?> objectClass){
		ModelClass ann = objectClass.getAnnotation(ModelClass.class);
		DocClass objectDocument = new DocClass();
		objectDocument.setName(ann.name());
		objectDocument.setDescription(ann.description());
		objectDocument.setVersion(ann.version());
		objectDocument.setFields(getFieldsData(objectClass));
		return objectDocument;		
	}
	
	private DocClass fillPrimitiveOrJavaObject(String clase){
		DocClass obj = new DocClass();
		obj.setName(clase);
		return obj;
	}
	/** Obtiene la informacion de los campos del objeto que se envia.
	 * @param annClass
	 * @return
	 */
	private ArrayList<DocField> getFieldsData(Class<?> annClass) {
		ArrayList<DocField> result = new ArrayList<DocField>();
		ModelMethod meth;
		for (Method methd : annClass.getMethods()){
			meth = methd.getAnnotation(ModelMethod.class);
			if (meth != null){
				result.add(buildFieldDoc(meth, methd));
			}
		}
		return result;
	}
	
	/** 
	 * @param methdAnnotation
	 * @param methd
	 * @return
	 */
	private DocField buildFieldDoc(ModelMethod methdAnnotation, Method methd){
		DocField fieldDocument = null;
		String typename = "";
		typename = methdAnnotation.type();
		if (typename == null || typename == ""){
			typename = methd.getReturnType().getName();
		}
		fieldDocument = new DocField();
		fieldDocument.setName(methdAnnotation.name());
		fieldDocument.setType(typename);
		fieldDocument.setDescription(methdAnnotation.description());
		fieldDocument.setRequired(methdAnnotation.required());
		fieldDocument.setSize(methdAnnotation.size());
		fieldDocument.setTypeDesc(buildFieldTypeDoc(methdAnnotation.type()));
		return fieldDocument;
	}
	
	private DocClass buildFieldTypeDoc(String type){
		DocClass typo = buildDoc(type);
		if (typo == null){
			typo = new DocClass();
			typo.setName(type);
		}
		if (typo.equals("void")){
			typo.setName(type);
		}
		return typo;
	}
	
	/** Devuelve el reflections para el paquete que esta en el @RESTService como 'modelpath' o null si no existe el parametro
	 * Si no existe se tendra que obtener el paquete de cada metodo
	 * @param rser
	 * @return
	 * @throws NotARESTServiceException 
	 */
	private Reflections getModelFromServiceAnnotation(final RESTService rser) throws NotARESTServiceException{
		if (rser == null){
			throw new NotARESTServiceException("No hay @RESTService ");
		}
		Reflections models = null;
		String modelpath = rser.modelpath();
		if (!modelpath.equals("")){
			models = new Reflections(modelpath);
		}
		return models;
	}
}
