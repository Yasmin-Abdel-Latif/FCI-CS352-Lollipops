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
import com.FCI.SWE.Models.*;


/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohammed Samir, Mariam Fouad, Nour Mohammed Srour Alwani, Yasmine Abdel Latif, Salwa Ahmed, Huda Mohammed
 * @version 2.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class Service {
	
	public static String nameLoggedin;
	public static String friendRequestName;
	public static ArrayList<String> checkedFriends;
	
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
	 * @return Status json in string fromat
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname, @FormParam("email") String email, @FormParam("password") String pass) 
	{
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUserWithName(uname);
		if(user != null)
		{
			object.put("Status", "Failed");
		}
		else
		{

			user = new UserEntity(uname, email, pass);
			user.saveUser();
			object.put("Status", "OK");
		}
		
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
			object.put("password", user.getPassword());
		}
		return object.toString();
	}
	/**
	 * SendFriendRequest Service
	 * @param FriendUserName
	 * @return friend request in json format converted to string
	 */
	@SuppressWarnings({ "unchecked" })
	@POST
	@Path("/SendFriendRequestService")
	public String requestService(@FormParam("friend") String friend) 
	{

		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUserWithName(friend);
		if (user == null) 
		{
			object.put("Status", "Failed");
			UserController.echo = "user name doesn't exist";
		} 
		else 
		{
			Friend friendObj = new Friend(friend,UserController.userData.getName());
			boolean isDone = friendObj.sendRequest();
			if (!isDone) 
			{
				object.put("Status", "Failed");
			} 
			else
			{
				object.put("friend", friend);
				object.put("name", UserController.userData.getName());
				object.put("state", false);
				object.put("Status", "OK");
			}
		}
		return object.toString();
	}
	/**
	 * Accept friend request service
	 * @param sender friend name
	 * @return friend request in json format converted to string
	 */
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
	
	/**
	 * Chat With a friend service
	 * @param fName receiver friend name
	 * @param msg the sent message
	 * @return json object with status OK or failed
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/ChatMsgService")
	public String ChatMsgService(@FormParam("friendsNames") String fName, @FormParam("chatMsg") String msg) 
	{
		JSONObject object = new JSONObject();
		
		if(UserController.friends.contains(fName))
		{
			Messages chatMsgObj = new ChatMsg(fName, UserController.userData.getName(), msg);
			chatMsgObj.sendMessage();
			UserController.echo = "Message Sent Successfully";
			object.put("Status", "OK");
		}
		else
		{
			UserController.echo = fName + " is not a friend";
			object.put("Status", "Failed");
		} 
		return object.toString();
	}
	
	/**
	 * create a conversation with some users
	 * @param fName string contains receivers names separated with ;
	 * @param cName the conversation name which is unique
	 * @return json object with status OK or failed
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/conversationService")
	public String createConversation(@FormParam("friendsNames") String fName, @FormParam("conversationName") String cName) 
	{
		JSONObject object = new JSONObject();
		
		GroupMsg groupMsgObj = GroupMsg.getConversationWithName(cName);
		if (groupMsgObj != null) 
		{
			object.put("Status", "Failed");
		} 
		else
		{
			groupMsgObj = new GroupMsg(cName, UserController.userData.getName(), "");
			groupMsgObj.saveConversation(fName);
			object.put("Status", "OK");
		}
		return object.toString();
	}
	
	/**
	 * send a message to a specific conversation(Group msg)
	 * @param cName the conversation name which is unique
	 * @param msg the sent message
	 * @return json object with status OK or failed
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/SendGroupMsgService")
	public String GroupMsgService(@FormParam("conversationName") String cName, @FormParam("groupMsg") String msg)
	{
		JSONObject object = new JSONObject();
		
		
		Messages groupMsgObj = GroupMsg.getConversationWithName(cName);
		if (groupMsgObj == null) 
		{
			UserController.echo = "this Conversation doesn't exist";
			object.put("Status", "Failed");
		}
		else
		{
			if(GroupMsg.getAllRecievers(cName).contains(UserController.userData.getName()) || GroupMsg.getCreator(cName).equals(UserController.userData.getName()))
			{
				groupMsgObj = new GroupMsg(cName, UserController.userData.getName(), msg);
				groupMsgObj.sendMessage();
				UserController.echo = "Message Sent Successfully";
				object.put("Status", "OK");
			}
			else
			{
				UserController.echo = "you don't this Conversation";
				object.put("Status", "Failed");
			}
		}
		return object.toString();
	}
	
	/**
	 * the creator the only one who can attach someone to an existing conversation
	 * @param fName the person name to attach
	 * @param cName the conversation name to attach to
	 * @return json object with status OK or failed
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/attachService")
	public String attachConversation(@FormParam("friendsNames") String fName, @FormParam("conversationName") String cName) 
	{
		JSONObject object = new JSONObject();
		
		GroupMsg groupMsgObj = GroupMsg.getConversationWithName(cName);
		if (groupMsgObj == null) 
		{
			UserController.echo = "conversation not exist";
			object.put("Status", "Failed");
		} 
		else
		{
			if(groupMsgObj.attach(fName, cName))
			{
				UserController.echo = fName + " attached to the conversation";
				object.put("Status", "OK");
			}
			else
			{
				object.put("Status", "Failed");
			}
		}
		return object.toString();
	}
	
	/**
	 * the creator can detach someone from a conversation and a receiver can detach himself but not others
	 * @param fName the person name to detach
	 * @param cName the conversation name to detach from
	 * @return json object with status OK or failed
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/deattachService")
	public String deattachConversation(@FormParam("friendsNames") String fName, @FormParam("conversationName") String cName) 
	{
		JSONObject object = new JSONObject();
		
		GroupMsg groupMsgObj = GroupMsg.getConversationWithName(cName);
		if (groupMsgObj == null) 
		{
			UserController.echo = "conversation not exist";
			object.put("Status", "Failed");
		} 
		else
		{
			UserController.echo = "conversation exist";
			if(groupMsgObj.deattach(fName, cName))
			{
				object.put("Status", "OK");
			}
			else
			{
				object.put("Status", "Failed");
			}
		}
		return object.toString();
	}
}