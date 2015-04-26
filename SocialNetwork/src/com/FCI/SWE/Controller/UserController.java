package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.*;
import com.FCI.SWE.Services.Service;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohammed Samir, Mariam Fouad, Nour Mohammed Srour Alwani, Yasmine
 *         Abdel Latif, Salwa Ahmed, Huda Mohammed
 * @version 1.0
 * @since 2014-02-12
 *
 */
@SuppressWarnings("unused")
@Path("/")
@Produces("text/html")
public class UserController {

	//http://localhost:8080/rest/
	//http://direct-hallway-864.appspot.com/rest/
	//http://lollipops-social-network.appspot.com/rest/
	public String webServiceLink = "http://lollipops-social-network.appspot.com/rest/";
	public static UserEntity userData = null;
	private static boolean sentFriend = false;
	private static boolean firstTime = true;
	public static ArrayList<String> friendRequests;
	public static ArrayList<String> friends;
	public static ArrayList<String> userSentRequests;
	public static ArrayList<String> userUnSeenNotifications;
	public static ArrayList<String> userSeenNotifications;
	public static String echo;
	public static uTimeline timeline= new uTimeline();
	public static String fpName=""; //friend or page name to which timeline i wanna post to
	public static fpTimeline FPageTimeline= new fpTimeline();
	public static String passPostFeelings="notValid";
	public static String passPostPrivacy="Public"; //default is public
	public static boolean gotIntoSetFeelings=false;
	public static boolean gotIntoSetPrivacy=false;
	public static String[] customList;
	public static HashTimeline hashTimeline;
	public static PageTimeline pageTimeline = new PageTimeline ();
	
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
	}

	/**
	 * Action function to render Sign Out page, this function will be executed
	 * using url like this /rest/signout
	 * 
	 * @return signOut page
	 */

	@GET
	@Path("/signout")
	public Response signOut() {
		return Response.ok(new Viewable("/jsp/signOut")).build();
	}

	/**
	 * Action Function to direct to message page
	 * using url like this /rest/Messages
	 * 
	 * @return Messages page
	 */
	
	@GET
	@Path("/msg")
	public Response Message() {
		return Response.ok(new Viewable("/jsp/messages")).build();
	}
	
	/**
	 * Action function to redirect user to startup page after signing out.
	 * 
	 * @return entryPoint page
	 */
	@GET
	@Path("/redirectStartUp")
	public Response redirectStartUp() {
		userData.setName("");
		userData.setPassword("");
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to redirect user to home page if she/he choose to back up
	 * from signing out This function does not create a new "user". The user is
	 * defined via her/his info in the datastore. It just passes vars here.
	 * 
	 * @return home page
	 */
	@GET
	@Path("/redirectHome")
	public Response redirectHome() {
		UserEntity user = new UserEntity(userData.getName(),
				userData.getPassword());
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", user.getName());
		map.put("email", user.getEmail());
		return Response.ok(new Viewable("/jsp/home", map)).build();

	}

	/**
	 * 
	 * In case of failure, it redirects to sendfriendrequest page.
	 * @return SendFriendRequest page
	 */
	@GET
	@Path("/SendFriendRequest")
	public Response SendRequest() {
		return Response.ok(new Viewable("/jsp/SendFriendRequest")).build();
	}
	
	/**
	 * 
	 * In case of failure, it redirects to sendfriendrequest page.
	 * @return SendFriendRequest page
	 */
	@GET
	@Path("/notify")
	public Response notifyUser() {
		return Response.ok(new Viewable("/jsp/notifications")).build();
	}

	/**
	 * Action Function to direct to timeline
	 * using url like this /rest/timeline
	 * 
	 * @return Messages page
	 */
	
	@GET
	@Path("/timeline")
	public Response timeline() {
		return Response.ok(new Viewable("/jsp/timeline")).build();
	}

	/**
	 * 
	 * @return
	 */
	@POST
	@Path("/fpTimeline")
	public Response fpTimeline(@FormParam("searchBox") String fpName1) {
		UserController.fpName=fpName1;
		UserController.echo=UserController.fpName;
		FPageTimeline.setPosts(fpTimeline.getAllPosts(fpName1));

		return Response.ok(new Viewable("/jsp/fpTimeline")).build();
	}
	
	@GET
	@Path("/Page")
	public Response page() {
		return Response.ok(new Viewable("/jsp/createPage")).build();
	}

	/**
	 * 
	 * @return
	 */
	@POST
	@Path("/setFeelings")
	public void setFeelings(@FormParam ("feelingSelect") String feelingSelect) {
		UserController.passPostFeelings=feelingSelect;
		gotIntoSetFeelings=true;
	}
	
	/**
	 *  setPrivacy method:
	 *                     will set the privacy of this current post in order to user it later in service
	 */
	@POST
	@Path("/setPrivacy")
	public void setPrivacy(@FormParam ("privacySelect") String privacySelect,@FormParam ("cList") String cList) {
		UserController.passPostPrivacy=privacySelect;
		gotIntoSetPrivacy=true;
		customList=cList.split("\\s*,\\s*");
	}
	/**
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces("text/html")
	public Response response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		String serviceUrl = webServiceLink + "RegistrationService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&email=" + email
					+ "&password=" + pass;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return Response.ok(new Viewable("/jsp/Registeration")).build();
			
			return Response.ok(new Viewable("/jsp/login")).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return null;
	}

	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String serviceUrl = webServiceLink + "LoginService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "uname=" + uname + "&password=" + pass;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			UserEntity user = UserEntity.getUser(object.toJSONString());
			userData = user;
			map.put("message","Welcome " + user.getName());
			friendRequests = Friend.getUserFriendRequests(userData.getName());
			friends = Friend.getUserFriends(userData.getName());
			userSentRequests = Friend.getUserSentRequests(userData.getName());
			timeline.setPosts(uTimeline.getAllPosts(userData.getName()));
			passPostFeelings="notValid";
			passPostPrivacy="Public";
			ArrayList<String> msgNotifications = MsgNotify.myUnSeenNotifications();
			ArrayList<String> groupMsgNotifications = GroupMsgNotify.myUnSeenNotifications();
			ArrayList<String> requestNotifications = RequestSent.myUnSeenNotifications();
			ArrayList<String> acceptNotifications = AcceptedRequest.myUnSeenNotifications();
			ArrayList<String> postNotifications = PostNotify.myUnSeenNotifications();
			ArrayList<String> shareNotifications = ShareNotify.myUnSeenNotifications();
			userUnSeenNotifications = new ArrayList<String>();
			userUnSeenNotifications.addAll(msgNotifications);
			userUnSeenNotifications.addAll(groupMsgNotifications);
			userUnSeenNotifications.addAll(requestNotifications);
			userUnSeenNotifications.addAll(acceptNotifications);
			userUnSeenNotifications.addAll(postNotifications);
			userUnSeenNotifications.addAll(shareNotifications);
			ArrayList<String> msgNotifications1 = MsgNotify.mySeenNotifications();
			ArrayList<String> groupMsgNotifications1 = GroupMsgNotify.mySeenNotifications();
			ArrayList<String> requestNotifications1 = RequestSent.mySeenNotifications();
			ArrayList<String> acceptNotifications1 = AcceptedRequest.mySeenNotifications();
			ArrayList<String> postNotifications1 = PostNotify.mySeenNotifications();
			ArrayList<String> shareNotifications1 = ShareNotify.mySeenNotifications();
			userSeenNotifications = new ArrayList<String>();
			userSeenNotifications.addAll(msgNotifications1);
			userSeenNotifications.addAll(groupMsgNotifications1);
			userSeenNotifications.addAll(requestNotifications1);
			userSeenNotifications.addAll(acceptNotifications1);
			userSeenNotifications.addAll(postNotifications1);
			userSeenNotifications.addAll(shareNotifications1);
			map.put("name", user.getName());

			return Response.ok(new Viewable("/jsp/home", map)).build();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Action function to send friend request to fName
	 * 
	 * @param fName
	 *            provided requested friend name
	 * @return Home page view
	 */
	@POST
	@Path("/request")
	@Produces("text/html")
	public Response Request(@FormParam("friend") String fName) {
		String serviceUrl = webServiceLink + "SendFriendRequestService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friend=" + fName;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message","Welcome " + userData.getName() + "<br>" + echo);
			userSentRequests = Friend.getUserSentRequests(userData.getName());
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/home", map)).build();
	}

	/**
	 * Action function to accept friend request with name friend
	 * 
	 * @param friend
	 *            provided accepted friend name
	 * @return Home page view
	 */
	@POST
	@Path("/Accept")
	@Produces("text/html")
	public Response Accept(@FormParam("fname") String friend) {
		String serviceUrl = webServiceLink + "AcceptService";
		Map<String, String> map = new HashMap<String, String>();
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "fname=" + friend;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			map.put("message","Welcome " + userData.getName());
			friendRequests = Friend.getUserFriendRequests(userData.getName());
			friends = Friend.getUserFriends(userData.getName());
			userSentRequests = Friend.getUserSentRequests(userData.getName());
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/home", map)).build();
	}
	
	/**
	 * Action function to send one-to-one msg to a friend
	 * 
	 * @param fName
	 *            provided requested friend name
	 * @return Home page view
	 */
	@POST
	@Path("/ChatMsg")
	@Produces("text/html")
	public Response chatMsg(@FormParam("friendsNames") String fName, @FormParam("chatMsg") String msg) {
		String serviceUrl = webServiceLink + "ChatMsgService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friendsNames=" + fName + "&chatMsg=" + msg;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message","Welcome " + userData.getName() + "<br>" + echo);
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/home", map)).build();
	}
	
	/**
	 * Action function to create a conversation with some users as receivers
	 * 
	 * @param fName
	 *            receivers names separated by ;
	 * @param cName
	 * 			  the ID => the conversation
	 * @return Home page view
	 */
	@POST
	@Path("/CreateConversation")
	@Produces("text/html")
	public Response createConversation(@FormParam("friendsNames") String fName, @FormParam("conversationName") String cName) {
		String serviceUrl = webServiceLink + "conversationService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friendsNames=" + fName + "&conversationName=" + cName;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			echo = "Created Successfully";
			if (object.get("Status").equals("Failed"))
				echo = "Conversation Name Already Exist";
			map.put("message", echo);
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/messages", map)).build();
	}
	
	/**
	 * Action function to send a group msg to the conversation receiver
	 * 
	 * @param cName the conversation name
	 * @param msg the sent message
	 * 						provided requested friend name
	 * @return messages page view
	 */
	@POST
	@Path("/SendMsg")
	@Produces("text/html")
	public Response groupMsg(@FormParam("conversationName") String cName, @FormParam("groupMsg") String msg) {
		String serviceUrl = webServiceLink + "SendGroupMsgService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "conversationName=" + cName + "&groupMsg=" + msg;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", echo);
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/messages", map)).build();
	}
	
	/**
	 * Action function to attach someone to the conversation
	 * 
	 * @param fName the name of (attached use)
	 * @param cName the conversation name
	 * @return messages page view
	 */
	@POST
	@Path("/AttachToConversation")
	@Produces("text/html")
	public Response attachConversation(@FormParam("friendsNames") String fName, @FormParam("conversationName") String cName) {
		String serviceUrl = webServiceLink + "attachService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friendsNames=" + fName + "&conversationName=" + cName;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", echo);
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/messages", map)).build();
	}
	
	/**
	 * Action function to detach someone to the conversation
	 * 
	 * @param fName the name of (detached user)
	 * @param cName the conversation name
	 * @return messages page view
	 */
	@POST
	@Path("/DeattachFromConversation")
	@Produces("text/html")
	public Response deattachConversation(@FormParam("friendsNames") String fName, @FormParam("conversationName") String cName) {
		String serviceUrl = webServiceLink + "deattachService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friendsNames=" + fName + "&conversationName=" + cName;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", echo);
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/messages", map)).build();
	}
	
	@POST
	@Path("/seenNotifications")
	@Produces("text/html")
	public Response seenNotification() {
		String serviceUrl = webServiceLink + "seenService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			ArrayList<String> msgNotifications = MsgNotify.myUnSeenNotifications();
			ArrayList<String> groupMsgNotifications = GroupMsgNotify.myUnSeenNotifications();
			ArrayList<String> requestNotifications = RequestSent.myUnSeenNotifications();
			ArrayList<String> acceptNotifications = AcceptedRequest.myUnSeenNotifications();
			ArrayList<String> postNotifications = PostNotify.myUnSeenNotifications();
			ArrayList<String> shareNotifications = ShareNotify.myUnSeenNotifications();
			userUnSeenNotifications = new ArrayList<String>();
			userUnSeenNotifications.addAll(msgNotifications);
			userUnSeenNotifications.addAll(groupMsgNotifications);
			userUnSeenNotifications.addAll(requestNotifications);
			userUnSeenNotifications.addAll(acceptNotifications);
			userUnSeenNotifications.addAll(postNotifications);
			userUnSeenNotifications.addAll(shareNotifications);
			ArrayList<String> msgNotifications1 = MsgNotify.mySeenNotifications();
			ArrayList<String> groupMsgNotifications1 = GroupMsgNotify.mySeenNotifications();
			ArrayList<String> requestNotifications1 = RequestSent.mySeenNotifications();
			ArrayList<String> acceptNotifications1 = AcceptedRequest.mySeenNotifications();
			ArrayList<String> postNotifications1 = PostNotify.mySeenNotifications();
			ArrayList<String> shareNotifications1 = ShareNotify.mySeenNotifications();
			userSeenNotifications = new ArrayList<String>();
			userSeenNotifications.addAll(msgNotifications1);
			userSeenNotifications.addAll(groupMsgNotifications1);
			userSeenNotifications.addAll(requestNotifications1);
			userSeenNotifications.addAll(acceptNotifications1);
			userSeenNotifications.addAll(postNotifications1);
			userSeenNotifications.addAll(shareNotifications1);
			friendRequests = Friend.getUserFriendRequests(userData.getName());
			friends = Friend.getUserFriends(userData.getName());
			userSentRequests = Friend.getUserSentRequests(userData.getName());
			map.put("message", "Welcome " + userData.getName());
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/home", map)).build();
	}
	
	@POST
	@Path("/chosenNotification")
	@Produces("text/html")
	public Response chosenNotification(@FormParam("ID") String ID, @FormParam("type") String type) {
		
		String serviceUrl = webServiceLink + "chosenService";
		Map<String, String> map = new HashMap<String, String>();
		try {
			
			URL url = new URL(serviceUrl);
			String urlParameters = "ID=" + ID + "&type=" + type;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", "Welcome " + userData.getName() + "<br>" + echo);
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			map.put("message", "Welcome " + userData.getName() + "<br>MalformedURLException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			map.put("message", "Welcome " + userData.getName() + "<br>IOException");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			map.put("message", "Welcome " + userData.getName() + "<br>ParseException");
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/pressedNotify", map)).build();
	}
	@POST
	@Path("/sharePost")
	@Produces("text/html")
	public Response sharePoste(@FormParam("ID") String ID) {
		
		String serviceUrl = webServiceLink + "shareService";
		Map<String, String> map = new HashMap<String, String>();
		try {
			
			URL url = new URL(serviceUrl);
			String urlParameters = "ID=" + ID;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", "Welcome " + userData.getName() + "<br>" + echo);
			map.put("name", fpName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			map.put("message", "Welcome " + userData.getName() + "<br>MalformedURLException");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			map.put("message", "Welcome " + userData.getName() + "<br>IOException");
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			map.put("message", "Welcome " + userData.getName() + "<br>ParseException");
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/fpTimeline", map)).build();
	}
	/**
	 * Action function to post a new post
	 * 
	 */
	@POST
	@Path("/createPost")
	@Produces("text/html")
	public Response createPost(@FormParam("postContent") String pContent) {
		if (!gotIntoSetFeelings)  // if i havent selected a feeling, set it to not valid in order not to print the last feeling selected 
		{	
			UserController.passPostFeelings="notValid";
		}
		else
		{
			gotIntoSetFeelings=false;
		}
		if (!gotIntoSetPrivacy) // if i havent selected a privacy yet, set it to public in order not to print the last privacy selected
		{
			UserController.passPostPrivacy="Public";
		} 
		else 
		{
			gotIntoSetPrivacy = false;
		}
		String serviceUrl = webServiceLink + "createPostService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "&postContent=" + pContent;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", echo);
			map.put("name", userData.getName());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/timeline", map)).build();
	}
	
	/**
	 * Action function to post a new post
	 * 
	 */
	@POST
	@Path("/createPostOnFP")
	@Produces("text/html")
	public Response createPostOnFP(@FormParam("postContent") String pContent) {
		String serviceUrl = webServiceLink + "createPostOnFPService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "&postContent=" + pContent;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", echo);
			map.put("name", fpName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/fpTimeline", map)).build();
	}
	
	/**
	 * Action function to post a new post
	 * 
	 */
	@POST
	@Path("/createPostOnPage")
	@Produces("text/html")
	public Response createPostOnPage(@FormParam("postContent") String pContent) {
		String serviceUrl = webServiceLink + "createPostOnpageService";
		Map<String, String> map = new HashMap<String, String>();

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "&postContent=" + pContent;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds

			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;

			map.put("message", echo);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/jsp/page", map)).build();
	}
	
	/**
	 * 
	 * @return
	 */
	@POST
	@Path("/likePage")
	@Produces("text/html")
	public Response likePage() {
		Map<String, String> map = new HashMap<String, String>();
		String serviceUrl = webServiceLink + "pagelikeService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "name="+userData.getName();
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			map.put("message", echo);
			map.put("name", fpName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return Response.ok(new Viewable("/jsp/Ppage", map)).build();

	}
	
	/**
	 * 
	 * @param postID
	 */
	@POST
	@Path("/likePost")
	@Produces("text/html")
	public void likePost(@FormParam("postID") String postID) {
		Map<String, String> map = new HashMap<String, String>();
		String serviceUrl = webServiceLink + "postlikeService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "postID="+postID;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			map.put("message", echo);
			map.put("name", fpName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 
	 * @param pageName
	 * @param pageType
	 * @param pageCatg
	 * @return
	 */
	@POST
	@Path("/CreatePage")
	@Produces("text/html")
	public Response CreatePage(@FormParam("PageName") String pageName ,
			@FormParam("pageType") String pageType, @FormParam("pageCatg") String pageCatg) {
		Map<String, String> map = new HashMap<String, String>();
		String serviceUrl = webServiceLink + "createpageService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "pageName=" + pageName+ "&pageType=" + pageType
					+ "&pageCatg=" + pageCatg;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			map.put("message", echo);
			map.put("name", fpName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * UserEntity user = new UserEntity(uname, email, pass);
		 * user.saveUser(); return uname;
		 */
		return Response.ok(new Viewable("/jsp/createPage", map)).build();

	}
	
	@POST
	@Path("/hashTimeline")
	@Produces("text/html")
	public Response search(@FormParam("searchBoxh") String text ) {
		hashTimeline = new HashTimeline();
		Map<String, String> map = new HashMap<String, String>();
		String serviceUrl;
		if (text.contains("#"))
		 serviceUrl = webServiceLink + "hashSearchService";
		else
		serviceUrl = webServiceLink + "pageSearchService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "search=" + text ;
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(60000); // 60 Seconds
			connection.setReadTimeout(60000); // 60 Seconds
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String line, retJson = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = reader.readLine()) != null) {
				retJson += line;
			}
			writer.close();
			reader.close();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			map.put("message", echo);
			map.put("name", fpName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(text.contains("#"))
			return Response.ok(new Viewable("/jsp/HashTimeline", map)).build();
		if (pageTimeline.page.getPageOwner().equals(userData.getName())) //the user is the admain
			return Response.ok(new Viewable("/jsp/page", map)).build();
		
		return Response.ok(new Viewable("/jsp/Ppage", map)).build();
	}
}