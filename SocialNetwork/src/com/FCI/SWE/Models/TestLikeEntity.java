package com.FCI.SWE.Models;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestLikeEntity {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	Like l;
	Page p = new Page("Hello", "Social", "better world for better future", "MohamedSamir");
	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void likeObjModelTest() 
	{
		p.savePage(1);
		l = new Like("page", 1);
		l.likeObj("Nur Alwani");
		Page p2 = new Page("Hello Guys", "Social", "better world for better future", "MohamedSamir");
		p2.savePage(2);
		l = new Like("page", 2);
		l.likeObj("Nur Alwani");
		Page p1 = new Page("Hello world", "Social", "better world for better future", "MohamedSamir");
		l = new Like();
		l.setObjID(p1.savePage(l.nextLikeID()));
		l.setPostOrPage("page");
		Assert.assertEquals(l.likeObj("Nur Alwani"), 3);
	}
	
	@Test(dependsOnMethods={"likeObjModelTest"})
	public void allLikeUsersByIDModelTest() 
	{
		Assert.assertEquals(l.allLikeUserByID(1), 1);
	}
	
	@Test(dependsOnMethods={"likeObjModelTest"})
	public void likePageModelTest() 
	{
		l = new Like();
		l.setLikeID(1);
		Assert.assertEquals(l.likePage("yasmin"), 1);
	}
	
	@Test(dependsOnMethods={"likePageModelTest"})
	public void allLikeUserModelTest() 
	{
		l = new Like("page", 1);
		Assert.assertEquals(l.allLikeUser(), 2);
	}

	@Test(dependsOnMethods={"likePageModelTest"})
	public void checkLikeIDModelTest() 
	{
		l = new Like();
		l.setLikeID(1);
		Assert.assertEquals(l.checkLikeID().booleanValue(), true);
	}
	
	@Test
	public void likeObjModelTest1() 
	{
		Post p = new Post("MohamedSamir", "MohamedSamir", "Happy Birthday", 1, "u", "Public", "Happy", 1, 0);
		p.registerPost();
		l = new Like("post", 1);
		l.likeObj("Nur Alwani");
		Post p2 = new Post("MohamedSamir", "Nur Alwani", "Happy Birthday", 1, "u", "Public", "Happy", 1, 0);
		l = new Like();
		l.setObjID(p2.registerPostOnFP("MohamedSamir"));
		l.setPostOrPage("post");
		Assert.assertEquals(l.likeObj("MohamedSamir"), 5);
	}
	
	@Test(dependsOnMethods={"likeObjModelTest1"})
	public void likePostModelTest() 
	{
		l = new Like("post", 1);
		Assert.assertEquals(l.likePost("yasmin"), 4);
	}
}
