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

public class GroupMsgNotify implements Notification {

	public String rName;
	public String sName;
	public String msg;
	public final static String TYPE = "4";
	public boolean seen;

	/**
	 * Execution Function
	 */
	public void addNotification() {
		rName = Messages.reciever;
		sName = Messages.sender;
		msg = Messages.msg;
		seen = false;

		ArrayList<String> recieve = GroupMsg.getAllRecievers(Messages.reciever);
		recieve.add(GroupMsg.getCreator(Messages.reciever));
		for (int i = 0; i < recieve.size(); i++) 
		{
			DatastoreService datastore1 = DatastoreServiceFactory
					.getDatastoreService();
			Query gaeQuery1 = new Query("Notifications");
			PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
			List<Entity> list1 = pq1.asList(FetchOptions.Builder
					.withDefaults());
			Entity messageNotify = new Entity("Notifications",
					list1.size() + 1);

			if(!recieve.get(i).equals(Messages.sender))
			{
				messageNotify.setProperty("ID", list1.size() + 1);
				messageNotify.setProperty("Type", TYPE);
				messageNotify.setProperty("Sender", Messages.sender);
				messageNotify.setProperty("Name", recieve.get(i));
				messageNotify.setProperty("Msg", Messages.msg);
				messageNotify.setProperty("Seen", false);
				messageNotify.setProperty("Conversation", rName);
				datastore1.put(messageNotify);
			}
		}
	}

	public ArrayList<String> pressedNotification(String ID) 
	{
		ID = ID.trim();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Notifications");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		String cName = "";
		for(Entity entity : pq.asIterable())
		{
			if(entity.getProperty("ID").toString().equals(ID))
			{
				cName = entity.getProperty("Conversation").toString();
			}
		}
		ArrayList<String> messages = new ArrayList<String>();
		DatastoreService datastore1 = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery1 = new Query("message");
		PreparedQuery pq1 = datastore1.prepare(gaeQuery1);
		
		for (Entity entity1 : pq1.asIterable()) 
		{
			if (entity1.getProperty("Reciever").toString().equals(cName))
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
			if (entity.getProperty("Type").toString().equals(TYPE)
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
			if (entity.getProperty("Type").toString().equals(TYPE)
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
			if (notifications.getProperty("Type").toString().equals(TYPE)
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
				notifications2.setProperty("Type", TYPE);
				notifications2.setProperty("Sender", sender);
				notifications2.setProperty("Name", UserController.userData.getName());
				notifications2.setProperty("Msg", msg);
				notifications2.setProperty("Seen", true);
				notifications2.setProperty("Conversation", cName);
				datastore2.put(notifications2);
			}
		}
	}
	
	public String toString() {
		String print = sName + " sent this message to conversation (" + rName
					+ "): " + "<br>" + msg;
		return print;
	}

}
