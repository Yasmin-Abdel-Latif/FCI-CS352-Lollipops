
/**
 * 
 * @author Nour Mohammed Srour Alwani
 * @version 1.0
 * @since 2014-02-12
 *
 */


package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class uTimeline {
	
	String profilePicture;
	String coverPhoto;
	ArrayList<Post> posts = new ArrayList<>();
	String ownerOfTimeline;
	
	public uTimeline() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public uTimeline(String profilePicture, String coverPhoto,
			ArrayList<Post> posts, String ownerOfTimeline) {
		super();
		this.profilePicture = profilePicture;
		this.coverPhoto = coverPhoto;
		this.posts = posts;
		this.ownerOfTimeline = ownerOfTimeline;
	}



	/**
	 * createPost method will create a new post and register it
	 * @param pContent
	 *                post content
	 * @param postFeelings, postPrivacy
	 */
	public void createPost(String pContent, String postPrivacy, String postFeelings)
	{
		Post newPost = new Post(UserController.userData.getName(), UserController.userData.getName(),pContent, 0,"u",postPrivacy,postFeelings);
		newPost.registerPost();
		this.posts.add(newPost);
	}
	
	
	/**
	 * getAllPosts method : will return all posts of a certain OWNER
	 * 
	 * @param name
	 *            user name
	 * @return posts
	 *              arraylist of all posts of that user
	 */
	public static ArrayList<Post> getAllPosts(String ownerName) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String postContent = null;
		String postOwner="";
		String postPoster="";
		String postFeelings="";
		String postPrivacy="";
		String postUserOrPage="";
		int nLikes=0;
		ArrayList<Post> posts = new ArrayList<Post>();

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Owner").toString().equals(ownerName))
			{
				postOwner=entity.getProperty("Owner").toString();
				postPoster=entity.getProperty("Poster").toString();
				postContent=entity.getProperty("Content").toString();
				nLikes=Integer.parseInt(entity.getProperty("nLikes").toString());
				postUserOrPage=entity.getProperty("UserOrPage").toString();
				postFeelings=entity.getProperty("Feelings").toString();
				postPrivacy=entity.getProperty("Privacy").toString();

				posts.add(new Post(postOwner, postPoster, postContent, nLikes, postUserOrPage, postPrivacy, postFeelings));
			}
		}
		return posts;
	}
	
	public static ArrayList<Post> getAllSharedPosts(String ownerName) {
		
		ArrayList<Post> posts = new ArrayList<Post>();
		DatastoreService datastore1 = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery1 = new Query("Share");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		for (Entity entity1 : pq1.asIterable()) 
		{
			if (entity1.getProperty("PostSharer").toString().equals(ownerName))
			{
				String postID = entity1.getProperty("PostID").toString();
				DatastoreService datastore2 = DatastoreServiceFactory
						.getDatastoreService();
				Query gaeQuery2 = new Query("Post");
				PreparedQuery pq2 = datastore2.prepare(gaeQuery2);
				for (Entity entity2 : pq2.asIterable()) 
				{
					if (entity2.getProperty("ID").toString().equals(postID))
					{
						String postOwner1 = entity2.getProperty("Owner").toString();
						String postPoster1 = entity2.getProperty("Poster").toString();
						String postContent1 = entity2.getProperty("Content").toString();
						int nLikes1 = Integer.parseInt(entity2.getProperty("nLikes").toString());
						String postUserOrPage1 = entity2.getProperty("UserOrPage").toString();
						String postFeelings1 = entity2.getProperty("Feelings").toString();
						String postPrivacy1 = entity2.getProperty("Privacy").toString();
						posts.add(new Post(postOwner1, postPoster1, postContent1, nLikes1, postUserOrPage1, postPrivacy1, postFeelings1));
					}
				}
			}
		}
		return posts;
	}
	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

}
