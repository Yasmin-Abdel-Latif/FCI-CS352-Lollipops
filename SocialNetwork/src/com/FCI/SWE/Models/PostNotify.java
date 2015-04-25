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
import com.google.appengine.api.datastore.Query.FilterOperator;

public class PostNotify implements Notification {

	public String rName;
	public String sName;
	public String msg;
	public int type;
	public boolean seen;

	/**
	 * Execution Function
	 */
	public void addNotification() {
		type = 5;
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
		messageNotify.setProperty("Type", 5);
		messageNotify.setProperty("Sender", Post.poster);
		messageNotify.setProperty("Name", Post.OwnerOfTimeline);
		messageNotify.setProperty("Msg", Post.poster + " Posted On Your Timeline");
		messageNotify.setProperty("Seen", false);
		datastore1.put(messageNotify);
	}

	@SuppressWarnings("deprecation")
	public ArrayList<String> pressedNotification(String ID) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		gaeQuery.addFilter("ID", FilterOperator.EQUAL, ID);
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Entity entity = pq.asSingleEntity();
		String sender = entity.getProperty("Sender").toString();
		String cName = entity.getProperty("Conversation").toString();
		ArrayList<String> messages = new ArrayList<String>();
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("message");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		
		for (Entity entity1 : pq1.asIterable()) 
		{
			if ((entity1.getProperty("Sender").toString().equals(sender)
					&& entity1.getProperty("Reciever").toString().equals(cName))) 
			{
				String conversation = entity1.getProperty("Sender") + ": " + entity1.getProperty("Msg").toString();
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
			if (entity.getProperty("Type").toString().equals("4")
					&& entity.getProperty("Seen").toString().equals("false")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": sent message to conversation " + entity.getProperty("Conversation").toString() + ": <br>" + entity.getProperty("Msg").toString());
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
			if (entity.getProperty("Type").toString().equals("4")
					&& entity.getProperty("Seen").toString().equals("true")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": sent message to conversation " + entity.getProperty("Conversation").toString() + ": <br>" + entity.getProperty("Msg").toString());
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
			if (notifications.getProperty("Type").toString().equals("4")
					&& notifications.getProperty("Seen").toString().equals("false")
					&& notifications.getProperty("Name").toString().equals(UserController.userData.getName())) 
			{
				
				String ID = notifications.getProperty("ID").toString();
				String msg = notifications.getProperty("Msg").toString();
				String sender = notifications.getProperty("Sender").toString();
				String cName = notifications.getProperty("Conversation").toString();
				DatastoreService datastore2 = DatastoreServiceFactory
						.getDatastoreService();
				Entity notifications2 = new Entity("Notifications", Integer.parseInt(ID));

				notifications2.setProperty("ID", Integer.parseInt(ID));
				notifications2.setProperty("Type", 4);
				notifications2.setProperty("Sender", sender);
				notifications2.setProperty("Name", UserController.userData.getName());
				notifications2.setProperty("Msg", msg);
				notifications2.setProperty("Seen", true);
				notifications2.setProperty("Conversation", cName);
				datastore2.put(notifications2);
			}
		}
	}

}
