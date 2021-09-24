package com.revature;

import java.sql.SQLException;
import java.util.List;

import com.revature.dummymodels.TestClass;
import com.revature.orm.ORM;
import com.revature.util.Configuration;

public class TestingDriver {

	public static void main(final String[] args) throws SQLException {

		final Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(TestClass.class);
		
		final TestClass test = new TestClass(14, "username", "password");
		final ORM orm = ORM.getInstance();
		
		assert orm.addObjectToDb(test) : "Failed to save object to database.";

		test.setTestUsername("newUsername");
		test.setTestPassword("newPassword");
		assert orm.updateObjectInDb(test) : "Failed to update object.";
		
		final TestClass response = orm.getObjectFromDb(TestClass.class, test.getId());
		assert response != null && response.getId() == test.getId() : "Retrieved Object's Id does not match the expected id";
		System.out.println(response.toString() + "\n----------------------------------------------------------------------------------");
		
		final List<TestClass> list = orm.getAllObjectsFromDb(TestClass.class);
		list.forEach(e -> System.out.println(e.toString()));
		
		assert orm.removeObjectFromDb(test) : "Failed to delete object from database.";
	}

}
