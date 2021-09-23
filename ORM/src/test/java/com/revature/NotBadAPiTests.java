package com.revature;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.diyORM.NotBadAPi;
import com.revature.dummymodels.TestClass;

public class NotBadAPiTests {
	
	private TestClass testObject;
	private NotBadAPi api;
	

	@Before
	public void setUp() throws Exception {
		NotBadAPi api = NotBadAPi.getInstance();
		this.testObject = new TestClass(1, "passing", "failing");
	}

	@After
	public void tearDown() {
		this.testObject = null;
	}

	@Test
	public void testAddObjectToDB() {
		api.addObjectToDB(testObject);
		fail("Not yet implemented");
	}

	@Test
	public void testGetObjectsFromDB() {
		api.getObjectsFromDB(testObject);
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateObjectInDB() {
		api.updateObjectInDB(testObject);
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteObjFromDB() {
		api.deleteObjFromDB(testObject);
		fail("Not yet implemented");
	}

}
