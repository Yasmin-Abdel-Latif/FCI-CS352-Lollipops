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
	 */
	public void createPost(String pContent)
	{
		Post newPost = new Post(UserController.userData.getName(), UserController.userData.getName(),pContent, 0,"u");
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
		Query gaeQuery = new Query("post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String postContent =null;
		String postOwner="";
		String postPoster="";
		int nLikes=0;
		ArrayList<Post> posts = new ArrayList<Post>();

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Owner").toString().equals(ownerName))
			{
				postOwner=entity.getProperty("Owner").toString();
				postPoster=entity.getProperty("Poster").toString();
				postContent=entity.getProperty("Content").toString();
				nLikes=Integer.parseInt(entity.getProperty("nLikes").toString());
				
				posts.add(new Post(postOwner, postPoster, postContent, nLikes,"u"));
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
