package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class fpTimeline {
	
	String profilePicture;
	String coverPhoto;
	ArrayList<Post> posts = new ArrayList<>();
	
	public void addPost (Post newPost)
	{
		newPost.registerPostOnFP(UserController.fpName);
		this.posts.add(newPost);
	}
	
	/**
	 * getAllPosts method : will return all posts of a certain OWNER
	 * 
	 * @param name
	 *            user name
	 * @return posts
	 *              arraylist of all posts of that OWNER
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
//				postUserOrPage=entity.getProperty("UserOrPage").toString();
				nLikes=Integer.parseInt(entity.getProperty("nLikes").toString());
				
				posts.add(new Post(postOwner, postPoster, postContent, nLikes,"u"));
			}
		}
		return posts;
	}
	
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}


}
