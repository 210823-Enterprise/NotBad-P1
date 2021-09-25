package com.revature;

import com.revature.dummymodels.SubTestClass;
import com.revature.dummymodels.TestClass;
import com.revature.orm.Configuration;
import com.revature.orm.ORM;

public class TestDriver {

	public static void main(final String[] args) {
		final Configuration config = Configuration.getInstance();
		config.addAnnotatedClass(TestClass.class);
		config.addAnnotatedClass(SubTestClass.class);
		config.finalizeConfig();
		
		final TestClass testClass = new TestClass(1,"name","pass", new SubTestClass(1,"subclass"));
		assert ORM.getInstance().addObjectToDb(testClass) : "Failed to add object to database";
		
		testClass.setTestUsername("newUsername");
		testClass.getSubclass().setTestUsername("newUsername");
		assert ORM.getInstance().updateObjectInDb(testClass) : "Failed to update object in database";
		
		final TestClass returned = ORM.getInstance().getObjectFromDb(TestClass.class,testClass.getId());
		assert returned.getId() == testClass.getId() : "Failed to get object from database";
		assert returned.getSubclass().getId() == testClass.getSubclass().getId() : "Failed to get sub-object from database";
		
		assert ORM.getInstance().removeObjectFromDb(testClass) : "Failed to remove object from database";
	}

}
