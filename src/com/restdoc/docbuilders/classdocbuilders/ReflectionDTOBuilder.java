package com.restdoc.docbuilders.classdocbuilders;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.documentation.annotations.exceptions.NotARESTServiceException;
import com.documentation.model.DocClass;
import com.documentation.model.DocField;
import com.restdoc.contextreaders.abstracts.AbstractContextReader;

public class ReflectionDTOBuilder extends DTODocBuilder {
	
	public ReflectionDTOBuilder() {
		super();
	}

	@Override
	protected DocClass fillClassDoc(Class<?> objectClass) {
		DocClass doc = new DocClass();
		doc.setName(objectClass.getName());
		doc.setFields(buildFieldsInfo(objectClass.getDeclaredFields()));
		return doc;
	}
	
	private List<DocField> buildFieldsInfo(Field[] classFields) {
		List<DocField> df = new ArrayList<DocField>();
		for (Field field : classFields){
			df.add(getFieldInfo(field));
		}
		return df;
	}
	
	private DocField getFieldInfo(Field field) {
		DocField df = new DocField();
		df.setName(field.getName());
		df.setType(field.getType().getName());
		df.setTypeDesc(buildFieldTypeDoc(field.getType().getName()));
		try{
			putGenericsInfo(field, df);
		}catch (ClassCastException e){
			System.out.println("No generics here");
		}
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
	
	private void putGenericsInfo(Field field, DocField df){
		ParameterizedType  pt = ((ParameterizedType)field.getGenericType());
		if (pt.getActualTypeArguments().length > 0){
				ArrayList<DocClass> gens = new ArrayList<DocClass>();
				gens.add(buildDoc(((Class<?>)pt.getActualTypeArguments()[0]).getName()));
				df.setGenerics(gens);
		}
	}
	
	/*@Override
	protected void setupForService(Class<?> annotatedClass)
			throws NotARESTServiceException {
		ctxReader = AbstractContextReader.getContextReader();
		modelPaths = ctxReader.readPathsToModels();
	}*/
}
