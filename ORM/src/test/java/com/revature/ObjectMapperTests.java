package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.connection.ConnectionFactory;
import com.revature.dummymodels.TestClass;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectUpdater;

public class ObjectMapperTests {

	private Connection connection;
	private TestClass testObject;
	private ObjectRemover remover;
	private ObjectSaver saver;
	private ObjectGetter getter;
	private ObjectUpdater updater;
	
	@Before
	public void setup() throws SQLException {
		this.connection = ConnectionFactory.getInstance().getConnection();
		
		this.testObject = new TestClass(1, "test", "testing");
		this.remover = new ObjectRemover();
		this.saver = new ObjectSaver();
		this.getter = new ObjectGetter();
		this.updater = new ObjectUpdater();
	}
	
	@After
	public void tearDown() {
		this.connection = null;
		this.testObject = null;
		this.remover = null;
		this.saver = null;
		this.getter = null;
		this.updater = null;
	}
	
	@Test
	public void remove_normal() {
		remover.removeObjectFromDb(this.testObject, this.connection);
	}
	
	@Test
	public void save_normal() {
		saver.addObjectToDb(this.testObject, this.connection);
	}
	
	@Test
	public void get_normal() {
		getter.getObjectFromDb(this.testObject, this.connection);
	}
	
	@Test
	public void update_normal() {
		updater.updateObjectInDb(this.testObject, this.connection);
	}
	
}
