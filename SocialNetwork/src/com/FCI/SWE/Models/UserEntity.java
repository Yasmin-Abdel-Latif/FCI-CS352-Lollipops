package com.FCI.SWE.Models;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
//import com.google.appengine.api.datastore.Query.FilterOperator;


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
	public static String myName;

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
	public UserEntity(String name, String email, String password) 
	{
		this.name = name;
		this.email = email;
		this.password = password;
		UserEntity.myName = name;
	}
	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	/**
	 * getter for user password
	 * @return user password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * setter for user password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * setter for user name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * setter for user email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * getter for user name
	 * @return user name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * getter for user email
	 * @return user email
	 */
	public String getEmail() 
	{
		return email;
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
				myName= entity.getProperty("name").toString();
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
		Key UserKey = KeyFactory.createKey("users", (this.name.length()*2) + (this.password.length()*2) + (this.email.length()*2));
	    Entity employee = new Entity("users", UserKey);
	    
		employee.setProperty("ID", (this.name.length()*2) + (this.password.length()*3) + (this.email.length()*4));
		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		datastore.put(employee);

		return true;
	}
	

}