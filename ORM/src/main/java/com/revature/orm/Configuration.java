package com.revature.orm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.revature.exceptions.ClassNotConfiguredException;
import com.revature.util.MetaModel;

public class Configuration {
	
	private static final Configuration config = new Configuration();
	private final List<MetaModel<Class<?>>> metaModelList;
	
	private Configuration() {
		this.metaModelList = new LinkedList<>();
	}
	
	@SuppressWarnings("unchecked")
	public void addAnnotatedClass(final Class<?> annotatedClass) {
		final MetaModel<Class<?>> model = (MetaModel<Class<?>>) MetaModel.of(annotatedClass);
		if(this.metaModelList.contains(model))
			throw new IllegalStateException(annotatedClass.getName() + " is already registered.");
		this.metaModelList.add( model );
		ORM.getInstance().generateTable(model);
	}
	
	@SuppressWarnings("unchecked")
	public <T> MetaModel<T> getModel(final Class<T> clazz) {
		for(final MetaModel<?> model: this.metaModelList)
			if(model.getClazz() == clazz)
				return (MetaModel<T>) model;
		throw new ClassNotConfiguredException();
	}
	
	public List<MetaModel<Class<?>>> getMetaModels() {
		return ((this.metaModelList == null) ? Collections.emptyList() : this.metaModelList);
	}
	
	public static Configuration getInstance() {
		return config;
	}
	
}
