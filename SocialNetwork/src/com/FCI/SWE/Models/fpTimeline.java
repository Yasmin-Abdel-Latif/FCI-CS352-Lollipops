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

public class fpTimeline {
	
	String profilePicture;
	String coverPhoto;
	ArrayList<Post> posts = new ArrayList<>();
	
	public int addPost (Post newPost)
	{
		int postID = newPost.registerPostOnFP(UserController.fpName);
		this.posts.add(newPost);
		return postID;
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
		Privacy pub = new Public(); //always display public posts of the owner of the timeline am visiting OR posts with no privacy set => friend's post on that timline
		pub.fillAccToPrivacy(posts,ownerName,UserController.userData.getName());
		
		ArrayList<String>ownerFriends=Friend.getUserFriends(ownerName); //allow displaying private posts only if am a friend to the timeline's owner
		boolean friendOfOwner=false;
		for (int i=0;i<ownerFriends.size();i++)
		{
			if (ownerFriends.get(i).equals(UserController.userData.getName()))
			{
				friendOfOwner=true;
				Privacy pri = new Private(); 
				pri.fillAccToPrivacy(posts,ownerName,UserController.userData.getName());
				break;
			}
		}
		
		if (friendOfOwner) //if am a friend of the owner => check for custom posts am included in & display them
		{
			Privacy cus = new Custom();
			cus.fillAccToPrivacy(posts, ownerName,UserController.userData.getName());
		}
		
		Collections.sort(posts, postsSortedByID);
		return posts;
	}
	public static Comparator<Post> postsSortedByID = new Comparator<Post>() 
	{
		public int compare(Post p1, Post p2) 
		{

		   int pp1 = p1.iD;
		   int pp2 = p2.iD;

		   return pp1-pp2; // sort in an ascending order of id. (Display might be descending according to post type -- handled in jsp)
		}
	};
	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}
}
