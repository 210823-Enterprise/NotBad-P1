package com.revature.orm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exceptions.ClassNotConfiguredException;
import com.revature.util.MetaModel;

public class Configuration {
	
	private static final Logger LOG = Logger.getLogger(Configuration.class);
	
	private static final Configuration config = new Configuration();
	private final List<MetaModel<Class<?>>> metaModelList;
	private boolean finalized = false;
	
	private Configuration() {
		this.metaModelList = new LinkedList<>();
	}
	
	@SuppressWarnings("unchecked")
	public void addAnnotatedClass(final Class<?> annotatedClass) {
		if(this.finalized)
			throw new IllegalStateException("Configuation has already been finalized.");
		
		final MetaModel<Class<?>> model = (MetaModel<Class<?>>) MetaModel.of(annotatedClass);
		if(this.metaModelList.contains(model))
			throw new IllegalStateException(annotatedClass.getName() + " is already registered.");
		this.metaModelList.add( model );
		LOG.info("Added " + annotatedClass.getName() + " to meta models.");
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
	
	public void finalizeConfig() {
		this.finalized = true;
		this.metaModelList.forEach(m -> ORM.getInstance().generateTable(m));
	}
	
	public static Configuration getInstance() {
		return config;
	}
	
}
