package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.connection.ConnectionFactory;
import com.revature.dummymodels.TestClass;
import com.revature.objectmapper.ObjectUpdater;
import com.revature.util.Configuration;

public class TestingDriver {

	public static void main(final String[] args) throws SQLException {

		final Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(TestClass.class);
		
		final Connection connection = ConnectionFactory.getInstance().getConnection();
		connection.setAutoCommit(true);
		final ObjectUpdater remover = new ObjectUpdater();
		final TestClass test = new TestClass(1, "test2", "testing2");
		
		remover.updateObjectInDb(test, connection);
	}

}
