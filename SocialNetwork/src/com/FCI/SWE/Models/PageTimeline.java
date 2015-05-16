package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * this class has all the information to view a page timeline
 * @author mariam
 *
 */
public class PageTimeline extends Timeline{

	public static ArrayList <Post> posts;
	public Page page ;
	public Boolean like ; //if the current user like the page
	
	/**
	 * this method to seen every post in a friend timeline
	 */
	@SuppressWarnings("deprecation")
	public void seen ()
	{
		for (int i=0;i<posts.size();i++)
		{
			int seen = posts.get(i).getSeen()+1;
			posts.get(i).setSeen(seen);
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Query query = new Query("Post");
			query.addFilter("ID", FilterOperator.EQUAL, posts.get(i).getiD());
			PreparedQuery pq = datastore.prepare(query);
			Entity postEntity = pq.asSingleEntity();
			postEntity.setProperty("seen", seen);
			datastore.put(postEntity);
			
		}
	}
	/**
	 * 
	 * @param pContent
	 * 			the content of the post
	 * @return 
	 * 
	 */
	public int createPost(Post newPost)
	{
		int postID = newPost.registerPost();
		PageTimeline.posts.add(newPost);
		return postID;
	}
	
	/**
	 * 
	 * @param pageName
	 * 			the name of the page to search for
	 * @return
	 * 		all the post of this page
	 */
	public static ArrayList<Post> getAllPosts(String pageName) {
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
		int seen =0;
		posts = new ArrayList<Post>();

		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Owner").toString().equals(pageName)&& entity.getProperty("UserOrPage").toString().equals("p"))
			{
				postOwner=entity.getProperty("Owner").toString();
				postPoster=entity.getProperty("Poster").toString();
				postContent=entity.getProperty("Content").toString();
				nLikes=Integer.parseInt(entity.getProperty("nLikes").toString());
				postUserOrPage=entity.getProperty("UserOrPage").toString();
				postFeelings = entity.getProperty("Feelings").toString();
				postPrivacy = entity.getProperty("Privacy").toString();
				ID = Integer.parseInt(entity.getProperty("ID").toString());
				seen = Integer.parseInt(entity.getProperty("seen").toString());
				Post post = new Post(postOwner, postPoster, postContent, nLikes,postUserOrPage,postPrivacy,postFeelings, ID,seen);
				post.setiD(ID);
				posts.add(post);
			}
		}
		return posts;
	}
}
