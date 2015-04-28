package com.FCI.SWE.Models;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Custom implements Privacy {
	@SuppressWarnings("unused")
	@Override
	/**
	 * 
	 * @param posts 
	 *              arraylist of all posts that will be displayed to the timeline viewer
	 * @param ownerName
	 *                  name of the owner of the timeline
	 * @param viewerName
	 *                  name of the viewer of the timeline
	 */
	public void fillAccToPrivacy(ArrayList<Post> posts, String ownerName, String viewerName) {
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
		int i=0;
		int ID = 0;
		for (Entity entity : pq.asIterable()) {
			 i=0;
			if ((entity.getProperty("Owner").toString().equals(ownerName))
					&& (entity.getProperty("Privacy").toString()
							.equals("Custom"))) 
			{
				for (int j=0;j<Integer.parseInt(entity.getProperty("CustomListNumber").toString());j++)
				{
					if (entity.getProperty(("FriendCus# " + i)).toString().equals(viewerName) && (entity.getProperty("UserOrPage").toString().equals("u"))) 
					{
						postOwner = entity.getProperty("Owner").toString();
						postPoster = entity.getProperty("Poster").toString();
						postContent = entity.getProperty("Content").toString();
						nLikes = Integer.parseInt(entity.getProperty("nLikes").toString());
						postUserOrPage = entity.getProperty("UserOrPage").toString();
						postPrivacy = entity.getProperty("Privacy").toString();
						postFeelings = entity.getProperty("Feelings").toString();
						ID = Integer.parseInt(entity.getProperty("ID").toString());
						int seen = Integer.parseInt(entity.getProperty("seen").toString());
						Post post = new Post(postOwner, postPoster, postContent, nLikes, "u", postPrivacy, postFeelings, ID,seen);
						posts.add(post);
					}
					i++;
				}
			}
		}
	}
}