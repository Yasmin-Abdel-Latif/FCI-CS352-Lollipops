package com.FCI.SWE.Models;

import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;


/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {

	private String name;
	private String email;
	private String password;
	private String FriendUserName;
	private String FriendRequest;
	private boolean FriendRequestStatus;

	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		
		this.name = name;
		this.email = email;
		this.password = password;
		this.FriendRequestStatus = false;
		this.FriendUserName = "";
		this.FriendRequest = "";

	}

	public void setFriendUserName(String FriendUserName) {
		this.FriendUserName = FriendUserName;
	}
	
	public void setFriendRequest(String FriendRequest) {
		this.FriendRequest = FriendRequest;
	}
	
	public void setFriendRequestStatus(boolean FriendRequestStatus) {
		this.FriendRequestStatus = FriendRequestStatus;
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
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
	public static UserEntity getUser(String json) {

		JSONParser parser = new JSONParser();
		try {
			JSONObject object = (JSONObject) parser.parse(json);
			return new UserEntity(object.get("name").toString(), object.get(
					"email").toString(), object.get("password").toString());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(name) && entity.getProperty("password").toString().equals(pass)) 
			{
				UserEntity returnedUser = new UserEntity(entity.getProperty("name").toString(), entity.getProperty("email").toString(), entity.getProperty("password").toString());
				return returnedUser;
			}
		}

		return null;
	}
	
	public static UserEntity getUserWithName(String name) 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			System.out.println(entity.getProperty("name").toString());
			if (entity.getProperty("name").toString().equals(name)) 
			{
				UserEntity returnedUser = new UserEntity(entity.getProperty("name").toString(), entity.getProperty("email").toString(), entity.getProperty("password").toString());
				return returnedUser;
			}
		}

		return null;
	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		
		Key UserKey = KeyFactory.createKey("users", list.size() + 1);
	    Entity employee = new Entity("users", UserKey);
	 
		employee.setProperty("ID", list.size() + 1);
		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		employee.setProperty("Friend", this.FriendUserName);
		employee.setProperty("Friend Request", this.FriendRequest);
		employee.setProperty("Friend Request Status", this.FriendRequestStatus);
		datastore.put(employee);

		return true;
	}
	
	@SuppressWarnings({ "deprecation" })
	public String updateUser()
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("users");
		query.addFilter("name", FilterOperator.EQUAL, this.name);
		PreparedQuery pq = datastore.prepare(query);
		Entity employee = pq.asSingleEntity();
		
		if(employee.getProperty("Friend").equals(""))
		{
			employee.setProperty("Friend", this.FriendUserName);
		}
		
		if(!employee.getProperty("Friend").equals("") && !employee.getProperty("Friend").toString().contains(this.FriendUserName))
		{
			employee.setProperty("Friend", employee.getProperty("Friend") + "," +this.FriendUserName);
			employee.setProperty("Friend Request Status", employee.getProperty("Friend Request Status") + "," + this.FriendRequestStatus);
		}

		if(employee.getProperty("Friend Request").equals(""))
		{
			employee.setProperty("Friend Request", this.FriendRequest);
		}

		if(!employee.getProperty("Friend Request").equals("") && !employee.getProperty("Friend Request").toString().contains(this.FriendRequest))
		{
			employee.setProperty("Friend Request", employee.getProperty("Friend Request") + "," + this.FriendRequest);
			employee.setProperty("Friend Request Status", employee.getProperty("Friend Request Status") + "," + this.FriendRequestStatus);
		}

		
		datastore.put(employee);
		return "Done";
	}
}
