package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		friend = friend.trim();
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
		PostNotify postNotify = new PostNotify();
		ShareNotify shareNotify = new ShareNotify();
		Invoker invoke = new Invoker();
		invoke.setCommand(msgNotify);
		invoke.seen();
		invoke.setCommand(msgNotify2);
		invoke.seen();
		invoke.setCommand(acceptNotify);
		invoke.seen();
		invoke.setCommand(requestNotify);
		invoke.seen();
		invoke.setCommand(postNotify);
		invoke.seen();
		invoke.setCommand(shareNotify);
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
		
		if(type.equals("5"))
		{
			UserController.echo = "Posts";
			PostNotify postNotify = new PostNotify();
			Invoker invoke = new Invoker();
			invoke.setCommand(postNotify);
			messages = invoke.press(ID.toString());
		}
		
		if(type.equals("6"))
		{
			UserController.echo = "Posts";
			ShareNotify shareNotify = new ShareNotify();
			Invoker invoke = new Invoker();
			invoke.setCommand(shareNotify);
			messages = invoke.press(ID.toString());
		}
		object.put("Status", "OK");
		return object.toString();
	}
	@SuppressWarnings("unchecked")
	@POST
	@Path("/shareService")
	public String sharePost(@FormParam("ID") String ID) 
	{
		ID = ID.trim();
		JSONObject object = new JSONObject();
		Share sharePost = new Share(Integer.parseInt(ID));
		sharePost.share();
		ShareNotify shareNotify = new ShareNotify();
		Invoker invoke = new Invoker();
		invoke.setCommand(shareNotify);
		invoke.add();
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
			Like like = new Like();
			int likeID = like.nextLikeID();
			int postId = UserController.timeline.createPost(pContent,postPrivacy,postFeelings, likeID);

			like.setObjID(postId);
			like.setPostOrPage("post");
			like.likeObj(UserController.userData.getName());
			object.put("Status", "OK");
			if (pContent.contains("#")) 
			{
				Pattern MY_PATTERN = Pattern.compile("(?:(?<=\\s)|^)#(\\w*[A-Za-z_]+\\w*)");
				Matcher mat = MY_PATTERN.matcher(pContent);
				ArrayList<String> alreadyInPost = new ArrayList<String>();
				while (mat.find()) {
					String hash = mat.group(1);
					if (alreadyInPost.contains(hash)) // if this hashtag is	repeated in the post
						continue;
					Hashtag hashObj = new Hashtag(hash);
					if (!hashObj.checkHashTag()) 
					{
						hashObj.saveHashtag(postId);
					} 
					else 
					{
						hashObj.upDateHashtag(postId);
					}
					alreadyInPost.add(hash);
				}
			}
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
		pContent = pContent.trim();
		//only friends can post on their friend timeline
		if (Friend.getUserFriends(UserController.fpName).contains(UserController.userData.getName())) 
		{
			if (!pContent.equals("")) 
			{
				UserController.FPageTimeline = new FriendPageTimeline();
				Post newPost = new Post(UserController.fpName,UserController.userData.getName(), pContent, 0,"u", "", "",0,0); // no feelings on someone else's wall
				ArrayList<Post> friendPagePosts = new ArrayList<Post>();
				friendPagePosts.addAll(FriendPageTimeline.getAllPosts(UserController.fpName));
				friendPagePosts.addAll(FriendPageTimeline.getAllSharedPosts(UserController.fpName));
				UserController.FPageTimeline.setPosts(friendPagePosts);
				int postId = UserController.FPageTimeline.addPost(newPost);
	 			object.put("Status", "OK");
	 			PostNotify postNotify = new PostNotify();
				Invoker invoke = new Invoker();
				invoke.setCommand(postNotify);
				invoke.add();
				if (pContent.contains("#")) 
				{
					Pattern MY_PATTERN = Pattern.compile("(?:(?<=\\s)|^)#(\\w*[A-Za-z_]+\\w*)");
					Matcher mat = MY_PATTERN.matcher(pContent);
					ArrayList<String> alreadyInPost = new ArrayList<String>();
					while (mat.find()) {
						String hash = mat.group(1);
						if (alreadyInPost.contains(hash)) // if this hashtag is	repeated in the post
							continue;
						Hashtag hashObj = new Hashtag(hash);
						if (!hashObj.checkHashTag()) 
						{
							hashObj.saveHashtag(postId);
						} 
						else 
						{
							hashObj.upDateHashtag(postId);
						}
						alreadyInPost.add(hash);
					}
				}
			} 
			else 
			{
				UserController.echo = " Your Post is empty.";
				object.put("Status", "Failed");
			}
		}
		else 
		{
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
		if (!page.checkPage()) 
		{
			Like pageLike = new Like();
			int likeID = pageLike.nextLikeID();
			int objID = page.savePage(likeID);
			pageLike.setObjID(objID);
			pageLike.setPostOrPage("page");
			pageLike.likeObj(UserController.userData.getName());
			UserController.echo = "Page created successfully "; //
		} 
		else 
		{
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
	
	/**
	 * 
	 * @return
	 */
	@POST
	@Path("/pagelikeService")
	public String pageLike(@FormParam("name") String name) {
		JSONObject object = new JSONObject();
		if (UserController.pageTimeline.page.getPageName()==null)
			UserController.echo = "no such a page";
		else {
			if (UserController.pageTimeline.like)
					UserController.echo = "you already like this page" ;
			else
			{
				Like like = new Like ();
				like.setLikeID(UserController.pageTimeline.page.getLikeID());
				like.likePage(name);
				UserController.pageTimeline.like = true;
			}
		}
		return object.toString();
	}
	
	/**
	 *  this method is to like posts 
	 * @param postID
	 * @return
	 */
	@POST
	@Path("/postlikeService")
	public String postLike(@FormParam("ID") String postID) {
		JSONObject object = new JSONObject();
		postID = postID.trim();
		int postId = Integer.parseInt(postID);
		Like like = new Like();
		like.setObjID(postId);
		like.setPostOrPage("post");
		like.allLikeUser();
		ArrayList<String> users = like.getUsers();
		if (users.contains(UserController.userData.getName()))
			UserController.echo = "you already like this post";
		else 
			like.likePost(UserController.userData.getName());
		
		return object.toString();
	}
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/pageSearchService")
	public String pageSearch(@FormParam("search") String page) {
		JSONObject object = new JSONObject();
		Page pageObj = new Page(page);
		UserController.pageTimeline.page = new Page();
		PageTimeline.posts = new ArrayList<Post> ();
		if (!pageObj.checkPage())
			UserController.echo = "no such a page";
		else 
		{
			UserController.pageTimeline.page = pageObj.getPage();
			PageTimeline.getAllPosts(page);
			UserController.echo = page + " page";
			int likeID = UserController.pageTimeline.page.getLikeID();
			Like like = new Like ();
			like.allLikeUserByID(likeID);
			ArrayList <String> users = new ArrayList<String>();
			users = (ArrayList<String>) like.getUsers().clone();
			if (users.contains(UserController.userData.getName()))
				UserController.pageTimeline.like= true;
			else
				UserController.pageTimeline.like= false;
			
		}
		return object.toString();
	}

	/**
	 * create post on page by admain
	 * 
	 * @param pContent
	 *            post content
	 * @return status in a json object
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/createPostOnpageService")
	public String createPostOnPageService(
			@FormParam("postContent") String pContent) {
		JSONObject object = new JSONObject();
		if (!pContent.equals(" ")) {
			Like like = new Like();
			int likeID = like.nextLikeID();
			Post newPost = new Post(
					UserController.pageTimeline.page.getPageName(),
					UserController.pageTimeline.page.getPageName(), pContent,
					likeID, "p", "", "", 0,0);
			int postID = UserController.pageTimeline.createPost(newPost);
			like.setObjID(postID);
			like.setPostOrPage("post");
			like.likeObj(UserController.userData.getName());
			if (pContent.contains("#")) {
				Pattern MY_PATTERN = Pattern
						.compile("(?:(?<=\\s)|^)#(\\w*[A-Za-z_]+\\w*)");
				Matcher mat = MY_PATTERN.matcher(pContent);
				ArrayList<String> alreadyInPost = new ArrayList<String>();
				while (mat.find()) {
					String hash = mat.group(1);
					if (alreadyInPost.contains(hash)) // if this hashtag is
														// repeated in the post
						continue;
					Hashtag hashObj = new Hashtag(hash);
					if (!hashObj.checkHashTag()) {
						hashObj.saveHashtag(postID);
					} else {
						hashObj.upDateHashtag(postID);
					}
					alreadyInPost.add(hash);

				}
			}
			UserController.echo = "Post Created Successfuly";
			object.put("Status", "OK");
		} else {
			UserController.echo = " Your Post is empty.";
			object.put("Status", "Failed");
		}
		return object.toString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/redirService")
	public String fpTimeline(@FormParam("searchBox") String fpName1) {
		JSONObject object = new JSONObject();
		fpName1 = fpName1.trim();
		UserController.fpName = fpName1;
		UserController.echo = UserController.fpName;
		UserController.FPageTimeline = new FriendPageTimeline();
		ArrayList<Post> friendPagePosts = new ArrayList<Post>();
		friendPagePosts.addAll(FriendPageTimeline.getAllPosts(UserController.fpName));
		UserController.FPageTimeline.setPosts(friendPagePosts);
		UserController.FPageTimeline.seen();
		object.put("Status", "OK");
		return object.toString();
	}
}