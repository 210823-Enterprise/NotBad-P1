package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectRemover extends ObjectMapper {
	
	private static final String SQL = "DELETE from %s where %s = ?;";
	
	public boolean removeObjectFromDb(final Object object, final Connection connection) {
		
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
	
	
	

}
