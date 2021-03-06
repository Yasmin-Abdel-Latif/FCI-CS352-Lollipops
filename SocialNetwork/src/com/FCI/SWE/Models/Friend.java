package com.FCI.SWE.Models;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Controller.UserController;
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
 * @author Mariam Fouad, Nour Mohammed Srour Alwani, Yasmine Abdel Latif, Salwa
 *         Ahmed, Huda Mohammed
 * @version 2.0
 * @since 27 - 2- 2015
 */
public class Friend {

	static String rName; // reciever
	boolean state;
	static String sName; // sender

	/**
	 * Constructor accepts friend class data
	 * 
	 * @param name
	 *            user name
	 * @param friend
	 *            friend name
	 */
	public Friend(String friend, String name) {
		Friend.rName = friend;
		Friend.sName = name;
		this.state = false;
	}

	/**
	 * getter for friend name
	 * 
	 * @return name
	 */
	public String getFriend() {
		return rName;
	}

	/**
	 * setter for friend name
	 * 
	 * @param friendname
	 */
	public void setFriend(String friend) {
		Friend.rName = friend;
	}

	/**
	 * setter for state
	 * 
	 * @param state
	 */

	public void setState(boolean state) {
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
	public static Friend getrequest(String json) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject fobject = (JSONObject) parser.parse(json);
			return new Friend(fobject.get("friend").toString(), fobject.get(
					"name").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean sendRequest() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("friend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(sName)
					&& entity.getProperty("friend").toString()
							.equals(Friend.rName) && entity.getProperty("state").toString().equals("true"))
			{
				UserController.echo = "You are already friends";
				System.out.println(UserController.echo);
				return false;
			}
			// if already the other user is a friend or sent a request
			if (entity.getProperty("name").toString().equals(rName)
					&& entity.getProperty("friend").toString()
							.equals(Friend.sName) && entity.getProperty("state").toString().equals("true"))
			{
				UserController.echo = "You are already friends";
				System.out.println(UserController.echo);
				return false;
			}
			
			if (entity.getProperty("name").toString().equals(sName)
					&& entity.getProperty("friend").toString()
							.equals(Friend.rName) && !entity.getProperty("state").toString().equals("true"))
			{
				UserController.echo = "You already sent request before";
				System.out.println(UserController.echo);
				return false;
			}
			// if already the other user is a friend or sent a request
			if (entity.getProperty("name").toString().equals(rName)
					&& entity.getProperty("friend").toString()
							.equals(Friend.sName) && !entity.getProperty("state").toString().equals("true"))
			{
				UserController.echo = "You already have a request from this user";
				System.out.println(UserController.echo);
				return false;
			}
			if(rName.equals(sName))
			{
				UserController.echo = "You are sending request to yourself! o.O DUH :D";
				System.out.println(UserController.echo);
				return false;
			}
		}
		// check now if there is a user with this name
		if (UserEntity.getUserWithName(rName) == null)
		{
			UserController.echo = "User Not Exist";
			System.out.println(UserController.echo);
			return false;
		}
		Key UserKey = KeyFactory.createKey("friend",
				((sName.length() * 3) + (rName.length() * 2)) * 2);
		Entity friends = new Entity("friend", UserKey);

		friends.setProperty("ID",
				((sName.length() * 3) + (rName.length() * 2)) * 2);
		friends.setProperty("name", sName);
		friends.setProperty("friend", Friend.rName);
		friends.setProperty("state", false);
		datastore.put(friends);
		
		UserController.echo = "Request Sent Successfully";
		System.out.println(UserController.echo);
		return true;
	}

	/**
	 * @param name
	 * @return arraylist of user friends names to display them
	 */
	public static ArrayList<String> getUserFriendRequests(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("friend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<String> friends = new ArrayList<String>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("friend").toString().equals(name)
					&& !entity.getProperty("state").toString().equals("true")) {
				friends.add(entity.getProperty("name").toString());
			}
		}
		return friends;
	}
	
	/**
	 * this method is used to get all the friends of the user name
	 * @param name
	 * 		the user name
	 * @return
	 * 		ArrayList of all the friends names
	 */
	public static ArrayList<String> getUserFriends(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("friend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<String> friends = new ArrayList<String>();
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("friend").toString().equals(name)
					&& entity.getProperty("state").toString().equals("true")) 
			{
				friends.add(entity.getProperty("name").toString());
			}
			else if(entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("state").toString().equals("true"))
			{
				friends.add(entity.getProperty("friend").toString());
			}
		}
		return friends;
	}
	
	/**
	 * this method is used to get all the friends request of the user name
	 * @param name
	 * 		the user name
	 * @return
	 * 		ArrayList of all the friends request names
	 */
	public static ArrayList<String> getUserSentRequests(String name) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("friend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		ArrayList<String> friends = new ArrayList<String>();
		for (Entity entity : pq.asIterable()) {
			if(entity.getProperty("name").toString().equals(name)
					&& !entity.getProperty("state").toString().equals("true"))
			{
				friends.add(entity.getProperty("friend").toString());
			}
		}
		return friends;
	}

	/**
	 * 
	 * @param name
	 *            reciever name
	 * @param friend
	 *            sender name
	 * @return String of status
	 */
	@SuppressWarnings("deprecation")
	public String Accept(String name, String friend) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("friend");
		query.addFilter("name", FilterOperator.EQUAL, friend);
		query.addFilter("friend", FilterOperator.EQUAL, name);
		// query.addFilter("ID", FilterOperator.EQUAL, ((friend.length()*3) +
		// (name.length()*2))*2);
		PreparedQuery pq = datastore.prepare(query);
		Entity friends = pq.asSingleEntity();
		friends.setProperty("state", this.state);
		datastore.put(friends);
		
		return "Done";
	}
}
