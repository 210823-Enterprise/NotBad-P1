package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Column;

public class ColumnField {
	
//	@Column
	//private String name; (// how do I determine if this is a VARCHAR or NUMERIC or SERIAL PRIMARY KEY?

	private final Field field;
	
	public ColumnField(final Field field) {
		
		if (field.getAnnotation(Column.class) == null) {
			// If the field object that we pass through DOESN't have the column annotation, then it returns null
			throw new IllegalStateException("Cannot create ColumnField Object! Provided field " + getName() + " is not annotated with @Column");
		}
		
		this.field = field;
		
	}
	
	public String getName() {
		return this.field.getName();
	}
	
	public Class<?> getType() {
		return this.field.getType();
	}
	
	public String getColumnName() {
		return this.field.getAnnotation(Column.class).columnName();
		
	}
	
	
	
}

