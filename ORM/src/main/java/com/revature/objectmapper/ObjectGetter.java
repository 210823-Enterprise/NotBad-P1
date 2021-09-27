package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revature.annotations.Entity;
import com.revature.orm.Configuration;
import com.revature.util.ColumnField;
import com.revature.util.MetaModel;

public class ObjectGetter extends ObjectMapper{
	
	private static final Logger LOG = Logger.getLogger(ObjectGetter.class);
	
	private static final String GETSQL = "SELECT * from %s where %s = ?;";
	private static final String GETALLSQL = "SELECT * from %s;";
	
	//wrapper for getting the primary key
	public <T> Optional<T> getObjectFromDb(final Class<T> clazz, final Object object, final Connection connection) {
		final MetaModel<T> model = Configuration.getInstance().getModel(clazz);
		return getObjectFromDb(clazz, object, model.getPrimaryKey().getColumnName(), connection);
	}
	
	public <T> Optional<T> getObjectFromDb(final Class<T> clazz, final Object object, final String columnName, final Connection connection) {
		
		//get model
		final MetaModel<T> model = Configuration.getInstance().getModel(clazz);
		
		//check cache
		final Optional<T> cache = ObjectCache.getInstance().get(model, columnName, object);
		if(cache.isPresent()) {
			LOG.info("Retrieved object " + object.getClass().getName() + " with " + columnName + " = " + object.toString() + " from cache.");
			return cache;
		}
		
		try {
			//generate prepared statement
			final String sql = String.format(GETSQL, model.getTableName(), columnName);
			
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ParameterMetaData parameter = statement.getParameterMetaData();
			
			//set the value of the prepared statement
			setPreparedStatementByType(statement, parameter.getParameterTypeName(1), object.toString(), 1);
			
			//run query
			final ResultSet result = statement.executeQuery();
			if( result.next() ) {
				//return result
				final Optional<T> out = constructObject(model, result, connection);
				LOG.info("Retrieved object " + object.getClass().getName() + " with " + columnName + " = " + object.toString() + " from database.");
				return out;
			}
		} catch(final IllegalStateException | IllegalArgumentException | SecurityException | SQLException e) {
			LOG.error("Failed to retrieved object " + object.toString() + ".");
			LOG.error(e.getLocalizedMessage());
		}
		return Optional.empty();
	}
	
	public <T> List<T> getAllObjectsFromDb(final Class<T> clazz, final Connection connection) {
		final List<T> list = new ArrayList<T>();
		try {	
			final MetaModel<T> model = Configuration.getInstance().getModel(clazz);
			final String sql 		 = String.format(GETALLSQL, model.getTableName());
			
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ResultSet result = statement.executeQuery();
			
			while( result.next() ) {
				final Optional<T> construct = constructObject(model, result, connection);
				if(construct.isPresent())
					list.add(construct.get());
			}
			LOG.info("Retrieved all object of type " + clazz.getName() + ".");
			return list;
		} catch(final IllegalStateException | SQLException e) {
			LOG.error("Failed to retrieved all object of type " + clazz.getName() + ".");
			LOG.error(e.getLocalizedMessage());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> constructObject(final MetaModel<T> model, final ResultSet result, final Connection connection) {
		//TODO: fix requiring a defined empty constructor
		try {
			final Object out = model.getType().newInstance();
			setValue( model.getPrimaryKey().getName(), out, result.getObject(model.getPrimaryKey().getColumnName()) );
			
			for(final ColumnField field: model.getColumns()) {
				final Object value = result.getObject(field.getColumnName());
				
				if(field.getType().getAnnotation(Entity.class) != null) {
					final Optional<?> object = getObjectFromDb(field.getType(), value, connection);
					setValue( field.getName(), out, object.get() );
				} else {
					setValue( field.getName(), out, value );
				}
			}
			
			return (Optional<T>) Optional.of(out);
			
		} catch (final InstantiationException e) {
			throw new RuntimeException("class " + model.getClassName() + " does not have a nullary constructor.");
		} catch (final Exception e) {
			LOG.error("Failed to construct class " + model.getClassName() + ".");
			LOG.error(e.getLocalizedMessage());
			return Optional.empty();
		}
	}
	
	public static ObjectGetter getInstance() {
		return new ObjectGetter();
	}
	
}
