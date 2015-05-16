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

/**
 * <h1>MsgNotify class</h1>
 * <p>
 * This class will act as a concrete command subclass of the interface
 * Notification
 * </p>
 *
 * @author Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
 *         Ahmed, Huda Mohammed
 * @version 1.0
 * @since 9 - 4 - 2015
 */
public class MsgNotify implements Notification {

	public String rName;
	public String sName;
	public String msg;
	public final static String TYPE = "3";
	public boolean seen;

	/**
	 * Action function to add new notification into the data store
	 */
	public void addNotification() {
		rName = Messages.reciever;
		sName = Messages.sender;
		msg = Messages.msg;
		seen = false;

		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("Notifications");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		List<Entity> list1 = pq1
				.asList(FetchOptions.Builder.withDefaults());
		Entity messageNotify = new Entity("Notifications", list1.size() + 1);

		messageNotify.setProperty("ID", list1.size() + 1);
		messageNotify.setProperty("Type", TYPE);
		messageNotify.setProperty("Sender", Messages.sender);
		messageNotify.setProperty("Name", Messages.reciever);
		messageNotify.setProperty("Msg", Messages.msg);
		messageNotify.setProperty("Seen", false);
		datastore1.put(messageNotify);
	}

	/**
	 * Action function enters the data store and get all related records to the pressed 
	 * notification which in this case, the conversation between the user and his friend
	 * @param ID the notification ID
	 * @return arraylist holds history of conversation messages
	 */
	public ArrayList<String> pressedNotification(String ID) 
	{
		ID = ID.trim();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String sender = "";
		String name = "";
		for(Entity entity : pq.asIterable())
		{
			if(entity.getProperty("ID").toString().equals(ID))
			{
				sender = entity.getProperty("Sender").toString();
				name = entity.getProperty("Name").toString();
			}
		}
		ArrayList<String> messages = new ArrayList<String>();
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("message");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		
		for (Entity entity1 : pq1.asIterable()) 
		{
			if ((entity1.getProperty("Sender").toString().equals(sender)
					&& entity1.getProperty("Reciever").toString().equals(name))
					|| (entity1.getProperty("Sender").toString().equals(name)
							&& entity1.getProperty("Reciever").toString().equals(sender))) 
			{
				String conversation = entity1.getProperty("Sender") + ": " + entity1.getProperty("Msg").toString();
				messages.add(conversation);
			}
		}
		return messages;
	}

	/**
	 * Action function enters the data store and get all unseen records of the chat message 
	 * notifications
	 * @return arraylist holds unseen notifications of type chat message
	 */
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
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": sent you a message:-" + "<br>" + entity.getProperty("Msg").toString());
			}
		}
		return friends;
	}
	
	/**
	 * Action function enters the data store and get all seen records of the chat message 
	 * notifications
	 * @return arraylist holds seen notifications of type chat message
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
				friends.add(entity.getProperty("ID").toString() + "-" + entity.getProperty("Type").toString() + " " + entity.getProperty("Sender").toString() + ": sent you a message:-" + "<br>" + entity.getProperty("Msg").toString());
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
	
	public String toString() {
		String print = sName + " sent you this message: " + "<br>" + msg;
		return print;
	}
}
