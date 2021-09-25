package com.revature;

public class NotBadAPiTests {
	
	/*
	private ORM orm;
	private Configuration config;
	private TestClass testObject;
	
	@Before
	public void setUp() throws Exception {
		this.config = Configuration.getInstance();
		this.config.addAnnotatedClass(TestClass.class);
		this.orm = ORM.getInstance();
		this.testObject = new TestClass(1, "passing", "failing");
	}

	@After
	public void tearDown() {
		this.config = null;
		this.orm = null;
		this.testObject = null;
	}

	@Test
	public void test_addObject() {
		this.testObject.setTestUsername("username");
		this.testObject.setTestPassword("password");
		assertTrue(this.orm.addObjectToDb(this.testObject));
	}

	@Test
	public void test_updateObject() {
		this.testObject.setTestUsername("newUsername");
		this.testObject.setTestPassword("newPassword");
		assertTrue(this.orm.updateObjectInDb(this.testObject));
	}

	@Test
	public void test_getObject() {
		final TestClass response = this.orm.getObjectFromDb(TestClass.class, this.testObject.getId());
		assertTrue(response.getId() == this.testObject.getId());
	}

	@Test
	public void test_removeObject() {
		assertTrue(this.orm.removeObjectFromDb(this.testObject));
	}
	*/
}
