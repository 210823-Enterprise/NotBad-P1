package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.revature.annotations.Entity;
import com.revature.orm.Configuration;
import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectRemover extends ObjectMapper {
	
	private static final String SQL = "DELETE from %s where %s = ?;";
	
	public boolean removeObjectFromDb(final Object object, final Connection connection) {
		
		try {
			
			final MetaModel<?> model = Configuration.getInstance().getModel(object.getClass());
			final IdField primaryKey = model.getPrimaryKey();
			final String sql 		 = String.format(SQL, model.getTableName(), primaryKey.getColumnName());
			final List<ColumnField> fields = model.getColumns();
			
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ParameterMetaData parameter = statement.getParameterMetaData();
			
			final Object primaryValue = getValue(primaryKey.getName(), object);
			setStatement(statement, parameter, primaryValue, 1);

			statement.executeUpdate();
			
			for(int i = 0; i < fields.size(); i++) {
				final Object value = getValue(fields.get(i).getName(), object);
				if(value.getClass().getAnnotation(Entity.class) != null) {
					removeObjectFromDb(value, connection);
				}
			}
			
			ObjectCache.getInstance().remove(object);
			return true;
		} catch(final IllegalStateException | SQLException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static ObjectRemover getInstance() {
		return new ObjectRemover();
	}
	
}
