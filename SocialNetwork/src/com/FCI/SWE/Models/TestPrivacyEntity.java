package com.FCI.SWE.Models;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestPrivacyEntity {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	Privacy p;

	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void fillAccToPrivacyPublicModelTest() {
		p = new Public();
		Post p1 = new Post("MohamedSamir", "MohamedSamir", "Happy Birthday", 1,
				"u", "Public", "Happy", 1, 0);
		p1.registerPost();
		Post p2 = new Post("Nur Alwani", "Nur Alwani", "Happy Birthday", 1,
				"u", "Public", "Happy", 2, 0);
		p2.registerPost();
		
		Post p3 = new Post("Nur Alwani", "Nur Alwani", "Happy New Year", 1,
				"u", "Public", "Happy", 3, 0);
		p3.registerPost();
		ArrayList<Post> posts = new ArrayList<Post>();
		
		Assert.assertEquals(p.fillAccToPrivacy(posts, "Nur Alwani", "").size(), 2);
	}
	
	@Test
	public void fillAccToPrivacyPrivateModelTest() {
		p = new Private();
		Post p1 = new Post("MohamedSamir", "MohamedSamir", "Happy Birthday", 1,
				"u", "Private", "Happy", 1, 0);
		p1.registerPost();
		Post p2 = new Post("Nur Alwani", "Nur Alwani", "Happy Birthday", 1,
				"u", "Private", "Happy", 2, 0);
		p2.registerPost();
		
		ArrayList<Post> posts = new ArrayList<Post>();
		
		Assert.assertEquals(p.fillAccToPrivacy(posts, "Nur Alwani", "").size(), 1);
	}
	
//	@Test
//	public void fillAccToPrivacyCustomModelTest() {
//		p = new Custom();
//	}
}
