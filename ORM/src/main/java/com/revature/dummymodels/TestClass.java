package com.revature.dummymodels;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

@Entity(tableName="test_table")
public class TestClass {

	@Override
	public String toString() {
		return "TestClass [id=" + id + ", testUsername=" + testUsername + ", testPassword=" + testPassword + "]";
	}

	@Id(columnName="test_id")
	private int id;
	
	@Column(columnName="test_username")
	private String testUsername;

	@Column(columnName="test_password")
	private String testPassword;

	public TestClass() {
		super();
	}

	public TestClass(final String testUsername, final String testPassword) {
		super();
		this.id = -1;
		this.testUsername = testUsername;
		this.testPassword = testPassword;
	}

	public TestClass(final int id, final String testUsername, final String testPassword) {
		super();
		this.id = id;
		this.testUsername = testUsername;
		this.testPassword = testPassword;
	}
	
	public boolean setId(final int id) {
		if(this.id >= 0)
			return false;
		this.id = id;
		return true;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getTestUsername() {
		return this.testUsername;
	}

	public void setTestUsername(final String testUsername) {
		this.testUsername = testUsername;
	}

	public String getTestPassword() {
		return this.testPassword;
	}

	public void setTestPassword(final String testPassword) {
		this.testPassword = testPassword;
	}
	
}
