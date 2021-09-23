package com.revature.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.JoinColumn;

public class MetaModel<T> {

	private final Class<T> clazz;
	private final IdField primarykeyField;
	private final List<ColumnField> columnFields;
	private final List<ForeignKeyField> foreignKeyFields;
	
	public static <T> MetaModel<T> of(final Class<T> clazz) {
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create Metamodel object! Provided class " + clazz.getName() + " is not annotated with @Entity");
		}
		return new MetaModel<>(clazz);		
	}
	
	public MetaModel(final Class<T> clazz) {
		this.clazz = clazz;
		this.columnFields = generateColumns();
		this.primarykeyField = generatePrimaryKey();
		this.foreignKeyFields = generateForeignKeys();
	}
	
	private IdField generatePrimaryKey() {
        final Field[] fields = this.clazz.getDeclaredFields();
        for (final Field field : fields) {
            final Id primaryKey = field.getAnnotation(Id.class);
            if (primaryKey != null) {
                return new IdField(field);
            }
        }
        throw new RuntimeException("Did not find a field annotated with @Id in: " + this.clazz.getName());
    }

    private List<ColumnField> generateColumns() {
    	
    	final List<ColumnField> columnFields = new ArrayList<ColumnField>();
        final Field[] fields = this.clazz.getDeclaredFields();
        
        for (final Field field : fields) {
            final Column column = field.getAnnotation(Column.class);
            if (column != null) {
            	columnFields.add(new ColumnField(field));
            }
        }
        if (columnFields.isEmpty()) {
            throw new RuntimeException("No columns found in: " + this.clazz.getName());
        }
        return columnFields;
    }

    private List<ForeignKeyField> generateForeignKeys() {

        final List<ForeignKeyField> foreignKeyFields = new ArrayList<>();
        final Field[] fields = this.clazz.getDeclaredFields();
        
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
	
	public String getClassName() {
		return this.clazz.getName();
	}
	
	public String getSimpleClassName() {
		return this.clazz.getSimpleName();
	}

	public Class<T> getClazz() {
		return this.clazz;
	}

	public IdField getPrimaryKey() {
		return this.primarykeyField;
	}

	public List<ColumnField> getColumns() {
		return this.columnFields;
	}

	public List<ForeignKeyField> getForeignKeys() {
		return this.foreignKeyFields;
	}

}
