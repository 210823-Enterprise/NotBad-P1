package com.revature.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Configuration {
	
	private List<MetaModel<Class<?>>> metaModelList;  // List is a list of MetaModel<Dog>, MetaModel<Cat>
	
	// this essentially does what the Hibernate.cfg.xml mapping property does!
	@SuppressWarnings("unchecked")
	public Configuration addAnnotatedClass(final Class<?> annotatedClass) {
		
		if (this.metaModelList == null) {
			this.metaModelList = new LinkedList<>();
		}
		// we are adding MetaModel<SomeClass>
		this.metaModelList.add( (MetaModel<Class<?>>) MetaModel.of(annotatedClass) );
		return this;
	}
	
	public List<MetaModel<Class<?>>> getMetaModels() {
		return ((this.metaModelList == null) ? Collections.emptyList() : this.metaModelList);
	}
	
}
