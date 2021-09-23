package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.connection.ConnectionFactory;
import com.revature.dummymodels.TestClass;
import com.revature.objectmapper.ObjectGetter;
import com.revature.objectmapper.ObjectRemover;
import com.revature.objectmapper.ObjectSaver;
import com.revature.objectmapper.ObjectUpdater;
import com.revature.util.Configuration;

public class TestingDriver {

	public static void main(final String[] args) throws SQLException {

		final Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(TestClass.class);
		
		final Connection connection = ConnectionFactory.getInstance().getConnection();
		connection.setAutoCommit(true);
		

		final TestClass test = new TestClass("username", "password");

		final ObjectSaver saver = new ObjectSaver();
		final ObjectUpdater updater = new ObjectUpdater();
		final ObjectGetter getter = new ObjectGetter();
		final ObjectRemover remover = new ObjectRemover();
		
		
		final int id = (int) saver.addObjectToDb(test, connection);
		test.setId(id);

		test.setTestUsername("newUsername");
		test.setTestPassword("newPassword");
		updater.updateObjectInDb(test, connection);
		
		final TestClass response = (TestClass) getter.getObjectFromDb(test.getClass(), id, "test_id", connection);
		assert(response.getId() == test.getId());
		System.out.println(response.toString());
		
		remover.removeObjectFromDb(test, connection);
	}

}
