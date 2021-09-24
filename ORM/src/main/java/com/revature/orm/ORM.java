package com.revature.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
	
	/**
	 * @return Singleton instance of the ORM class
	 */
	public static ORM getInstance() {
		return DIYORM;
	}
	
	/**
	 * Removes provided object from the database, assuming an object in the database
	 * has a matching primary key.
	 * @param object to remove from database
	 * @return if the operation succeeded
	 */
	public boolean removeObjectFromDb(final Object object) {
		return this.objectRemover.removeObjectFromDb(object, this.connection);
	}
	
	/**
	 * Retrieves and object from the database using the specified column name. If more then 1 column matches the
	 * Provided value, one object is arbitrarily returned.
	 * @param clazz The object's class.
	 * @param columnName The column name to check for retrieving the value
	 * @param value The value at the column
	 * @return Retrieved Object
	 */
	public <T> T getObjectFromDb(final Class<T> clazz, final String columnName, final Object value) {
		final Optional<T> result = this.objectGetter.getObjectFromDb(clazz, value, columnName, this.connection);
		return result.isPresent() ? clazz.cast( result.get() ) : null;
	}
	
	/**
	 * Retrieves and object from the database, matching the provided value to the primary key.
	 * @param clazz The object's class.
	 * @param columnName The column name to check for retrieving the value
	 * @param value The value at the column
	 * @return Retrieved Object
	 */
	public <T> T getObjectFromDb(final Class<T> clazz, final Object value) {
		final Optional<T> result = this.objectGetter.getObjectFromDb(clazz, value, this.connection);
		return result.isPresent() ? clazz.cast( result.get() ) : null;
	}
	
	/**
	 * Returns all objects in the database of the specified class
	 * @param clazz To retrieve objects for
	 * @return list of all objects
	 */
	public <T> List<T> getAllObjectsFromDb(final Class<T> clazz) {
		return this.objectGetter.getAllObjectsFromDb(clazz, this.connection);
	}
	
	/**
	 * Updates an existing entry in the database using the provided object. Fails if the provided object
	 * does not already exist in the database
	 * @param object Object to update.
	 * @return if the operation succeeded
	 */
	public boolean updateObjectInDb(final Object object) {
		return this.objectUpdater.updateObjectInDb(object, this.connection);
	}
	
	/**
	 * Saves an object to the database. Fails if the object already exists. If the primary key is set to auto generate,
	 * automatically sets the object's id to the generated id.
	 * @param object to save
	 * @return if the operation succeeded
	 */
	public boolean addObjectToDb(final Object object) {
		return this.objectSaver.addObjectToDb(object, this.connection);
	}
	
	public void setAutoCommit(final boolean setting) {
		try {
			this.connection.setAutoCommit(setting);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getAutoCommit() {
		try {
			return this.connection.getAutoCommit();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void commit() {
		try {
			this.connection.commit();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rollback() {
		try {
			this.connection.rollback();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
	}

}
