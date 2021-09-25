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
	private boolean finalized;
	
	private Configuration() {
		this.metaModelList = new LinkedList<>();
		this.finalized = false;
	}
	
	/**
	 * Registers a class to be added to the ORM and database. Must be properly annotated with the
	 * class marked as @Entity, and at least 1 variable marked with @Id, and another with @Column.
	 * 
	 * Throws 'IllegalStateException' if the configuration has been finalized, or the provided clas
	 * it not properly annotated.
	 */
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
	
	/**
	 * Gets the model of the specified class.
	 * 
	 * Throws 'ClassNotConfiguredException' if the class has not been added to the configuration.
	 */
	@SuppressWarnings("unchecked")
	public <T> MetaModel<T> getModel(final Class<T> clazz) {
		
		for(final MetaModel<?> model: this.metaModelList)
			if(model.getType() == clazz)
				return (MetaModel<T>) model;
		
		throw new ClassNotConfiguredException();
	}
	
	/**
	 * @return A list of all registed classes.
	 */
	public List<MetaModel<Class<?>>> getMetaModels() {
		return ((this.metaModelList == null) ? Collections.emptyList() : this.metaModelList);
	}
	
	/**
	 * Marks a configuration as complete. Creates tables in the database for all registered class (if needed),
	 * and prevents adding additional classes to the configuration.
	 */
	public void finalizeConfig() {
		this.finalized = true;
		this.metaModelList.forEach(m -> ORM.getInstance().generateTable(m));
	}
	
	/**
	 * @return Static instance of the configuration.
	 */
	public static Configuration getInstance() {
		return config;
	}
	
}
