package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ObjectMapper {
	
	protected void setStatement(final PreparedStatement statement, final ParameterMetaData pd, final Object object, final String fieldName, final int index) {
		try {
			
			//TODO: move get value to meta model, try to not use a diry hack
			final Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			final Object value = field.get(object);
			field.setAccessible(false);
			
			setPreparedStatementByType(statement, pd.getParameterTypeName(index), value.toString(), index);
			
		} catch (IllegalAccessException | IllegalArgumentException | SQLException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void setPreparedStatementByType(final PreparedStatement statement, final String type, final String input, final int index) {

		//TODO: smart type evaluation
		try {
			System.out.println(type);
			switch (type) {
			case "boolean":
				statement.setBoolean(index, Boolean.parseBoolean(input));
				break;
			case "varchar":
				statement.setString(index, input);
				break;
			case "int":
			case "int4":
				statement.setInt(index, Integer.parseInt(input));
				break;
			case "double":
				statement.setDouble(index, Double.parseDouble(input));
				break;
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}

	}

}
