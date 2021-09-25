package com.revature.objectmapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.revature.exceptions.ClassNotConfiguredException;
import com.revature.exceptions.UnsupportedTypeException;
import com.revature.orm.Configuration;
import com.revature.util.ColumnField;
import com.revature.util.IdField;
import com.revature.util.MetaModel;

public abstract class ObjectMapper {
	
	protected void setStatement(final PreparedStatement statement, final ParameterMetaData pd, final Object value, final int index) {
		try {
			setPreparedStatementByType(statement, pd.getParameterTypeName(index), value.toString(), index);
		} catch (IllegalArgumentException | SQLException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	protected void setPreparedStatementByType(final PreparedStatement statement, final String type, final String input, final int index) throws SQLException {

		//TODO: smart type evaluation
		switch (type) {
		case "boolean":
			statement.setBoolean(index, Boolean.parseBoolean(input));
			break;
		case "varchar":
		case "text":
		case "string":
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
			throw new UnsupportedTypeException(type + " is not mapped to a prepared statement type.");
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
	
	private static final Map<Class<?>,String> TYPES;
	static {
		TYPES = new HashMap<Class<?>,String>();
		TYPES.put(boolean.class,   "BOOLEAN");
		TYPES.put(Boolean.class,   "BOOLEAN");
		TYPES.put(byte.class,      "TINYINT");
		TYPES.put(Byte.class,      "TINYINT");
		TYPES.put(short.class,     "SMALLINT");
		TYPES.put(Short.class,     "SMALLINT");
		TYPES.put(int.class,       "INT");
		TYPES.put(Integer.class,   "INT");
		TYPES.put(long.class,      "BIGINT");
		TYPES.put(Long.class,      "BIGINT");
		TYPES.put(float.class,     "FLOAT(24)");
		TYPES.put(Float.class,     "FLOAT(24)");
		TYPES.put(double.class,    "FLOAT(53)");
		TYPES.put(Double.class,    "FLOAT(53)");
		TYPES.put(char.class,      "CHAR(1)");
		TYPES.put(Character.class, "CHAR(1)");
		
		TYPES.put(String.class,    "VARCHAR(50)");
		TYPES.put(BigInteger.class,"BIGINT");
		TYPES.put(BigDecimal.class,"MONEY");
	}
	
	private static final String FOREIGNKEY = "%s%s, FOREIGN KEY (%s) REFERENCES %s(%s)";
	protected String getSqlType(final ColumnField field) {
		String modifiers = "";
		if(!field.getNullable())
			modifiers += " NOT NULL";
		if(field.getUnique())
			modifiers += " UNIQUE";
		
		if(TYPES.containsKey(field.getType()))
			return TYPES.get(field.getType()) + modifiers;
		
		try {
			final MetaModel<?> model = Configuration.getInstance().getModel(field.getType());
			return String.format(FOREIGNKEY, 
					getSqlType(model.getPrimaryKey()),
					modifiers,
					field.getColumnName(),
					model.getTableName(),
					model.getPrimaryKey().getColumnName()
				);
		} catch(final ClassNotConfiguredException e) {
			throw new UnsupportedTypeException(field.getName() + " is not mapped to a SQL type.");
		}
	}
	
	protected String getSqlType(final IdField field) {
		if(TYPES.containsKey(field.getType())) {
			return TYPES.get(field.getType());
		}
		throw new UnsupportedTypeException(field.getName() + " is not mapped to a SQL type.");
	}

}
