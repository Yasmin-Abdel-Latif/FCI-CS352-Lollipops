package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
/**
 * class Public
 * @author Nur Alwani
 * @date April 20, 2015
 * @version 1.0
 *
 */
public class Public implements Privacy{

	/**
	 * 
	 * @param posts 
	 *              arraylist of all posts that will be displayed to the timeline viewer
	 * @param ownerName
	 *                  name of the owner of the timeline
	 * @param viewerName
	 *                  name of the viewer of the timeline
	 */
	@SuppressWarnings("unused")
	@Override
	public ArrayList<Post> fillAccToPrivacy(ArrayList<Post> posts, String ownerName,String viewerName) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("Post");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String postContent =null;
		String postOwner="";
		String postPoster="";
		String postFeelings="";
		String postPrivacy="";
		String postUserOrPage="";
		int nLikes=0;
		int ID = 0;
		for (Entity entity : pq.asIterable()) {
			if ((entity.getProperty("Owner").toString().equals(ownerName)) && (!entity.getProperty("Privacy").toString().equals("Private")) && (!entity.getProperty("Privacy").toString().equals("Custom")) && (entity.getProperty("UserOrPage").toString().equals("u")))
			{
				postOwner=entity.getProperty("Owner").toString();
				postPoster=entity.getProperty("Poster").toString();
				postContent=entity.getProperty("Content").toString();
				nLikes=Integer.parseInt(entity.getProperty("nLikes").toString());
				postUserOrPage=entity.getProperty("UserOrPage").toString();
				postPrivacy=entity.getProperty("Privacy").toString();
				postFeelings=entity.getProperty("Feelings").toString();

				System.out.println("Poster: " + postPoster);
				ID = Integer.parseInt(entity.getProperty("ID").toString());
				int seen = Integer.parseInt(entity.getProperty("seen").toString());
				Post post = new Post(postOwner, postPoster, postContent, nLikes, "u", postPrivacy, postFeelings, ID,seen);
				posts.add(post);
			}
		}
		return posts;
	}
}
