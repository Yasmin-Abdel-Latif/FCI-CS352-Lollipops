package com.FCI.SWE.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.FCI.SWE.Controller.UserController;
import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TestService {

	String webServiceLink = "http://localhost:8080/rest/";
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@BeforeClass
	public void setUp() {
		helper.setUp();
	}

	@AfterClass
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void requestServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		String serviceUrl = webServiceLink + "SendFriendRequestService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friend=" + "Huda";
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
	
	@Test
	public void AcceptServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		String serviceUrl = webServiceLink + "AcceptService";
		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "fname=" + "Huda";
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
	
	@Test
	public void ChatMsgServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		String serviceUrl = webServiceLink + "ChatMsgService";

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friendsNames=" + "yasmin" + "&chatMsg=" + "Hello!";
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
	
	@Test
	public void CreateConversationServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		String serviceUrl = webServiceLink + "conversationService";

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "friendsNames=" + "Huda;yasmin;Nur Alwani" + "&conversationName=" + "lollipops";
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
	
	@Test
	public void CreatePageServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		String serviceUrl = webServiceLink + "createpageService";

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "pageName=" + "Lollipops3" + "&pageType=" + "Educational"
					+ "&pageCatg=" + "society of FCIans" ;
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
	
	@Test
	public void CreatePostServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		String serviceUrl = webServiceLink + "createPostService";

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "postContent=" + "when in doubt know your way out";
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
	
	@Test
	public void LikePostServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		String serviceUrl = webServiceLink + "postlikeService";

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "ID=" + "1";
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
	
	@Test
	public void GroupMsgServiceTest() throws ParseException {
		/*
		 * string json connect("URL",POST,parameters,Encoding) json =
		 * {status:ok}
		 */
		UserEntity user = new UserEntity("MohamedSamir",
				"m.samir@fci-cu.edu.eg", "123");

		UserController.userData = user;
		String serviceUrl = webServiceLink + "SendGroupMsgService";

		try {
			URL url = new URL(serviceUrl);
			String urlParameters = "conversationName=" + "lollipops" + "&groupMsg=" + "we have meeting tomorrow";
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
			System.out.println(object.toString());
			System.out.println(UserController.echo);
			Assert.assertEquals(object.get("Status"), "OK");
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
}
