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

public class ShareNotify implements Notification {

	public String rName;
	public String sName;
	public String msg;
	public int type;
	public boolean seen;

	/**
	 * Execution Function
	 */
	public void addNotification() {
		type = 6;
		rName = Share.postOwner;
		sName = Share.postSharer;
		msg = Share.postSharer + " Shared Your Post";
		seen = false;
		
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Notifications");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		List<Entity> list1 = pq1.asList(FetchOptions.Builder.withDefaults());
		Entity messageNotify = new Entity("Notifications", list1.size()+1);

		messageNotify.setProperty("ID", list1.size() + 1);
		messageNotify.setProperty("Type", 6);
		messageNotify.setProperty("Sender", Share.postSharer);
		messageNotify.setProperty("Name", Share.postOwner);
		messageNotify.setProperty("Msg", Share.postSharer + " Shared Your Post");
		messageNotify.setProperty("Seen", false);
		messageNotify.setProperty("ShareID", Share.ID);
		datastore1.put(messageNotify);
	}

	public ArrayList<String> pressedNotification(String ID) {
		ID = ID.trim();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String shareID = "";
		for(Entity entity : pq.asIterable())
		{
			if(entity.getProperty("ID").toString().equals(ID))
			{
				shareID = entity.getProperty("ShareID").toString();
			}
		}
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Share");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		String postID = "";
		String postSharer = "";
		String postOwner = "";
		for (Entity entity1 : pq1.asIterable()) 
		{
			if (entity1.getProperty("ID").toString().equals(shareID)) 
			{
				postID = entity1.getProperty("PostID").toString();
				postSharer = entity1.getProperty("PostSharer").toString();
				postOwner = entity1.getProperty("PostOwner").toString();
			}
		}
		ArrayList<String> messages = new ArrayList<String>();
		DatastoreService datastore2 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery2 = new Query("Post");
		PreparedQuery pq2 = datastore2.prepare(gaeQuery2);
		for (Entity entity1 : pq2.asIterable()) 
		{
			if (entity1.getProperty("ID").toString().equals(postID)) 
			{
				String content = entity1.getProperty("Content").toString();
				String feeling = entity1.getProperty("Feelings").toString();
				String nLikes = entity1.getProperty("nLikes").toString();
				String conversation = "<FONT COLOR='#65267a' SIZE='4'><B>"+ postSharer + " Shared " + postOwner +"'s Post : </B>" + content + "</FONT>";
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
			if (entity.getProperty("Type").toString().equals("6")
					&& entity.getProperty("Seen").toString().equals("false")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Shared Your Post");
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
			if (entity.getProperty("Type").toString().equals("6")
					&& entity.getProperty("Seen").toString().equals("true")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Shared Your Post");
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
			if (notifications.getProperty("Type").toString().equals("6")
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
				notifications2.setProperty("Type", 6);
				notifications2.setProperty("Sender", sender);
				notifications2.setProperty("Name", UserController.userData.getName());
				notifications2.setProperty("Msg", msg);
				notifications2.setProperty("Seen", true);
				datastore2.put(notifications2);
			}
		}
	}

}
