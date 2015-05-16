package com.FCI.SWE.Models;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestFriendEntity {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	Friend f = new Friend("Nur Alwani", "MohamedSamir");
	
	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void SendRequestModelTest() 
	{
		UserEntity user = new UserEntity("Nur Alwani", "n.alwani@gmail.com", "123");
		UserEntity user1 = new UserEntity("MohamedSamir", "m.samir@gmail.com", "123");
		if(UserEntity.getUserWithName("Nur Alwani") == null)
			user.saveUser();
		if(UserEntity.getUserWithName("MohamedSamir") == null)
			user1.saveUser();
		Assert.assertEquals(f.sendRequest().booleanValue(), true);
	}
	
	
	@Test(dependsOnMethods={"SendRequestModelTest"})
	public void AcceptRequestModelTest() 
	{
		Assert.assertEquals(f.Accept("Nur Alwani", "MohamedSamir"), "Done");
	}
}
