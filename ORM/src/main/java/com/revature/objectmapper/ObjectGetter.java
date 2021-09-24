package com.revature.objectmapper;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.util.ColumnField;
import com.revature.util.MetaModel;

public class ObjectGetter extends ObjectMapper{
	
	private static final String GETSQL = "SELECT * from %s where %s = ?;";
	
	public <T> Optional<T> getObjectFromDb(final Class<T> clazz, final Object object, final Connection connection) {
		final MetaModel<T> model = MetaModel.of(clazz);
		return getObjectFromDb(clazz, object, model.getPrimaryKey().getColumnName(), connection);
	}
	
	public <T> Optional<T> getObjectFromDb(final Class<T> clazz, final Object object, final String columnName, final Connection connection) {
		
		try {
			final MetaModel<T> model = MetaModel.of(clazz);
			final String sql 		 = String.format(GETSQL, model.getTableName(), columnName);
			
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ParameterMetaData parameter = statement.getParameterMetaData();
			
			setPreparedStatementByType(statement, parameter.getParameterTypeName(1), object.toString(), 1);

			final ResultSet result = statement.executeQuery();
			if( result.next() )
				return constructObject(model, result);
			
		} catch(final IllegalStateException | IllegalArgumentException | SecurityException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static final String GETALLSQL = "SELECT * from %s;";
	
	public <T> List<T> getAllObjectsFromDb(final Class<T> clazz, final Connection connection) {
		final List<T> list = new ArrayList<T>();
		try {	
			final MetaModel<T> model = MetaModel.of(clazz);
			final String sql 		 = String.format(GETALLSQL, model.getTableName());
			
			final PreparedStatement statement = connection.prepareStatement(sql);
			final ResultSet result = statement.executeQuery();
			
			while( result.next() ) {
				final Optional<T> construct = constructObject(model, result);
				if(construct.isPresent())
					list.add(construct.get());
			}
			return list;
		} catch(final IllegalStateException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> constructObject(final MetaModel<T> model, final ResultSet result) {
		//TODO: fix requiring a defined empty constructor
		try {
			final Object out = model.getClazz().newInstance();
			setValue( model.getPrimaryKey().getName(), out, result.getObject(model.getPrimaryKey().getColumnName()) );
			
			for(final ColumnField field: model.getColumns())
				setValue( field.getName(), out, result.getObject(field.getColumnName()) );
			
			return (Optional<T>) Optional.of(out);
		} catch (final Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static ObjectGetter getInstance() {
		return new ObjectGetter();
	}
	
}
