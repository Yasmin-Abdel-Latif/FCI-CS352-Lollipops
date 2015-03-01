package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

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
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser();
		JSONObject object = new JSONObject();
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
	@POST
	@Path("/SendFriendRequestService")
	public String SendFriendRequestService(@FormParam("uname") String uname,@FormParam("FriendUserName") String FriendUserName)
	{
		UserEntity User = UserEntity.getUserWithName(uname);
		UserEntity Friend = UserEntity.getUserWithName(FriendUserName);

		if (Friend == null) 
		{
			return "Username requested Not Found";
		} 
		else 
		{
			User.setFriendUserName(FriendUserName);
			User.setFriendRequestStatus(false);
			Friend.setFriendRequest(uname);
			User.updateUser();
			Friend.updateUser();
			return "Friend request was sent";
		}

	}

}