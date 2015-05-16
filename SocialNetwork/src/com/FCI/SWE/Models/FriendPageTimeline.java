/**
 * 
 * @author Nour Mohammed Srour Alwani
 * @version 1.0
 * @since 2014-02-12
 *
 */


package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
/**
 * class FriendPageTimeline
 * @author Nur Alwani
 * @date April 20, 2015
 * @version 1.0
 *
 */
public class FriendPageTimeline extends Timeline{
	
	public static Comparator<Post> postsSortedByID = new Comparator<Post>() 
			{
				public int compare(Post post1, Post post2) 
				{
				   return post1.getiD()-post2.getiD(); // sort in an ascending order of id. (Display might be descending according to post type -- handled in jsp)
				}
			};
	
	ArrayList<Post> posts = new ArrayList<>();
	/**
	 * register post in datastore and return the post ID
	 * @param newPost
	 * @return postID
	 */
	public int addPost (Post newPost)
	{
		int postID = newPost.registerPostOnFP(UserController.friendName);
		newPost.setiD(postID);
		this.posts.add(newPost);
		return postID;
	}
	
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
	 * getAllPosts method : will return all posts of a certain OWNER
	 * 
	 * @param name
	 *            user name
	 * @return posts
	 *              arraylist of all posts of that OWNER
	 */
	public static ArrayList<Post> getAllPosts(String ownerName) 
	{
		ArrayList<Post> posts = new ArrayList<Post>();
		Privacy publicPost = new Public(); //always display public posts of the owner of the timeline am visiting OR posts with no privacy set => friend's post on that timline
		publicPost.fillAccToPrivacy(posts,ownerName,UserController.userData.getName());
		
		ArrayList<String>ownerFriends=Friend.getUserFriends(ownerName); //allow displaying private posts only if am a friend to the timeline's owner
		boolean friendOfOwner=false;
		if (ownerFriends.contains(UserController.userData.getName()))
		{
			friendOfOwner=true;
			Privacy privatePost = new Private(); 
			privatePost.fillAccToPrivacy(posts,ownerName,UserController.userData.getName());
		}
		
		if (friendOfOwner) //if am a friend of the owner => check for custom posts am included in & display them
		{
			Privacy custom = new Custom();
			custom.fillAccToPrivacy(posts, ownerName,UserController.userData.getName());
		}
		
		Collections.sort(posts, postsSortedByID);
		return posts;
	}
	
	/**
	 * this  method all the post that the user shared
	 * @param ownerName
	 * 		the owner of the timeline
	 * @return
	 * 		ArrayList of posts
	 * 		
	 */
	public static ArrayList<Post> getAllSharedPosts(String ownerName) 
	{
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
					if (entity2.getProperty("ID").toString().equals(postID) && (!entity2.getProperty("Privacy").toString().equals("Private")) && (!entity2.getProperty("Privacy").toString().equals("Custom")))
					{
						String postOwner1 = entity2.getProperty("Owner").toString();
						String postPoster1 = entity2.getProperty("Poster").toString();
						String postContent1 = entity2.getProperty("Content").toString();
						int nLikes1 = Integer.parseInt(entity2.getProperty("nLikes").toString());
						String postUserOrPage1 = entity2.getProperty("UserOrPage").toString();
						String postFeelings1 = entity2.getProperty("Feelings").toString();
						String postPrivacy1 = entity2.getProperty("Privacy").toString();
						int ID = Integer.parseInt(entity2.getProperty("ID").toString());
						int seen = Integer.parseInt(entity2.getProperty("seen").toString());
						Post post = new Post(postOwner1, postPoster1, postContent1, nLikes1, postUserOrPage1, postPrivacy1, postFeelings1, ID,seen);
						post.setiD(ID);
						posts.add(post);
					}
					else if (entity2.getProperty("ID").toString().equals(postID) && (entity2.getProperty("Privacy").toString().equals("Private")))
					{
						ArrayList<String>ownerFriends=Friend.getUserFriends(ownerName);
						if (ownerFriends.contains(UserController.userData.getName())) //if am a friend of the owner => check for custom posts am included in & display them
						{
							String postOwner1 = entity2.getProperty("Owner").toString();
							String postPoster1 = entity2.getProperty("Poster").toString();
							String postContent1 = entity2.getProperty("Content").toString();
							int nLikes1 = Integer.parseInt(entity2.getProperty("nLikes").toString());
							String postUserOrPage1 = entity2.getProperty("UserOrPage").toString();
							String postFeelings1 = entity2.getProperty("Feelings").toString();
							String postPrivacy1 = entity2.getProperty("Privacy").toString();
							int ID = Integer.parseInt(entity2.getProperty("ID").toString());
							int seen = Integer.parseInt(entity2.getProperty("seen").toString());
							Post post = new Post(postOwner1, postPoster1, postContent1, nLikes1, postUserOrPage1, postPrivacy1, postFeelings1, ID,seen);
							post.setiD(ID);
							posts.add(post);
						}
					}
					else if (entity2.getProperty("ID").toString().equals(postID) && (entity2.getProperty("Privacy").toString().equals("Custom")))
					{
						ArrayList<String>ownerFriends=Friend.getUserFriends(ownerName);
						if (ownerFriends.contains(UserController.userData.getName())) //if am a friend of the owner => check for custom posts am included in & display them
						{
							if(entity2.getProperty("Owner").toString().equals(UserController.userData.getName()))
							{
								String postOwner1 = entity2.getProperty("Owner").toString();
								String postPoster1 = entity2.getProperty("Poster").toString();
								String postContent1 = entity2.getProperty("Content").toString();
								int nLikes1 = Integer.parseInt(entity2.getProperty("nLikes").toString());
								String postUserOrPage1 = entity2.getProperty("UserOrPage").toString();
								String postFeelings1 = entity2.getProperty("Feelings").toString();
								String postPrivacy1 = entity2.getProperty("Privacy").toString();
								int ID = Integer.parseInt(entity2.getProperty("ID").toString());
								int seen = Integer.parseInt(entity2.getProperty("seen").toString());
								Post post = new Post(postOwner1, postPoster1, postContent1, nLikes1, postUserOrPage1, postPrivacy1, postFeelings1, ID,seen);
								post.setiD(ID);
								posts.add(post);
							}
							else
							{
								for (int j = 0 ; j < Integer.parseInt(entity2.getProperty("CustomListNumber").toString()) ; j++)
								{
									if (entity2.getProperty(("FriendCus# " + j)).toString().equals(UserController.userData.getName())) 
									{
										String postOwner1 = entity2.getProperty("Owner").toString();
										String postPoster1 = entity2.getProperty("Poster").toString();
										String postContent1 = entity2.getProperty("Content").toString();
										int nLikes1 = Integer.parseInt(entity2.getProperty("nLikes").toString());
										String postUserOrPage1 = entity2.getProperty("UserOrPage").toString();
										String postFeelings1 = entity2.getProperty("Feelings").toString();
										String postPrivacy1 = entity2.getProperty("Privacy").toString();
										int ID = Integer.parseInt(entity2.getProperty("ID").toString());
										int seen = Integer.parseInt(entity2.getProperty("seen").toString());
										Post post = new Post(postOwner1, postPoster1, postContent1, nLikes1, postUserOrPage1, postPrivacy1, postFeelings1, ID,seen);
										post.setiD(ID);
										posts.add(post);
									}
								}
							}
						}
					}
				}
			}
		}
		Collections.sort(posts, postsSortedByID);
		return posts;
	}
	
	/**
	 * setter for posts
	 * @param posts
	 */
	public void setPosts(ArrayList<Post> posts) {
		this.posts = new ArrayList<Post>();
		for(int i = 0 ; i < posts.size() ; i++)
		{
			this.posts.add(posts.get(i));
		}
	}
}
