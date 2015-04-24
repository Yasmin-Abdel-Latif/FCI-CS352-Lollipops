package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.Collections;

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
	public static ArrayList<String> messages;
	
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
				RequestSent requestNotify = new RequestSent();
				Invoker invoke = new Invoker();
				invoke.setCommand(requestNotify);
				invoke.add();
			}
		}
		return object.toString();
	}
	/**
	 * Accept friend request service
	 * @param friend: sender friend name
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
			AcceptedRequest acceptNotify = new AcceptedRequest();
			Invoker invoke = new Invoker();
			invoke.setCommand(acceptNotify);
			invoke.add();
		}
		return object.toString();
	}
	
	/**
	 * chat message service
	 * @param fName: friend name to chat with
	 * @param msg: message content
	 * @return status by a json format converted to string
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
			MsgNotify msgNotify = new MsgNotify();
			Invoker invoke = new Invoker();
			invoke.setCommand(msgNotify);
			invoke.add();
		}
		else
		{
			UserController.echo = fName + " is not a friend";
			object.put("Status", "Failed");
		} 
		return object.toString();
	}
	
	/**
	 * conversation service
	 * @param fName: friend name to chat with
	 * @param cName: conversation name
	 * @return status by a json format converted to string
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
	 * Group Msg service
	 * @param cName: conversation name
	 * @param msg: message to be sent
	 * @return status by a json format converted to string
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
				GroupMsgNotify msgNotify = new GroupMsgNotify();
				Invoker invoke = new Invoker();
				invoke.setCommand(msgNotify);
				invoke.add();
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
	 * attach Conversation service
	 * @param fName: friend name whom i want to attach
	 * @param cName: conversation name
	 * @return status by a json format converted to string
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
	 * detach service
	 * @param fName: friend name whom i want to detach
	 * @param cName: conversation name
	 * @return status by a json format converted to string
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

	@SuppressWarnings("unchecked")
	@POST
	@Path("/seenService")
	public String seenNotification() 
	{
		JSONObject object = new JSONObject();
		MsgNotify msgNotify = new MsgNotify();
		GroupMsgNotify msgNotify2 = new GroupMsgNotify();
		AcceptedRequest acceptNotify = new AcceptedRequest();
		RequestSent requestNotify = new RequestSent();
		Invoker invoke = new Invoker();
		invoke.setCommand(msgNotify);
		invoke.seen();
		invoke.setCommand(msgNotify2);
		invoke.seen();
		invoke.setCommand(acceptNotify);
		invoke.seen();
		invoke.setCommand(requestNotify);
		invoke.seen();
		object.put("Status", "OK");
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/chosenService")
	public String chosenNotification(@FormParam("ID") String ID, @FormParam("type") String type) 
	{
		JSONObject object = new JSONObject();
		messages = new ArrayList<String>();
		if(type.equals("1"))
		{
			UserController.echo = "Your Friends";
			AcceptedRequest acceptNotify = new AcceptedRequest();
			Invoker invoke = new Invoker();
			invoke.setCommand(acceptNotify);
			messages = invoke.press(ID.toString());
		}
		if(type.equals("2"))
		{
			UserController.echo = "Your Friend Requests";
			RequestSent requestNotify = new RequestSent();
			Invoker invoke = new Invoker();
			invoke.setCommand(requestNotify);
			messages = invoke.press(ID.toString());
		}
		if(type.equals("3"))
		{
			UserController.echo = "Messages";
			MsgNotify msgNotify = new MsgNotify();
			Invoker invoke = new Invoker();
			invoke.setCommand(msgNotify);
			messages = invoke.press(ID.toString());
		}
		
		if(type.equals("4"))
		{
			UserController.echo = "Messages";
			GroupMsgNotify msgNotify = new GroupMsgNotify();
			Invoker invoke = new Invoker();
			invoke.setCommand(msgNotify);
			messages = invoke.press(ID.toString());
		}
		object.put("Status", "OK");
		return object.toString();
	}
	/**
	 * create post service
	 * @param pContent 
	 *                 post content
	 * @return
	 *        status in a json object
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/createPostService")
	public String createPostService(@FormParam("postContent") String pContent)
	{
		JSONObject object = new JSONObject();
		
		if(!pContent.equals(""))
		{
			String postPrivacy=UserController.passPostPrivacy;
			String postFeelings=UserController.passPostFeelings;
			UserController.timeline.createPost(pContent,postPrivacy,postFeelings);
			object.put("Status", "OK");
		}
		else
		{
			UserController.echo = " Your Post is empty.";
			object.put("Status", "Failed");
		} 
		return object.toString();
	}
	
	/**
	 * create post on another friend timeline service
	 * @param pContent 
	 *                 post content
	 * @return
	 *        status in a json object
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/createPostOnFPService")
	public String createPostOnFPService(@FormParam("postContent") String pContent)
	{
		JSONObject object = new JSONObject();
		boolean friendOf=false;
		//only friends can post on their friend timeline
		ArrayList<String>ownerFriends=Friend.getUserFriends(UserController.fpName); //getting friends of the owner of the timeline to which am posting
		for (int j = 0; j < ownerFriends.size(); j++) {
			if (ownerFriends.get(j).equals(UserController.userData.getName())) {
				friendOf = true;
				if (!pContent.equals(" ")) {
					Post newPost = new Post(UserController.fpName,UserController.userData.getName(), pContent, 0,"u", "", ""); // no feelings on someone else's wall
					UserController.FPageTimeline.addPost(newPost);
					object.put("Status", "OK");
				} else {
					UserController.echo = " Your Post is empty.";
					object.put("Status", "Failed");
				}
				break;
			}
		}
		if (!friendOf) {
			UserController.echo = "You're not a friend of "+ UserController.fpName+ ". You can't post on thier timeline.\n Send them a friend request if you know them.";
			object.put("Status", "Failed");
		}
		
		return object.toString();
	}
	
	@POST
	@Path("/createpageService")
	public String CreatePage(@FormParam("pageName") String pageName,
			@FormParam("pageType") String pageType,
			@FormParam("pageCatg") String pageCatg) {
		JSONObject object = new JSONObject();
		Page page = new Page(pageName, pageType, pageCatg,
				UserController.userData.getName());
		if (!page.checkPage()) {
			page.savePage();
			UserController.echo = "Page created successfully "; //
		} else {
			UserController.echo = "This Page Name is already exist";
		}
		return object.toString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/hashSearchService")
	public String hashSearch(@FormParam("search") String hash) {
		JSONObject object = new JSONObject();
		hash = hash.substring(1); // removing the hashtag symbol
		Hashtag hashObj = new Hashtag(hash);
		if (!hashObj.checkHashTag())
			UserController.echo = "no such a hashtag";
		else {
			ArrayList<Integer> postID = hashObj.allPost();
			for (int i = 0; i < postID.size(); i++) {
				Post post = Post.getPostByName(postID.get(i));
				UserController.hashTimeline.posts.add(post);
			}
		}
		ArrayList<Hashtag> topHashes = hashObj.getAllHashtag();
		Collections.sort(topHashes); // to order the hashtag by there number of
										// hashes
		if (topHashes.size() <= 10)
			UserController.hashTimeline.hash = (ArrayList<Hashtag>) topHashes
					.clone();
		else
		{
			for (int i=topHashes.size()-10 ;i<topHashes.size();i++)
				UserController.hashTimeline.hash.add(topHashes.get(i));
		}
		return object.toString();
	}
}