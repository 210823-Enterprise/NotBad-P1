package com.revature.objectmapper;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class handles caching objects stored in database tables.
 * Singleton so that there's only one instance.
 *
 */
public class ObjectCache {
	
	private static final  ObjectCache OBJ_CACHE = new ObjectCache();
	
	private final HashMap<Class<?>, HashSet<Object>> cache;

	private ObjectCache() {
		super();
		this.cache = new HashMap<>();
	}
	
	public static ObjectCache getInstance() {
		return OBJ_CACHE;
	}
	
	
	// method: putObj in cache()
	
	// remove()

	
	
	
}
