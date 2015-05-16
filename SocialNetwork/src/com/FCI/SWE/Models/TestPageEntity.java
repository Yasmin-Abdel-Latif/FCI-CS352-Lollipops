package com.FCI.SWE.Models;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestPageEntity {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());
	
	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void SavePageModelTest() 
	{
		Page p = new Page("Hello", "Social", "better world for better future", "MohamedSamir");
		p.savePage(1);
		Page p1 = new Page("Hello world", "Social", "better world for better future", "MohamedSamir");
		Assert.assertEquals(p1.savePage(1), 2);
	}
	
	@Test(dependsOnMethods={"SavePageModelTest"})
	public void getPageModelTest() 
	{
		Page p1 = new Page("Hello world", "", "", "");
		Assert.assertEquals(p1.getPage().getPageOwner(), "MohamedSamir");
	}
	
	@Test(dependsOnMethods={"SavePageModelTest"})
	public void checkPageModelTest() 
	{
		Page p1 = new Page("Hello world", "", "", "");
		Assert.assertEquals(p1.checkPage().booleanValue(), true);
	}
}
