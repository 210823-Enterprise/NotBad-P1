package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectGetter extends ObjectMapper{
	
	private static final String SQL = "SELECT * from %s where %s = ?;";
	
	public Object getObjectFromDb(final Class<?> clazz, final Object object, final Connection connection) {
		
		try {
			final MetaModel<?> model = MetaModel.of(clazz);
			final IdField primaryKey = model.getPrimaryKey();
			final String sql 		 = String.format(SQL, model.getTableName(), primaryKey.getColumnName());
			
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ParameterMetaData parameter = statement.getParameterMetaData();
			
			setPreparedStatementByType(statement, parameter.getParameterTypeName(1), object.toString(), 1);

			final ResultSet result = statement.executeQuery();
			if( result != null) {
				result.next();
				
				//TODO: fix requiring a defined empty constructor
				final Object out = clazz.newInstance();
				setValue( primaryKey.getName(), out, result.getObject(primaryKey.getColumnName()) );
				for(final ColumnField field: model.getColumns())
					setValue( field.getName(), out, result.getObject(field.getColumnName()) );
				
				return out;
			}
			
		} catch(final IllegalStateException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | SQLException | InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public List<Object> getAllObjectsFromDb(final Class clazz, final Connection connection) {
		
		try {
			
			final MetaModel<?> model = MetaModel.of(object.getClass());
			final IdField primaryKey = model.getPrimaryKey();
			final String sql 		 = String.format(SQL, model.getTableName(), primaryKey.getColumnName());
			
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ParameterMetaData parameter = statement.getParameterMetaData();
			
			setStatement(statement, parameter, object, primaryKey.getName(), 1);

			System.out.println(statement);
			statement.executeUpdate();
			
		} catch(final IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch (final SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	*/
	
	public static ObjectGetter getInstance() {
		return new ObjectGetter();
	}
	
}
