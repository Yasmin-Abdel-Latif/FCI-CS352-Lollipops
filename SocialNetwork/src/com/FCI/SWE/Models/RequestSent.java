package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.Friend;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/** <h1>RequestSent class</h1>
* <p>
* This class will act as a concrete command subclass of the interface Notification
* </p>
*
* @author  Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
*         Ahmed, Huda Mohammed
* @version 1.0
* @since 9 - 4 - 2015
*/
public class RequestSent implements Notification{
	
	public String rName;
	public String sName;
	public String msg;
	public int type;
	public boolean seen;
	public void addNotification()
	{

		type = 2;
		rName = Friend.rName;
		sName = Friend.sName;
		msg = "You have friend request from "+Friend.sName;
		seen = false;
		
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Notifications");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		List<Entity> list1 = pq1.asList(FetchOptions.Builder.withDefaults());
		Entity messageNotify = new Entity("Notifications", list1.size()+1);

		messageNotify.setProperty("ID", list1.size() + 1);
		messageNotify.setProperty("Type", 2);
		messageNotify.setProperty("Sender", Friend.sName);
		messageNotify.setProperty("Name", Friend.rName);
		messageNotify.setProperty("Msg", "You have friend request from "+Friend.sName);
		messageNotify.setProperty("Seen", false);
		datastore1.put(messageNotify);
	}
	
	public ArrayList<String> pressedNotification(String ID)
	{
		ID = ID.trim();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String name = "";
		for(Entity entity : pq.asIterable())
		{
			if(entity.getProperty("ID").toString().equals(ID))
			{
				name = entity.getProperty("Name").toString();
			}
		}
		ArrayList<String> messages = new ArrayList<String>();
		messages = Friend.getUserFriendRequests(name);
		return messages;
	}
	public static ArrayList<String> myUnSeenNotifications()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<String> friends = new ArrayList<String>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Type").toString().equals("2")
					&& entity.getProperty("Seen").toString().equals("false")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName()))
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Sent You a friend request");
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
			if (entity.getProperty("Type").toString().equals("2")
					&& entity.getProperty("Seen").toString().equals("true")
					&& entity.getProperty("Name").toString().equals(UserController.userData.getName()))
			{
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Sent You a friend request");
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
			if (notifications.getProperty("Type").toString().equals("2")
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
				notifications2.setProperty("Type", 2);
				notifications2.setProperty("Sender", sender);
				notifications2.setProperty("Name", UserController.userData.getName());
				notifications2.setProperty("Msg", msg);
				notifications2.setProperty("Seen", true);
				datastore2.put(notifications2);
			}
		}
	}
	
	public String toString()
	{
		return msg;
	}
}