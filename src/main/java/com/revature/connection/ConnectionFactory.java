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
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
	}
	
	private ConnectionFactory() {
		
		try {
			Properties props = new Properties();
			props.load(new FileReader("src/main/resources/application.properties"));
			ds = new BasicDataSource();
			ds.setUrl(props.getProperty("url"));
			ds.setUsername(props.getProperty("username"));
			ds.setPassword(props.getProperty("password"));
			ds.setMinIdle(5);
			ds.setDefaultAutoCommit(false);
			ds.setMaxOpenPreparedStatements(100);
			
		} catch (IOException e) {
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
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	

}
