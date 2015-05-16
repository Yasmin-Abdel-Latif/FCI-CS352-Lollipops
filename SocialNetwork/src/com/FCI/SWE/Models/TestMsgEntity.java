package com.FCI.SWE.Models;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestMsgEntity {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	Messages m;
	
	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void SendChatMsgModelTest() 
	{
		m = new ChatMsg("MohamadSamir", "Huda", "Hi");
		Assert.assertEquals(m.sendMessage().booleanValue(), true);
	}
	
	@Test
	public void CreateGroupMsgModelTest() 
	{
		UserController.userData = new UserEntity("Nur Alwani", "n.alwani@gmail.com", "123");
		GroupMsg m = new GroupMsg("Lollipops", "", "");
		Assert.assertEquals(m.saveConversation("Huda;MohamadSamir;mariam").booleanValue(), true);
	}
	
	@Test(dependsOnMethods={"CreateGroupMsgModelTest"})
	public void SendGroupMsgModelTest1() 
	{
		m = new GroupMsg("Lollipops", "Nur Alwani", "Hello Guys");
		Assert.assertEquals(m.sendMessage().booleanValue(), true);
	}
}
