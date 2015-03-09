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

import com.FCI.SWE.Models.Friend;
import com.FCI.SWE.Models.UserEntity;
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

	public static UserEntity userData = null;
	private static boolean sentFriend = false;
	private static boolean firstTime = true;
	public static ArrayList<String> FriendRequests;
	public static ArrayList<String> Friends;
	public static ArrayList<String> UserSentRequests;

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
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		String serviceUrl = "http://direct-hallway-864.appspot.com/rest/RegistrationService";
		try {
			if (UserEntity.getUserWithName(uname) != null) {
				return "The User Name is Already Taken ";
			}
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
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";
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
		return "Failed";
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
		String serviceUrl = "http://direct-hallway-864.appspot.com/rest/LoginService";
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
			if (firstTime == true) {
				map.put("message",
						"Welcome " + user.getName());
				firstTime = false;
			} else if (sentFriend == true) {
				map.put("message", "your Friend request is sent :D ");
				sentFriend = false;
			} else {
				map.put("message", "mnorna :D ");
			}
			FriendRequests = Friend.getUserFriendRequests(userData.getName());
			Friends = Friend.getUserFriends(userData.getName());
			UserSentRequests = Friend.getUserSentRequests(userData.getName());
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
		String serviceUrl = "http://direct-hallway-864.appspot.com/rest/SendFriendRequestService";
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

			if (object.get("Status").equals("Failed"))
				return Response.ok(new Viewable("/jsp/SendFriendRequest")).build();
			if (firstTime == true) {
				map.put("message",
						"Welcome " + userData.getName());
				firstTime = false;
			} else if (sentFriend == true) {
				map.put("message", "your Friend request is sent :D ");
				sentFriend = false;
			} else {
				map.put("message", "mnorna :D ");
			}
			UserSentRequests = Friend.getUserSentRequests(userData.getName());
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
		String serviceUrl = "http://direct-hallway-864.appspot.com/rest/AcceptService";
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
			map.put("message",
					"Welcome " + userData.getName());
			FriendRequests = Friend.getUserFriendRequests(userData.getName());
			Friends = Friend.getUserFriends(userData.getName());
			UserSentRequests = Friend.getUserSentRequests(userData.getName());
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
}