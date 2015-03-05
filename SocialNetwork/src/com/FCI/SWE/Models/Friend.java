package com.FCI.SWE.Models;


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
	
	private String rName; // reciever
	@SuppressWarnings("unused")
	private boolean state;
	private String sName;  //sender

	public String getFriend() {
		return rName;
	}
	public void setFriend(String friend) {
		this.rName = friend;
	}


	public Friend(String friend) {
		// TODO Auto-generated constructor stub
		this.rName = friend ;
		this.sName = UserEntity.myName;
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

		System.out.println("hhhh "+json);
		
		JSONParser parser = new JSONParser();
		System.out.println("after "+json);
		try 
		{
			JSONObject fobject = (JSONObject) parser.parse(json);
			return new Friend(fobject.get("friend").toString());
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
			if(entity.getProperty("name").toString().equals(sName) && entity.getProperty("friend").toString().equals(this.rName))
			{
				return false;
			}
		}
		Key UserKey = KeyFactory.createKey("friend", (sName.length()*2) + (rName.length()*2));
	    Entity friends = new Entity("friend", UserKey);

	    friends.setProperty("ID", (sName.length()*2) + (rName.length()*2));
	    friends.setProperty("name", sName);
	    friends.setProperty("friend", this.rName);
	    friends.setProperty("state", false);
		datastore.put(friends);
		return true;
	}
}
