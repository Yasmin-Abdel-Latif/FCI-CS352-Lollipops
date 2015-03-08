package com.FCI.SWE.Models;


import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;


/**
 * <h1>Add friend class</h1>
 * <p>
 * This class will act as a model to accept friend request from other users
 * </p>
 *
 * @author Mariam Fouad
 * @version 1.0
 * @since 27 - 2- 2015
 */
public class Friend {
	
	private static String rName; // reciever
	private boolean state;
	private static String sName;  //sender

	public Friend(String friend, String name) 
	{
		Friend.rName = friend ;
		Friend.sName = name;
		this.state = false;
	}
	
	public String getFriend() 
	{
		return rName;
	}
	public void setFriend(String friend) 
	{
		Friend.rName = friend;
	}
	
	public void setState(boolean state) 
	{
		this.state = state;
	}

	
	/**
	 * 
	 * This static method will form UserEntity class using json format contains
	 * user data
	 * 
	 * @param json
	 *            String in json format contains user data
	 * @return Constructed user entity
	 */
	public static Friend getrequest (String json) 
	{
		JSONParser parser = new JSONParser();
		try 
		{
			JSONObject fobject = (JSONObject) parser.parse(json);
			return new Friend(fobject.get("friend").toString(),fobject.get("name").toString());
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return null;

	}
	
	
	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean sendRequest()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("friend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if(entity.getProperty("name").toString().equals(sName) && entity.getProperty("friend").toString().equals(Friend.rName))
				return false;
			
			//if already the other user is a friend or sent a request
			if (entity.getProperty("name").toString().equals(rName) && entity.getProperty("friend").toString().equals(Friend.sName))
				return false;
		}
		//check now if there is a user with this name 
		if (UserEntity.getUserWithName(rName)== null)
			return false;
		Key UserKey = KeyFactory.createKey("friend", ((sName.length()*3) + (rName.length()*2))*2);
	    Entity friends = new Entity("friend", UserKey);

	    friends.setProperty("ID", ((sName.length()*3) + (rName.length()*2))*2);
	    friends.setProperty("name", sName);
	    friends.setProperty("friend", Friend.rName);
	    friends.setProperty("state", false);
		datastore.put(friends);
		return true;
	}
	
	public static ArrayList<String> getUserFriend(String name) 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("friend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<String> friends = new ArrayList<String>();
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("friend").toString().equals(name) && !entity.getProperty("state").toString().equals("true"))
			{
				friends.add(entity.getProperty("name").toString());
			}
		}
		return friends;
	}
	
	@SuppressWarnings("deprecation")
	public String Accept(String name, String friend) 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("friend");
		query.addFilter("name", FilterOperator.EQUAL, friend);
		query.addFilter("friend", FilterOperator.EQUAL, name);
		//query.addFilter("ID", FilterOperator.EQUAL, ((friend.length()*3) + (name.length()*2))*2);
		PreparedQuery pq = datastore.prepare(query);
		Entity friends = pq.asSingleEntity();
		friends.setProperty("state", this.state);
		datastore.put(friends);
		return "Done";
	}
}
