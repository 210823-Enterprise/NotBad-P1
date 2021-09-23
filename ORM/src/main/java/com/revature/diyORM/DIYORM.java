package com.revature.diyORM;

import java.sql.Connection;

import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectUpdater;

public class DIYORM {
	
	private final static DIYORM DIYORM = new DIYORM();
	
	private final Connection connection;
	private final ObjectGetter objectGetter;
	private final ObjectSaver objectSaver;
	private final ObjectUpdater objectUpdater;
	private final ObjectRemover objectRemover;
	
	//TODO: move get connection to inside DAO methods instead of top level
	
	private DIYORM() {
		this.connection = ConnectionFactory.getInstance().getConnection();
		this.objectGetter = ObjectGetter.getInstance();
		this.objectSaver = ObjectSaver.getInstance();
		this.objectUpdater = ObjectUpdater.getInstance();
		this.objectRemover = ObjectRemover.getInstance();
	}
	
	public static DIYORM getInstance() {
		return DIYORM;
	}
	
	public boolean removeObjectFromDb(final Object object) {
		return this.objectRemover.removeObjectFromDb(object, this.connection);
	}
	
	public <T> T getObjectFromDb(final Class<T> clazz, final Object value) {
		return clazz.cast( this.objectGetter.getObjectFromDb(clazz, value, this.connection) );
	}
	
	public boolean updateObjectInDb(final Object object) {
		return this.objectUpdater.updateObjectInDb(object, this.connection);
	}
	
	public Object addObjectToDb(final Object object) {
		return this.objectSaver.addObjectToDb(object, this.connection);
	}

}
