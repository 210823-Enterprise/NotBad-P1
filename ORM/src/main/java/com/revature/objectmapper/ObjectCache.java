package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.revature.orm.Configuration;
import com.revature.util.MetaModel;

/**
 * This class handles caching objects stored in database tables.
 * Singleton so that there's only one instance.
 *
 */
public class ObjectCache {
	
	private static final ObjectCache OBJECT_CACHE = new ObjectCache();
	
	private final HashMap<Class<?>, HashMap<Object,Object>> cache;

	private ObjectCache() {
		super();
		this.cache = new HashMap<>();
	}
	
	public static ObjectCache getInstance() {
		return OBJECT_CACHE;
	}
	
	@SuppressWarnings("unchecked")
	public <T> Optional<T> get(final MetaModel<T> model, final String columnName, final Object primaryKey) {
		if(this.cache.containsKey(model.getType())) {
			final Map<Object, Object> subSet = this.cache.get(model.getType());
			
			if(model.getPrimaryKey().getColumnName().equals(columnName)) {
				if(subSet.containsKey(primaryKey))
					return (Optional<T>) Optional.of(subSet.get(primaryKey));
			} else {
				for(final Entry<Object, Object> set: subSet.entrySet()) {
					if(set.getValue().getClass() == primaryKey.getClass() && set.getValue().equals(primaryKey))
						return (Optional<T>) Optional.of(set.getValue());
				}
			}
		}
		return Optional.empty();
	}
	
	public void put(final Object object) {
		if(object != null) {
			final MetaModel<?> model = Configuration.getInstance().getModel(object.getClass());
			if(!this.cache.containsKey(object.getClass()))
				this.cache.put(object.getClass(), new HashMap<Object,Object>());
			final Map<Object, Object> subSet = this.cache.get(object.getClass());
			try {
				subSet.put( getValue(model.getPrimaryKey().getName(), object), object);
			} catch (final IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void remove(final Object object) {
		if(object != null) {
			final MetaModel<?> model = Configuration.getInstance().getModel(object.getClass());
			if(this.cache.containsKey(object.getClass())) {
				final Map<Object, Object> subSet = this.cache.get(object.getClass());
				try {
					final Object value = getValue(model.getPrimaryKey().getName(), object);
					if(subSet.containsKey(value))
						subSet.remove(value);
				} catch (final IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//TODO: try to not use a dirty hack
	protected Object getValue(final String fieldName, final Object source) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final Field field = source.getClass().getDeclaredField(fieldName);
		if(field.isAccessible()) {
			return field.get(source);
		} else {
			field.setAccessible(true);
			final Object value = field.get(source);
			field.setAccessible(false);
			return value;
		}
	}

}
