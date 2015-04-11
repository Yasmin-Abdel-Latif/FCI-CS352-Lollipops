package com.FCI.SWE.Models;

import java.util.List;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Post {
	String OwnerOfTimeline;
	String poster;
	String content=null;
	int nLikes;
	int iD;
	static int staticID=0;
	String userOrPage; //timeline of post indicator u for user - p for page
	

	
	public Post(String ownerOfTimeline, String poster, String content,
			int nLikes,String up) {
		super();
		OwnerOfTimeline = ownerOfTimeline;
		this.poster = poster;
		this.content = content;
		this.nLikes = nLikes;
		staticID++; //post id starts with 1
		iD=staticID;
		userOrPage=up;
	}

	public String getOwnerOfTimeline() {
		return OwnerOfTimeline;
	}

	public void setOwnerOfTimeline(String ownerOfTimeline) {
		OwnerOfTimeline = ownerOfTimeline;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getnLikes() {
		return nLikes;
	}
	public void setnLikes(int nLikes) {
		this.nLikes = nLikes;
	}


	/**
	 * register post method to save it in datastore
	 * @return true after saving
	 */
	public Boolean registerPost() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		Entity post = new Entity("post", list.size()+1);

		post.setProperty("Owner", OwnerOfTimeline);
		post.setProperty("Poster", poster);
		post.setProperty("Content", content);
		post.setProperty("nLikes", nLikes);
		post.setProperty("UserOrPage",userOrPage);
		datastore.put(post);
		return true;

	}
	
	/**
	 * register post method to save it in datastore
	 * @return true after saving
	 */
	public Boolean registerPostOnFP(String uName) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		Entity post = new Entity("post", list.size()+1);

		post.setProperty("Owner", UserController.fpName);
		post.setProperty("Poster", poster);
		post.setProperty("Content", content);
		post.setProperty("nLikes", nLikes);
		post.setProperty("UserOrPage",userOrPage);
		datastore.put(post);
		return true;

	}
}
