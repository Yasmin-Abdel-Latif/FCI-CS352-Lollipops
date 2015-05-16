package com.FCI.SWE.Models;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

/**
 * this class is to control all the likes 
 * @author mariam
 *
 */
public class Like {

	String postOrPage;
	int objID; // the ID of the object
	int nLikes;
	int likeID;
	ArrayList<String> users = new ArrayList<String>(); // users who like

	/**
	 * 
	 * @param userName
	 * @return
	 */
	
	public int likeObj(String userName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Like");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		int size = list.size() + 1;
		Entity post = new Entity("Like", size);

		post.setProperty("likeID", size);
		post.setProperty("postOrPage", postOrPage);
		post.setProperty("objID", objID);
		post.setProperty("nLikes", 1);
		post.setProperty("user1", userName);
		datastore.put(post);
		users.add(userName);
		return size;
	}

	/**
	 * this method is to add user name to like post
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int likePost(String userName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("Like");
		query.addFilter("objID", FilterOperator.EQUAL, objID);
		query.addFilter("postOrPage", FilterOperator.EQUAL, postOrPage);
		PreparedQuery pq = datastore.prepare(query);
		Entity likeE = pq.asSingleEntity();
		users.add(userName);
		likeE.setProperty("nLikes", users.size());
		String r = "user" + users.size();
		likeE.setProperty(r, userName);
		likeID = Integer.parseInt(likeE.getProperty("likeID").toString());
		datastore.put(likeE);
		return likeID;
	}

	/**
	 * this method is to like page
	 * @param userName
	 * 	the user name who like the page
	 * @return
	 * 		the ID of the like
	 */
	@SuppressWarnings("deprecation")
	public int likePage(String userName) {
		allLikeUserByID(likeID);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("Like");
		query.addFilter("likeID", FilterOperator.EQUAL, likeID);
		PreparedQuery pq = datastore.prepare(query);
		Entity likeE = pq.asSingleEntity();
		users.add(userName);
		likeE.setProperty("nLikes", users.size());
		String r = "user" + users.size();
		likeE.setProperty(r, userName);
		likeID = Integer.parseInt(likeE.getProperty("likeID").toString());
		datastore.put(likeE);
		return likeID;
	}


	/**
	 * this method is to add all the users that like this ID to the users ArrayList
	 * @param ID
	 * 		ID of the like object
	 */
	@SuppressWarnings("deprecation")
	public void allLikeUserByID(int ID) {

		users = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("Like");
		query.addFilter("likeID", FilterOperator.EQUAL, ID);
		PreparedQuery pq = datastore.prepare(query);
		Entity likeEntity = pq.asSingleEntity();
		String r = "user";
		for (int i = 1; i <= Integer.parseInt(likeEntity.getProperty("nLikes")
				.toString()); i++) {
			String likeUserName = likeEntity.getProperty((String) (r + i))
					.toString();
			users.add(likeUserName);
		}
	}
	
	/**
	 * this method is to add all the like user names to the users arrayList by there objID
	 * and the type of the object
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void allLikeUser() {

		users = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("Like");
		query.addFilter("objID", FilterOperator.EQUAL, objID);
		query.addFilter("postOrPage", FilterOperator.EQUAL, postOrPage);
		PreparedQuery pq = datastore.prepare(query);
		Entity likeEntity = pq.asSingleEntity();
		String r = "user";
		for (int i = 1; i <= Integer.parseInt(likeEntity.getProperty("nLikes")
				.toString()); i++) {
			String RecieverName = likeEntity.getProperty((String) (r + i))
					.toString();
			users.add(RecieverName);
		}
	}

	/**
	 * check if this obj already exist
	 * 
	 * @return true if exist falxe if not
	 */
	public Boolean checkLikeID() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Like");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("likeID").toString().equals(likeID)) {
				return true;
			}

		}

		return false;
	}

	/**
	 * check if this obj already exist
	 * 
	 * @return true if exist falxe if not
	 */
	public Boolean checkLike() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Like");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("objID").toString().equals(objID)
					&& entity.getProperty("postOrPage").toString()
							.equals(postOrPage)) {
				return true;
			}

		}

		return false;
	}

	/**
	 * get the next like id to be added to the table
	 * @return
	 * 	int ID
	 */
	public int nextLikeID() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Like");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		return list.size() + 1;

	}

	/**
	 * get the number of likes in an object using its id
	 * @param ID
	 * 	the object ID
	 * @return
	 * 	number of likes
	 */
	@SuppressWarnings("deprecation")
	public static int nLikeByID(int ID) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("Like");
		query.addFilter("likeID", FilterOperator.EQUAL, ID);
		PreparedQuery pq = datastore.prepare(query);
		Entity likeEntity = pq.asSingleEntity();
		int nlikes = Integer.parseInt(likeEntity.getProperty("nLikes")
				.toString());
		return nlikes;

	}

	/**
	 * {@link Constructor}
	 * @param postOrPage
	 * @param objID
	 * @param nLikes
	 * @param likeID
	 * @param users
	 */
	public Like(String postOrPage, int objID, int nLikes, int likeID,
			ArrayList<String> users) {
		super();
		this.postOrPage = postOrPage;
		this.objID = objID;
		this.nLikes = nLikes;
		this.likeID = likeID;
		this.users = users;
	}

	/**
	 * Contructor
	 * @param postOrPage
	 * @param objID
	 */
	public Like(String postOrPage, int objID) {
		super();
		this.postOrPage = postOrPage;
		this.objID = objID;
	}

	/**
	 * empty Constructor
	 */
	public Like() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *  getter postOrpage
	 * @return
	 */
	public String getPostOrPage() {
		return postOrPage;
	}

	/**
	 * setter postorPage
	 * @param postOrPage
	 */
	public void setPostOrPage(String postOrPage) {
		this.postOrPage = postOrPage;
	}

	/**
	 * getter objID
	 * @return
	 */
	public int getObjID() {
		return objID;
	}

	/**
	 * setter objID
	 * @param objID
	 */
	public void setObjID(int objID) {
		this.objID = objID;
	}

	/**
	 * getter nLikes
	 * @return
	 */
	public int getnLikes() {
		return nLikes;
	}

	/**
	 * setter nLikes
	 * @param nLikes
	 */
	public void setnLikes(int nLikes) {
		this.nLikes = nLikes;
	}

	/**
	 * getter likeID
	 * @return
	 */
	public int getLikeID() {
		return likeID;
	}

	/**
	 * setter LikeID
	 * @param likeID
	 */
	public void setLikeID(int likeID) {
		this.likeID = likeID;
	}

	/**
	 * getter users
	 * @return
	 */
	public ArrayList<String> getUsers() {
		return users;
	}

	/**
	 * setter users
	 * @param users
	 */
	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}
}
