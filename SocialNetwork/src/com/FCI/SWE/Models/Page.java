package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * this class is to control the page
 * @author mariam
 *
 */
public class Page {

	String pageName;
	String type;
	String category;
	String pageOwner;
	int likeID;
	
	/**
	 * Constrctor
	 * @param pageName
	 * @param type
	 * @param category
	 * @param pageOwner
	 * @param nLikes
	 */
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

	/**
	 * 	save the page in the table with the like iD pointer the the like table
	 * @param likeID
	 * 		the like ID 
	 * @return
	 * 		the page ID
	 */
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
	 * this method is to search for the page
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
	/**
	 * getter page Name
	 * @return
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * setter pageName
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * getter type
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * setter type
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * getter category
	 * @return
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * setter category
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * getter pageOwner
	 * @return
	 */
	public String getPageOwner() {
		return pageOwner;
	}

	/**
	 * setter pageOwner
	 * @param pageOwner
	 */
	public void setPageOwner(String pageOwner) {
		this.pageOwner = pageOwner;
	}

	/**
	 * getter LikeID
	 * @return
	 */
	public int getLikeID() {
		return likeID;
	}

	/**
	 * setter LikeID
	 * @param likeID
	 */
	public void setLikeID(int likeID) {
		this.likeID = likeID;
	}

	/**
	 * Constructor
	 * @param pageName
	 */
	public Page(String pageName) {
		super();
		this.pageName = pageName;
	}
	
	/**
	 * Constructor 
	 * @param pageName
	 * @param type
	 * @param category
	 * @param pageOwner
	 */
	public Page(String pageName, String type, String category, String pageOwner) {
		super();
		this.pageName = pageName;
		this.type = type;
		this.category = category;
		this.pageOwner = pageOwner;
	}

	/**
	 * Constructor
	 */
	public Page() {
		// TODO Auto-generated constructor stub
	}
}
