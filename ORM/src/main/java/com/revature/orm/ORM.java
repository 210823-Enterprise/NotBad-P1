package com.revature.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import java.util.Optional;

import com.revature.connection.ConnectionFactory;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectTable;
import com.revature.objectmapper.ObjectUpdater;
import com.revature.util.MetaModel;

public class ORM {
	
	private final static ORM DIYORM = new ORM();

	private final Connection connection;
	private final ObjectGetter objectGetter;
	private final ObjectSaver objectSaver;
	private final ObjectUpdater objectUpdater;
	private final ObjectRemover objectRemover;
	private final ObjectTable objectTable;
	
	private Savepoint savepoint;
	
	private ORM() {
		this.connection = ConnectionFactory.getInstance().getConnection();
		this.objectGetter = ObjectGetter.getInstance();
		this.objectSaver = ObjectSaver.getInstance();
		this.objectUpdater = ObjectUpdater.getInstance();
		this.objectRemover = ObjectRemover.getInstance();
		this.objectTable = ObjectTable.getInstance();
		this.savepoint = null;
		
		try {
			this.connection.setAutoCommit(true);
		} catch (final SQLException e) {}
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
	
	/**
	 * Creates a table for the specified model is none exists.
	 * @param model to create a table for.
	 */
	boolean generateTable(final MetaModel<?> model) {
		return this.objectTable.createTableInDb(model, this.connection);
	}
	
	/**
	 * Starts a transaction. Disabled auto-commit and logs database queries.
	 */
	public void startTransation() {
		if(this.connection == null) {
			try {
				this.connection.setAutoCommit(false);
				this.savepoint = this.connection.setSavepoint();
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("A transaction has already been started.");
		}
	}
	
	/**
	 * Removes all recorded queries.
	 */
	public void rollBack() {
		if(this.connection != null) {
			try {
				this.connection.rollback(this.savepoint);
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("No transaction has been started.");
		}
	}
	
	/**
	 * Removes all recorded queries and closes the transaction. Re-enables auto-commit.
	 */
	public void abort() {
		if(this.connection != null) {
			try {
				this.connection.rollback(this.savepoint);
				this.connection.setAutoCommit(true);
				this.savepoint = null;
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("No transaction has been started.");
		}
	}
	/**
	 * Commits all recorded queries and closes the transaction. Re-enables auto-commit.
	 */
	public void commit() {
		if(this.connection != null) {
			try {
				this.connection.commit();
				this.connection.setAutoCommit(true);
				this.savepoint = null;
			} catch (final SQLException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("No transaction has been started.");
		}
	}
	

}
