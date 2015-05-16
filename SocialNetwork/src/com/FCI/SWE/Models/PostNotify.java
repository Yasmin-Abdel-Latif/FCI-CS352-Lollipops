package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PostNotify implements Notification {

	public String rName;
	public String sName;
	public String msg;
	public final static String TYPE = "5";
	public boolean seen;

	/**
	 * Execution Function
	 */
	public void addNotification() {
		rName = Post.OwnerOfTimeline;
		sName = Post.poster;
		msg = Post.poster + " Posted On Your Timeline";
		seen = false;
		
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Notifications");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		List<Entity> list1 = pq1.asList(FetchOptions.Builder.withDefaults());
		Entity messageNotify = new Entity("Notifications", list1.size()+1);

		messageNotify.setProperty("ID", list1.size() + 1);
		messageNotify.setProperty("Type", TYPE);
		messageNotify.setProperty("Sender", Post.poster);
		messageNotify.setProperty("Name", Post.OwnerOfTimeline);
		messageNotify.setProperty("Msg", Post.poster + " Posted On Your Timeline");
		messageNotify.setProperty("Seen", false);
		messageNotify.setProperty("PostID", Post.iD);
		datastore1.put(messageNotify);
	}

	public ArrayList<String> pressedNotification(String ID) {
		ID = ID.trim();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String postID = "";
		for(Entity entity : pq.asIterable())
		{
			if(entity.getProperty("ID").toString().equals(ID))
			{
				postID = entity.getProperty("PostID").toString();
			}
		}
		ArrayList<String> messages = new ArrayList<String>();
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Post");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		
		for (Entity entity1 : pq1.asIterable()) 
		{
			if (entity1.getProperty("ID").toString().equals(postID)) 
			{
				String poster = entity1.getProperty("Poster").toString();
				String content = entity1.getProperty("Content").toString();
				String feeling = entity1.getProperty("Feelings").toString();
				String nLikes = entity1.getProperty("nLikes").toString();
				String conversation = "<FONT COLOR='#65267a' SIZE='4'><B>"+ poster +" : </B>" + content + "</FONT>";
				if (!feeling.equals("notValid"))
     			{
					conversation += "<FONT COLOR='#808080' SIZE='3'><B> -Feeling: </B>" + feeling + "</FONT>";
     			}
				conversation += "<FONT COLOR='#808080' SIZE='3'><B> Likes: </B>" + nLikes + "</FONT>";
				messages.add(conversation);
			}
		}
		return messages;
	}

	public static ArrayList<String> myUnSeenNotifications() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<String> friends = new ArrayList<String>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Type").toString().equals(TYPE)
					&& entity.getProperty("Seen").toString().equals("false")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Posted On Your Timeline");
			}
		}
		return friends;
	}
	
	public static ArrayList<String> mySeenNotifications()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<String> friends = new ArrayList<String>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Type").toString().equals(TYPE)
					&& entity.getProperty("Seen").toString().equals("true")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Posted On Your Timeline");
			}
		}
		return friends;
	}

	public void seenNotification()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(query);
		for(Entity notifications : pq.asIterable())
		{
			if (notifications.getProperty("Type").toString().equals(TYPE)
					&& notifications.getProperty("Seen").toString().equals("false")
					&& notifications.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				
				String ID = notifications.getProperty("ID").toString();
				String msg = notifications.getProperty("Msg").toString();
				String sender = notifications.getProperty("Sender").toString();
				DatastoreService datastore2 = DatastoreServiceFactory
						.getDatastoreService();
				Entity notifications2 = new Entity("Notifications", Integer.parseInt(ID));

				notifications2.setProperty("ID", Integer.parseInt(ID));
				notifications2.setProperty("Type", TYPE);
				notifications2.setProperty("Sender", sender);
				notifications2.setProperty("Name", UserController.userData.getName());
				notifications2.setProperty("Msg", msg);
				notifications2.setProperty("Seen", true);
				datastore2.put(notifications2);
			}
		}
	}

}
