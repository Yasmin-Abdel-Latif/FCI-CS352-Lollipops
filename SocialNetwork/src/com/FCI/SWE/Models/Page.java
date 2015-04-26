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
	int likeID;

	public Page(String pageName, String type, String category,
			String pageOwner, int nLikes) {
		super();
		this.pageName = pageName;
		this.type = type;
		this.category = category;
		this.pageOwner = pageOwner;
		this.likeID = nLikes;
	}

	/**
	 * 
	 * @param pageName
	 *            Name of the page
	 * @return true if it is exist false if not
	 */

	public Boolean checkPage() 
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("pageName").toString().equals(pageName)) 
			{
				return true;
			}
		}
		return false;
	}

	public int savePage(int likeID) {
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
		pageEntity.setProperty("likeID", likeID);
		datastore.put(pageEntity);
		return size;
	}
	
	/**
	 * 
	 * @return
	 * 		an object of the page from the table
	 */
	public Page getPage ()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Page");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity pageEntity : pq.asIterable()) 
		{
			if (pageEntity.getProperty("pageName").toString().equals(pageName)) 
			{
				pageOwner = pageEntity.getProperty("owner").toString();
				type = pageEntity.getProperty("type").toString();
				category = pageEntity.getProperty("category").toString();
				likeID = Integer.parseInt(pageEntity.getProperty("likeID").toString());
			}
		}
		
		return this;
	}
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPageOwner() {
		return pageOwner;
	}

	public void setPageOwner(String pageOwner) {
		this.pageOwner = pageOwner;
	}

	public int getLikeID() {
		return likeID;
	}

	public void setLikeID(int likeID) {
		this.likeID = likeID;
	}

	public Page(String pageName) {
		super();
		this.pageName = pageName;
	}
	
	public Page(String pageName, String type, String category, String pageOwner) {
		super();
		this.pageName = pageName;
		this.type = type;
		this.category = category;
		this.pageOwner = pageOwner;
	}

	public Page() {
		// TODO Auto-generated constructor stub
	}
}
