package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.dummymodels.TestClass;
import com.revature.objectmapper.ObjectRemover;

public class ObjectMapperTests {

	private Connection connection;
	private TestClass testObject;
	private ObjectRemover remover;
	
	@Before
	public void setup() throws SQLException {
		this.connection = ConnectionFactory.getInstance().getConnection();
		
		this.testObject = new TestClass(1, "test", "testing");
		this.remover = new ObjectRemover();
	}
	
	@After
	public void tearDown() {
		this.connection = null;
		this.testObject = null;
		this.remover = null;
	}
	
	@Test
	public void remove_normal() {
		this.remover.removeObjectFromDb(this.testObject, this.connection);
	}
	
	
	
}
