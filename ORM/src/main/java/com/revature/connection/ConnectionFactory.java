package com.revature.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;


/**
 * Class which represents a conneciton the database.
 * Only a single instance of this class is available during the use of the application.
 *
 */
public class ConnectionFactory {
	
	private static final Logger LOG = Logger.getLogger(ConnectionFactory.class);
	
	private BasicDataSource ds;
	private static final ConnectionFactory connection_factory = new ConnectionFactory();
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (final ClassNotFoundException e) {
			LOG.error(e.getLocalizedMessage());
		}
	}
	
	private ConnectionFactory() {
		try {			
			final InputStream app = getClass().getResourceAsStream("/application.properties");
			
			final Properties props = new Properties();
			props.load(app);
			this.ds = new BasicDataSource();
			this.ds.setUrl(props.getProperty("url"));
			this.ds.setUsername(props.getProperty("username"));
			this.ds.setPassword(props.getProperty("password"));
			this.ds.setMinIdle(5);
			this.ds.setDefaultAutoCommit(false);
			this.ds.setMaxOpenPreparedStatements(100);
		} catch (final IOException e) {
			LOG.error("Unable to load src/main/resources/application.properties. Connection aborted.");
			LOG.error(e.getLocalizedMessage());
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
			LOG.error("failed to get connection " + e.getLocalizedMessage());
		}
		return null;
		
	}
	
	

}
