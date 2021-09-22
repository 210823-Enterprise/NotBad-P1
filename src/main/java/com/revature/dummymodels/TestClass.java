package com.revature.dummymodels;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

@Entity(tableName="test_table")
public class TestClass {

	@Id(columnName="test_id")
	private final int id;
	
	@Column(columnName="test_username")
	private final String testUsername;
	
	@Column(columnName="test_password")
	private final String testPassword;

	public TestClass(final int id, final String testUsername, final String testPassword) {
		super();
		this.id = id;
		this.testUsername = testUsername;
		this.testPassword = testPassword;
	}
	
}
