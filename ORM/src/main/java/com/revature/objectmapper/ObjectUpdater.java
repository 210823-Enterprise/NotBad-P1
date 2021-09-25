package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.annotations.Entity;
import com.revature.orm.Configuration;
import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectUpdater extends ObjectMapper {
	
	private static final Logger LOG = Logger.getLogger(ObjectUpdater.class);
	
	private static final String SQL = "UPDATE %s SET %s WHERE %s = ?;";
	
	public boolean updateObjectInDb(final Object object, final Connection connection) {
		
		try {
			
			final MetaModel<?> model = Configuration.getInstance().getModel(object.getClass());
			final IdField primaryKey = model.getPrimaryKey();
			final List<ColumnField> fields = model.getColumns();

			//generate list of entry names
			String entries = "";
			for(final ColumnField field: fields)
				if(!field.getColumnName().equals(primaryKey.getColumnName()))
					entries += field.getColumnName() + " = ?, ";
			if(entries.length() > 2)
				entries = entries.substring(0,entries.length()-2);
			
			//generate prepared statement
			final String sql 		 = String.format(SQL, model.getTableName(), entries, primaryKey.getColumnName());
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ParameterMetaData parameter = statement.getParameterMetaData();
			
			//set each parameter
			for(int i = 0; i < fields.size(); i++) {
				
				final Object value = getValue(fields.get(i).getName(), object);
				
				if(value.getClass().getAnnotation(Entity.class) != null) {
					updateObjectInDb(value, connection);
					final Object foreignValue = getValue(model.getPrimaryKey().getName(), value);
					setStatement(statement, parameter, foreignValue, i+1);
				} else {
					setStatement(statement, parameter, value, i+1);
				}
			}
			final Object primaryValue = getValue(primaryKey.getName(), object);
			setStatement(statement, parameter, primaryValue, fields.size()+1);
			
			statement.executeUpdate();
			LOG.info("Updated " + object.toString() + ".");
		} catch(final IllegalStateException | SQLException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			LOG.error("Failed to updated " + object.toString() + ".");
			LOG.error(e.getLocalizedMessage());
			return false;
		}
		return true;
	}
	
	public static ObjectUpdater getInstance() {
		return new ObjectUpdater();
	}
	
}
