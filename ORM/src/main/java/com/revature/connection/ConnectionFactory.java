package com.revature.connection;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;


/**
 * Class which represents a conneciton the database.
 * Only a single instance of this class is available during the use of the application.
 *
 */
public class ConnectionFactory {
	
	private BasicDataSource ds;
	private static final ConnectionFactory connection_factory = new ConnectionFactory();
	
	static { // static initalizer loads before the main method
	
			try {
				Class.forName("org.postgresql.Driver");
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
			}
		
	}
	
	private ConnectionFactory() {
		
		try {
			final Properties props = new Properties();
			props.load(new FileReader("src/main/resources/application.properties"));
			this.ds = new BasicDataSource();
			this.ds.setUrl(props.getProperty("url"));
			this.ds.setUsername(props.getProperty("username"));
			this.ds.setPassword(props.getProperty("password"));
			this.ds.setMinIdle(5);
			this.ds.setDefaultAutoCommit(false);
			this.ds.setMaxOpenPreparedStatements(100);
			
		} catch (final IOException e) {
			// log that the file can't be found 
			// research into creating a custom loggers using a BufferedReader
		}
	}
	
	/**
	 * This is a method to retrieve a current static instance of the ConnectionFactory class.
	 * @return
	 */
	public static ConnectionFactory getInstance() {
		return connection_factory;
	}
	
	/**
	 * Method to retrieve a connection to database
	 * @return connection object
	 */
	public Connection getConnection() {
		
		try {
			return this.ds.getConnection();
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	

}
