package com.revature.orm;

import java.sql.Connection;

import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectUpdater;

public class ORM {
	
	private final static ORM DIYORM = new ORM();
	
	private final Connection connection;
	private final ObjectGetter objectGetter;
	private final ObjectSaver objectSaver;
	private final ObjectUpdater objectUpdater;
	private final ObjectRemover objectRemover;
	
	private ORM() {
		this.connection = ConnectionFactory.getInstance().getConnection();
		this.objectGetter = ObjectGetter.getInstance();
		this.objectSaver = ObjectSaver.getInstance();
		this.objectUpdater = ObjectUpdater.getInstance();
		this.objectRemover = ObjectRemover.getInstance();
	}
	
	public static ORM getInstance() {
		return DIYORM;
	}
	
	public boolean removeObjectFromDb(final Object object) {
		return this.objectRemover.removeObjectFromDb(object, this.connection);
	}
	
	public <T> T getObjectFromDb(final Class<T> clazz, final String columnName, final Object value) {
		return clazz.cast( this.objectGetter.getObjectFromDb(clazz, value, columnName, this.connection) );
	}
	
	public boolean updateObjectInDb(final Object object) {
		return this.objectUpdater.updateObjectInDb(object, this.connection);
	}
	
	public Object addObjectToDb(final Object object) {
		return this.objectSaver.addObjectToDb(object, this.connection);
	}

}
