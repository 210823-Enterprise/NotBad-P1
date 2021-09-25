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
	
	private static final Map<String,Evaluation> STATEMENT_TYPES;
	static {
		final Evaluation boolType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws SQLException {
				statement.setBoolean(index, Boolean.parseBoolean(input));
			}
		};
		final Evaluation byteType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws NumberFormatException, SQLException {
				statement.setByte(index, Byte.parseByte(input));
			}
		};
		final Evaluation shortType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws NumberFormatException, SQLException {
				statement.setShort(index, Short.parseShort(input));
			}
		};
		final Evaluation intType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws NumberFormatException, SQLException {
				statement.setInt(index, Integer.parseInt(input));
			}
		};
		final Evaluation longType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws NumberFormatException, SQLException {
				statement.setLong(index, Long.parseLong(input));
			}
		};
		final Evaluation floatType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws NumberFormatException, SQLException {
				statement.setFloat(index, Float.parseFloat(input));
			}
		};
		final Evaluation doubleType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws NumberFormatException, SQLException {
				statement.setDouble(index, Double.parseDouble(input));
			}
		};
		final Evaluation stringType = new Evaluation() {
			@Override
			public void updateStatement(final PreparedStatement statement, final String input, final int index) throws SQLException {
				statement.setString(index, input);
			}
		};
		STATEMENT_TYPES = new HashMap<>();
		STATEMENT_TYPES.put("bool",     boolType);
		STATEMENT_TYPES.put("boolean",  boolType );
		STATEMENT_TYPES.put("bit",      byteType );
		STATEMENT_TYPES.put("byte",     byteType );
		STATEMENT_TYPES.put("int1",     byteType );
		STATEMENT_TYPES.put("tinyint",  shortType );
		STATEMENT_TYPES.put("int2",     shortType );
		STATEMENT_TYPES.put("int",      intType );
		STATEMENT_TYPES.put("integer",  intType );
		STATEMENT_TYPES.put("mediumint",intType );
		STATEMENT_TYPES.put("int4",     intType );
		STATEMENT_TYPES.put("bigint",     longType );
		STATEMENT_TYPES.put("int8",     longType );
		STATEMENT_TYPES.put("float",    floatType );
		STATEMENT_TYPES.put("double",   doubleType );
		STATEMENT_TYPES.put("char",      stringType );
		STATEMENT_TYPES.put("varchar",   stringType );
		STATEMENT_TYPES.put("text",      stringType );
		STATEMENT_TYPES.put("string",    stringType );
		STATEMENT_TYPES.put("tinytext",  stringType );
		STATEMENT_TYPES.put("mediumtext",stringType );
		STATEMENT_TYPES.put("longtext",  stringType );
		STATEMENT_TYPES.put("blob",      stringType );
		STATEMENT_TYPES.put("longblob",  stringType );
	}
	
	interface Evaluation {
		void updateStatement(final PreparedStatement statement, final String input, final int index) throws NumberFormatException, SQLException;
	}
	
	protected void setPreparedStatementByType(final PreparedStatement statement, final String type, final String input, final int index) throws SQLException {
		if(STATEMENT_TYPES.containsKey(type.toLowerCase())) {
			final Evaluation eval = STATEMENT_TYPES.get(type.toLowerCase());
			eval.updateStatement(statement, input, index);
		} else {
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

