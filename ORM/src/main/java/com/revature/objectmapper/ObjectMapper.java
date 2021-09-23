package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class ObjectMapper {
	
	protected void setStatement(final PreparedStatement statement, final ParameterMetaData pd, final Object object, final String fieldName, final int index) {
		
		try {
			final Object value = getValue(fieldName, object);
			setPreparedStatementByType(statement, pd.getParameterTypeName(index), value.toString(), index);
		} catch (IllegalAccessException | IllegalArgumentException | SQLException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	protected void setPreparedStatementByType(final PreparedStatement statement, final String type, final String input, final int index) {

		//TODO: smart type evaluation
		try {
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
			default:
				System.out.println("Unknown type " + type);
			}
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

	//TODO: move get value to meta model, try to not use a dirty hack
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

	//TODO: move get value to meta model, try to not use a dirty hack
	protected void setValue(final String fieldName, final Object source, final Object value) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		final Field field = source.getClass().getDeclaredField(fieldName);
		if(field.isAccessible()) {
			field.set(source, value);
		} else {
			field.setAccessible(true);
			field.set(source, value);
			field.setAccessible(false);
		}
	}

}
