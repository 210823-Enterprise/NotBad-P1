package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.revature.orm.Configuration;
import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectUpdater extends ObjectMapper {
	
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
			for(int i = 0; i < fields.size(); i++)
				setStatement(statement, parameter, object, fields.get(i).getName(), i+1);
			setStatement(statement, parameter, object, primaryKey.getName(), fields.size()+1);
			
			//System.out.println(statement);
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
	
	public static ObjectUpdater getInstance() {
		return new ObjectUpdater();
	}
	
}
