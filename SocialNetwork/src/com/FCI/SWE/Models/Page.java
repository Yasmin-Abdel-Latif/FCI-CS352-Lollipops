package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Page {

	String pageName;
	String type;
	String category;
	String pageOwner;
	int nLikes;
	String activeUser1;
	String activeUser2;
	String activeUser3;

	public Page(String pageName, String type, String category,
			String pageOwner, int nLikes, String activeUser1,
			String activeUser2, String activeUser3) {
		super();
		this.pageName = pageName;
		this.type = type;
		this.category = category;
		this.pageOwner = pageOwner;
		this.nLikes = nLikes;
		this.activeUser1 = activeUser1;
		this.activeUser2 = activeUser2;
		this.activeUser3 = activeUser3;
	}

	/**
	 * 
	 * @param pageName
	 *            Name of the page
	 * @return true if it is exist false if not
	 */

	public Boolean checkPage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("pageName").toString().equals(pageName)) {
				return true;
			}

		}

		return false;
	}

	public Boolean savePage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int size = list.size() + 1;
		Entity pageEntity = new Entity("Page", size);
		pageEntity.setProperty("PageID", size);
		pageEntity.setProperty("owner", pageOwner);
		pageEntity.setProperty("pageName", pageName);
		pageEntity.setProperty("type", type);
		pageEntity.setProperty("category", category);
		pageEntity.setProperty("nLikes", 0);
		pageEntity.setProperty("activeUser1", "");
		pageEntity.setProperty("activeUser2", "");
		pageEntity.setProperty("activeUser3", "");
		datastore.put(pageEntity);
		return true;
	}
	
	public Page(String pageName, String type, String category, String pageOwner) {
		super();
		this.pageName = pageName;
		this.type = type;
		this.category = category;
		this.pageOwner = pageOwner;
	}

}
