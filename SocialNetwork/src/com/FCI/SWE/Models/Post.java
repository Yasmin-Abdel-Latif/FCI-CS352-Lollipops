/**
 * 
 * @author Nour Mohammed Srour Alwani
 * @version 1.0
 * @since 2014-02-12
 *
 */


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

public class Post extends AbsPost{
	String OwnerOfTimeline;
	String poster;
	String content=null;
	int nLikes;
	int iD;
	static int staticID=0;
	String userOrPage; //timeline of post indicator u for user - p for page
	String feeling="default";
	String privacy;
	String[] cList;
	
	public Post(String ownerOfTimeline, String poster, String content,
			int nLikes,String up, String postPrivacy, String postFeelings) {
		super();
		OwnerOfTimeline = ownerOfTimeline;
		this.poster = poster;
		this.content = content;
		this.nLikes = nLikes;
		staticID++; //post id starts with 1
		iD=staticID;
		userOrPage=up;
		this.feeling=postFeelings;
		this.privacy=postPrivacy;
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
	public int registerPost() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int size = list.size()+1;
		Entity post = new Entity("Post", size);

		post.setProperty("ID", size);
		post.setProperty("Owner", OwnerOfTimeline);
		post.setProperty("Poster", poster);
		post.setProperty("Content", content);
		post.setProperty("nLikes", nLikes);
		post.setProperty("UserOrPage",userOrPage);
		post.setProperty("Privacy",privacy);
		post.setProperty("Feelings",feeling);
		if (privacy.equals("Custom"))
		{
			post.setProperty("CustomListNumber", UserController.customList.length);
			for (int i=0;i<UserController.customList.length;i++)
			{
				post.setProperty("FriendCus# "+i,UserController.customList[i]);
				
			}			
		}
		datastore.put(post);
		return size;

	}
	
	public String getFeeling() {
		return feeling;
	}

	public void setFeeling(String feeling) {
		this.feeling = feeling;
	}

	/**
	 * register post method to save it in datastore
	 * @return true after saving
	 */
	public int registerPostOnFP(String uName) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int size = list.size()+1;
		Entity post = new Entity("Post", size);

		post.setProperty("ID", size);
		post.setProperty("Owner", UserController.fpName);
		post.setProperty("Poster", poster);
		post.setProperty("Content", content);
		post.setProperty("nLikes", nLikes);
		post.setProperty("UserOrPage",userOrPage);
		post.setProperty("Privacy",privacy);
		post.setProperty("Feelings",feeling);
		datastore.put(post);
		return size;

	}
	@SuppressWarnings("deprecation")
	public static Post getPostByName(int ID) 
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("Post");
		query.addFilter("ID", FilterOperator.EQUAL, ID);
		PreparedQuery pq = datastore.prepare(query);
		Entity postEntity = pq.asSingleEntity();
		String owner= (postEntity.getProperty("Owner")).toString();
		String poster=( postEntity.getProperty("Poster")).toString();
		String content=	(postEntity.getProperty("Content")).toString();
		int nlikes= Integer.parseInt((postEntity.getProperty("nLikes")).toString());
		String userorpage=( postEntity.getProperty("UserOrPage")).toString();
		String feeling = ( postEntity.getProperty("Feelings")).toString();
		String privacy = ( postEntity.getProperty("Privacy")).toString();
		Post p = new Post(owner , poster , content , nlikes , userorpage, privacy, feeling);
		return p;
	}
}
