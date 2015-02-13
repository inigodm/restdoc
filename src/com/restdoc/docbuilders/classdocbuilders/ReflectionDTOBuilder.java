package com.restdoc.docbuilders.classdocbuilders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.reflections.Reflections;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;
import com.documentation.model.DocField;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;

public class ReflectionDTOBuilder extends DTODocBuilder {
	AbstractContextReader ctxReader;
	
	public ReflectionDTOBuilder() {
		ctxReader = AbstractContextReader.getContextReader();
		models = new HashSet<Class<?>>();
	}

	@Override
	protected DocClass fillClassDoc(Class<?> objectClass) {
		DocClass doc = new DocClass();
		doc.setName(objectClass.getName());
		doc.setFields(buildFieldsInfo(objectClass.getMethods()));
		return doc;
	}

	private List<DocField> buildFieldsInfo(Method[] classMethods) {
		List<DocField> df = new ArrayList<DocField>();
		for (Method method : classMethods){
			if (isGet(method)){
				df.add(getFieldInfo(method));
			}
		}
		return df;
	}
	
	private boolean isGet(Method method){
		return method.getName().startsWith("get") || method.getName().startsWith("is");
	}

	private DocField getFieldInfo(Method method) {
		DocField df = new DocField();
		df.setName(method.getName());
		df.setType(method.getReturnType().getName());
		df.setTypeDesc(buildFieldTypeDoc(method.getReturnType().getName()));
		//TODO:sEGUIR AQUI
		return df;
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
	
	@Override
	protected void setupForService(Class<?> annotatedClass)
			throws NotARESTServiceException {
		AbstractContextReader cr = AbstractContextReader.getContextReader();
		String[] modelPaths = cr.readPathsToModels();
		for (String path : modelPaths){
			System.out.println("Se van a buscar los modelos de: " + path);
			addModelsFromPath(path);
		}
	}

	private void addModelsFromPath(String path) {
		Reflections reflections = new Reflections(path);
		models.addAll(reflections.getSubTypesOf(Object.class));
	}
}
