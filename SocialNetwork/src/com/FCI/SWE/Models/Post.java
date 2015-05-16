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
	static String OwnerOfTimeline;
	static String poster;
	String content=null;
	int likeID;
	static int iD;
	String userOrPage; //timeline of post indicator u for user - p for page
	String feeling="default";
	String privacy;
	String[] cList;
	int seen ;
	
	/**
	 * Constructor
	 * @param ownerOfTimeline
	 * @param poster
	 * @param content
	 * @param nLikes
	 * @param up
	 * @param postPrivacy
	 * @param postFeelings
	 * @param ID
	 * @param seen2
	 */
	public Post(String ownerOfTimeline, String poster, String content,
			int nLikes,String up, String postPrivacy, String postFeelings, int ID, int seen2) {
		super();
		OwnerOfTimeline = ownerOfTimeline;
		Post.poster = poster;
		this.content = content;
		this.likeID = nLikes;
		iD=ID;
		userOrPage=up;
		this.feeling=postFeelings;
		this.privacy=postPrivacy;
		seen = seen2;
	}

	/**
	 * getter seen
	 * @return
	 */
	public int getSeen() {
		return seen;
	}

	/**
	 * setter seen
	 * @param seen
	 */
	public void setSeen(int seen) {
		this.seen = seen;
	}

	/**
	 * getter ID
	 * @return
	 */
	public int getiD() {
		return iD;
	}

	/**
	 * getter ID
	 * @param iD
	 */
	public void setiD(int iD) {
		Post.iD = iD;
	}

	/**
	 * getter ownerOfTimeLine
	 * @return
	 */
	public String getOwnerOfTimeline() {
		return OwnerOfTimeline;
	}

	/**
	 * setter ownerOfTimeline
	 * @param ownerOfTimeline
	 */
	public void setOwnerOfTimeline(String ownerOfTimeline) {
		OwnerOfTimeline = ownerOfTimeline;
	}

	/**
	 * getter poster
	 * @return
	 */
	public String getPoster() {
		return poster;
	}

	/**
	 * setter poster
	 * @param poster
	 */
	public void setPoster(String poster) {
		Post.poster = poster;
	}
	
	/**
	 * getter Content
	 * @return
	 */

	public String getContent() {
		return content;
	}
	
	/**
	 * setter Content
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * getter nLikes
	 * @return
	 */
	public int getnLikes() {
		return likeID;
	}
	
	/**
	 * setter nLikes
	 * @param nLikes
	 */
	public void setnLikes(int nLikes) {
		this.likeID = nLikes;
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
		post.setProperty("nLikes", likeID);
		post.setProperty("UserOrPage",userOrPage);
		post.setProperty("Privacy",privacy);
		post.setProperty("Feelings",feeling);
		post.setProperty("seen",0);
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
	
	/**
	 * getter Feeling
	 * @return
	 */
	public String getFeeling() {
		return feeling;
	}

	/**
	 * setter Feeling
	 * @param feeling
	 */
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
		post.setProperty("Owner", uName);
		post.setProperty("Poster", poster);
		post.setProperty("Content", content);
		post.setProperty("nLikes", likeID);
		post.setProperty("UserOrPage",userOrPage);
		post.setProperty("Privacy",privacy);
		post.setProperty("Feelings",feeling);
		post.setProperty("seen",0);
		datastore.put(post);
		return size;

	}
	/**
	 * this method search the post table and get the post with this ID
	 * @param ID
	 * 		The ID of the post
	 * @return
	 * 		object from post
	 */
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
		int seen = Integer.parseInt((postEntity.getProperty("seen")).toString());
		Post p = new Post(owner , poster , content , nlikes , userorpage, privacy, feeling, ID,seen);
		return p;
	}
}
