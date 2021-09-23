package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public class ObjectSaver extends ObjectMapper {
	
	private static final String SQL = "INSERT INTO %s (%s) VALUES (%s) RETURNING %s.%s;";
	
	public Object addObjectToDb(final Object object, final Connection connection) {
		
		try {
			final MetaModel<?> model = MetaModel.of(object.getClass());
			final IdField primaryKey = model.getPrimaryKey();
			final List<ColumnField> fields = model.getColumns();

			//generate list of entry names
			String entries = "";
			for(final ColumnField field: fields)
				if(!field.getColumnName().equals(primaryKey.getColumnName()))
					entries += field.getColumnName() + ", ";
			if(entries.length() > 2)
			 entries = entries.substring(0, entries.length()-2);
			
			//generate string of ? of needed length
			String qs = "";
			for(int i = 0; i < fields.size(); i++)
				qs += "?, ";
			if(entries.length() > 2)
				qs = qs.substring(0,qs.length()-2);
			
			//generate prepared statement
			final String sql 		 = String.format(SQL, model.getTableName(), entries, qs, model.getTableName(), primaryKey.getColumnName() );
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ParameterMetaData parameter = statement.getParameterMetaData();
			
			//set each parameter
			for(int i = 0; i < fields.size(); i++)
				setStatement(statement, parameter, object, fields.get(i).getName(), i+1);

			final ResultSet result = statement.executeQuery();
			if( result != null) {
				result.next();
				return result.getObject(1);
			}
		} catch(final IllegalStateException e) {
			e.printStackTrace();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ObjectSaver getInstance() {
		return new ObjectSaver();
	}
	
}
