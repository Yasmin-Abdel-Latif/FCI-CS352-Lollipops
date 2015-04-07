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

public class GroupMsg extends Messages{
	
	static ArrayList<String> recievers;

	public GroupMsg(String cName, String sender, String msg) 
	{
		super(cName, sender, msg);
		type = true;
	}

	public Boolean sendMessage()
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("message");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		Entity message = new Entity("message", list.size()+1);

		message.setProperty("Type", type);
		message.setProperty("Sender", sender);
		message.setProperty("Reciever", reciever);
		message.setProperty("Msg", msg);
		message.setProperty("Seen", false);
		datastore.put(message);
		return true;
	}
	
	public static GroupMsg getConversationWithName(String cName) 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("GroupMessages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("ID").toString().equals(cName)) 
			{
				GroupMsg returnedConversation = new GroupMsg(entity.getProperty("ID").toString(), entity.getProperty("Creator").toString(), "");
				return returnedConversation;
			}
		}

		return null;
	}
	
	public Boolean saveConversation(String friendsNames)
	{
		recievers = new ArrayList<String>();
		String[] FriendsNames = friendsNames.split(";");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Entity employee = new Entity("GroupMessages", reciever);
	    
		employee.setProperty("ID", reciever);
		employee.setProperty("Creator", UserController.userData.getName());
		employee.setProperty("nRecievers", FriendsNames.length);
		
		String r = "Reciever";
		for(int i = 1 ; i <= FriendsNames.length ; i++)
		{
			recievers.add(FriendsNames[i-1]);
			employee.setProperty((String)(r+i), FriendsNames[i-1]);
		}
		datastore.put(employee);
		return true;
	}
	@SuppressWarnings("deprecation")
	public static ArrayList<String> getAllRecievers(String cName)
	{
		recievers = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("GroupMessages");
		query.addFilter("ID", FilterOperator.EQUAL, cName);
		PreparedQuery pq = datastore.prepare(query);
		Entity GMsg = pq.asSingleEntity();
		
		String r = "Reciever";
		for(int i = 1 ; i <= Integer.parseInt(GMsg.getProperty("nRecievers").toString()) ; i++)
		{
			String RecieverName = GMsg.getProperty((String)(r+i)).toString();
			recievers.add(RecieverName);
		}
		return recievers;
	}
	@SuppressWarnings("deprecation")
	public static String getCreator(String cName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("GroupMessages");
		query.addFilter("ID", FilterOperator.EQUAL, cName);
		PreparedQuery pq = datastore.prepare(query);
		Entity GMsg = pq.asSingleEntity();
		
		return GMsg.getProperty("Creator").toString();
	}
}
