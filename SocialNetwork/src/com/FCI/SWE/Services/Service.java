package com.FCI.SWE.Services;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.Friend;
import com.FCI.SWE.Models.UserEntity;


/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class Service {
	
	public static String nameLoggedin;
	public static String FriendRequestName;
	public static ArrayList<String> CheckedFriends;
	
	@GET
	@Path("/index")
	public String index() {
		return "Hello";
	}


		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname, @FormParam("email") String email, @FormParam("password") String pass) 
	{
		JSONObject object = new JSONObject();
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname, @FormParam("password") String pass) 
	{
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) 
		{
			object.put("Status", "Failed");
		} 
		else 
		{
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
		}
		return object.toString();
	}
	/**
	 * SendFriendRequest Service
	 * @param uname provided user name
	 * @param FriendUserName
	 */
	@SuppressWarnings({ "unchecked" })
	@POST
	@Path("/SendFriendRequestService")
	public String requestService(@FormParam("friend") String friend) 
	{

		JSONObject object = new JSONObject();
		object.put("friend", friend);
		object.put("name", UserController.userData.getName());
		object.put("state", false);
		Friend friendObj = Friend.getrequest(object.toString());
		boolean isDone = friendObj.sendRequest();
		if (friendObj == null || !isDone) 
		{
			object.put("Status", "Failed");
		} 
		else
		{
			object.put("Status", "OK");
		}
		return object.toString();
	}
	@SuppressWarnings("unchecked")
	@POST
	@Path("/AcceptService")
	public String AcceptRequestService(@FormParam("fname") String friend) 
	{
		JSONObject object = new JSONObject();
		object.put("friend", UserController.userData.getName());
		object.put("name", friend);
		object.put("state", false);
		Friend friendObj = Friend.getrequest(object.toString());
		
		if (friendObj == null) 
		{
			object.put("Status", "Failed");
		} 
		else
		{
			friendObj.setState(true);
			friendObj.Accept(UserController.userData.getName(),friend);
			
			object.put("Status", "OK");
		}
		return object.toString();
	}
}