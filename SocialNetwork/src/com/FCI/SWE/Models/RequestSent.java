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
	public final static String TYPE = "2";
	public boolean seen;
	
	/**
	 * Action function to add new notification into the data store
	 */
	public void addNotification()
	{
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
		messageNotify.setProperty("Type", TYPE);
		messageNotify.setProperty("Sender", Friend.sName);
		messageNotify.setProperty("Name", Friend.rName);
		messageNotify.setProperty("Msg", "You have friend request from "+Friend.sName);
		messageNotify.setProperty("Seen", false);
		datastore1.put(messageNotify);
	}
	
	/**
	 * Action function enters the data store and get all related records to the pressed 
	 * notification which in this case, the history of sent requests
	 * @param ID the notification ID
	 * @return arraylist holds history of sent requests
	 */
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
	
	/**
	 * Action function enters the data store and get all unseen records of the sent request 
	 * notifications
	 * @return arraylist holds unseen notifications of type sentrequest
	 */
	public static ArrayList<String> myUnSeenNotifications()
	{
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
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Sent You a friend request");
			}
		}
		return friends;
	}
	
	/**
	 * Action function enters the data store and get all seen records of the sent requests 
	 * notifications
	 * @return arraylist holds seen notifications of type sentrequest
	 */
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
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": Sent You a friend request");
			}
		}
		return friends;
	}
	
	/**
	 * Action function enters the data store and change the notifications of 
	 * this user to seen notification
	 */
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
	
	public String toString()
	{
		return msg;
	}
}
