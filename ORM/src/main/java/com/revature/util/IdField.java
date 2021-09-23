package com.revature.util;

import java.lang.reflect.Field;

import com.revature.annotations.Id;

public class IdField {

    private final Field field;

    public IdField(final Field field) {
        if (field.getAnnotation(Id.class) == null) {
            throw new IllegalStateException("Cannot create IdField object! Provided field, " + getName() + "is not annotated with @Id");
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
        return this.field.getAnnotation(Id.class).columnName();
    }

}
