package com.FCI.SWE.Models;

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
 * 
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
	 * 
	 * @param userName
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
	 * 
	 * @param ID
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
			String RecieverName = likeEntity.getProperty((String) (r + i))
					.toString();
			users.add(RecieverName);
		}
	}
	
	/**
	 * 
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

	public int nextLikeID() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("Like");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		return list.size() + 1;

	}

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

	public Like(String postOrPage, int objID, int nLikes, int likeID,
			ArrayList<String> users) {
		super();
		this.postOrPage = postOrPage;
		this.objID = objID;
		this.nLikes = nLikes;
		this.likeID = likeID;
		this.users = users;
	}

	public Like(String postOrPage, int objID) {
		super();
		this.postOrPage = postOrPage;
		this.objID = objID;
	}

	public Like() {
		// TODO Auto-generated constructor stub
	}

	public String getPostOrPage() {
		return postOrPage;
	}

	public void setPostOrPage(String postOrPage) {
		this.postOrPage = postOrPage;
	}

	public int getObjID() {
		return objID;
	}

	public void setObjID(int objID) {
		this.objID = objID;
	}

	public int getnLikes() {
		return nLikes;
	}

	public void setnLikes(int nLikes) {
		this.nLikes = nLikes;
	}

	public int getLikeID() {
		return likeID;
	}

	public void setLikeID(int likeID) {
		this.likeID = likeID;
	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}
}
