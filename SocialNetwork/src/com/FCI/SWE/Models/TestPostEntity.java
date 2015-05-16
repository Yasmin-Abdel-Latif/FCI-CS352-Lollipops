package com.FCI.SWE.Models;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestPostEntity {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	Post p = new Post("MohamedSamir", "MohamedSamir", "Happy Birthday", 1, "u", "Public", "Happy", 1, 0);
	
	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void RegisterPostModelTest() 
	{
		Assert.assertEquals(p.registerPost(), 1);
	}
	
	@Test(dependsOnMethods={"RegisterPostModelTest"})
	public void RegisterPostModelTest1() 
	{
		Assert.assertEquals(p.registerPost(), 2);
	}
	
	@Test(dependsOnMethods={"RegisterPostModelTest1"})
	public void getPostModelTest() 
	{
		Assert.assertEquals(Post.getPostByName(2).getPoster(), "MohamedSamir");
	}
	
	@Test(dependsOnMethods={"RegisterPostModelTest1"})
	public void RegisterPostFPModelTest() 
	{
		Post p = new Post("Huda", "MohamedSamir", "Happy Birthday", 1, "u", "Public", "Happy", 1, 0);
		Assert.assertEquals(p.registerPostOnFP("Huda"), 3);
	}
}
