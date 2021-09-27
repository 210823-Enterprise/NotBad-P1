package com.revature;

import com.revature.dummymodels.SubTestClass;
import com.revature.dummymodels.TestClass;
import com.revature.orm.Configuration;
import com.revature.orm.ORM;

public class TestDriver {

	public static void main(final String[] args) {
		/*
		 * Load objects into the configuration and run finalize so the
		 * tables are generated.
		 */
		final Configuration config = Configuration.getInstance();
		config.addAnnotatedClass(TestClass.class);
		config.addAnnotatedClass(SubTestClass.class);
		config.finalizeConfig();
		
		//testing class
		final TestClass testClass = new TestClass(1,"name","pass", new SubTestClass(1,"subclass"));
		final ORM orm = ORM.getInstance();
		
		//attempt to add item to database
		assert orm.addObjectToDb(testClass) : "Failed to add object to database";
		
		//change the username and password, then update the database
		testClass.setTestUsername("newUsername");
		testClass.getSubclass().setTestUsername("newUsername");
		assert orm.updateObjectInDb(testClass) : "Failed to update object in database";
		
		//get object and verfiy it's the same one
		final TestClass returned = orm.getObjectFromDb(TestClass.class,testClass.getId());
		assert returned.getId() == testClass.getId() : "Failed to get object from database";
		assert returned.getSubclass().getId() == testClass.getSubclass().getId() : "Failed to get sub-object from database";
		
		//delete object from db
		assert orm.removeObjectFromDb(testClass) : "Failed to remove object from database";
		
		System.out.println("All tests passed.");
	}

}
