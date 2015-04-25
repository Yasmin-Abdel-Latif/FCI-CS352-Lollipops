package com.FCI.SWE.Models;

import java.util.List;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class Share {

	public String postOwner;
	public String postSharer;
	public int ID;
	public int postID;
	
	public Share(){}
	public Share(int postId)
	{
		postID = postId;
	}
	@SuppressWarnings("deprecation")
	public void share()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Post");
		gaeQuery.addFilter("ID", FilterOperator.EQUAL, postID);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Entity entity = pq.asSingleEntity();
		postOwner = entity.getProperty("Owner").toString();
		postSharer = UserController.userData.getName();
		
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Share");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		List<Entity> list1 = pq1.asList(FetchOptions.Builder.withDefaults());
		ID = list1.size()+1;
		Entity sharePost = new Entity("Share", ID);
		sharePost.setProperty("ID", ID);
		sharePost.setProperty("PostID", postID);
		sharePost.setProperty("PostOwner", postOwner);
		sharePost.setProperty("PostSharer", postSharer);
		datastore1.put(sharePost);
		UserController.echo = "Post Shared Successfully";
	}
}
