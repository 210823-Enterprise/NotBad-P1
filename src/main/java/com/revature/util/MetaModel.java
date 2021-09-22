package com.revature.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

public class MetaModel<T> {

	private final Class<T> clazz;
	private IdField primarykeyField;
	private final List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;
	
	// of() method to take in a class and transform it to a meta model
	public static <T> MetaModel<T> of(final Class<T> clazz) {
		
		// first we have to check that it's marked with the Entity annotation
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create Metamodel object! Provided class " + clazz.getName() + " is not annotated with @Entity");
		}
		return new MetaModel<>(clazz);		
	}
	
	public MetaModel(final Class<T> clazz) {
		this.clazz = clazz;
		this.columnFields = new LinkedList<>();
		
	}
	
	// class name is com.revature.MyClass
	public String getClassName() {
		return clazz.getName();
	}
	
	
	// simple class name is just MyClass
	public String getSimpleClassName() {
		return clazz.getSimpleName();
	}
	
    public IdField getPrimaryKey() {

        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            final Id primaryKey = field.getAnnotation(Id.class);
            if (primaryKey != null) {
                return new IdField(field);
            }
        }
        throw new RuntimeException("Did not find a field annotated with @Id in: " + clazz.getName());
    }

    public List<ColumnField> getColumns() {

        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            final Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnFields.add(new ColumnField(field));
            }
        }

        if (columnFields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + clazz.getName());
        }

        return columnFields;
    }

    public List<ForeignKeyField> getForeignKeys() {

        final List<ForeignKeyField> foreignKeyFields = new ArrayList<>();
        final Field[] fields = clazz.getDeclaredFields();
        
        for (final Field field : fields) {
        	
            final JoinColumn column = field.getAnnotation(JoinColumn.class);
            
            if (column != null) {
                foreignKeyFields.add(new ForeignKeyField(field));
            }
        }

        return foreignKeyFields;

    }

	public String getTableName() {
		return this.clazz.getAnnotation(Entity.class).tableName();
	}

	
	
	
	
	
}
