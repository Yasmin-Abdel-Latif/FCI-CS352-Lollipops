package com.FCI.SWE.Models;

import java.util.*;

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

public class Hashtag implements Comparable<Hashtag>{

	String hash;
	int nHashes;

	public Hashtag(String hash) {
		super();
		this.hash = hash;
	}

	/**
	 * this function is used to search the hashtag table and look for a specific
	 * hashtag
	 * 
	 * @return Boolean false if the hashtag is not exist true if it exist
	 */
	public Boolean checkHashTag() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashTag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("hashtag").toString().equals(hash)) {
				return true;
			}

		}

		return false;
	}

	/**
	 * this method is used to save the hashtag for the first time
	 * 
	 * @param postID the id of the post in table post
	 * @return
	 */
	public Boolean saveHashtag(int postID) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashTag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		Entity hashEntity = new Entity("HashTag", list.size() + 1);

		hashEntity.setProperty("hashtag", hash);
		hashEntity.setProperty("nHashes", 1);
		hashEntity.setProperty("post1", postID);
		datastore.put(hashEntity);
		return true;
	}

	/**
	 * this method is used to update the information of n already exist hashtag 
	 * @param postID the id of post
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Boolean upDateHashtag(int postID) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("HashTag");
		query.addFilter("hashtag", FilterOperator.EQUAL, hash);
		PreparedQuery pq = datastore.prepare(query);
		Entity hashEntity = pq.asSingleEntity();
		int nHashes = Integer.parseInt(hashEntity.getProperty("nHashes").toString());
		nHashes++;
		String postNumber = (String) ("post" + Integer.toString(nHashes));
		hashEntity.setProperty("nHashes", nHashes);
		hashEntity.setProperty(postNumber, postID);
		datastore.put(hashEntity);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Integer> allPost()
	{
		ArrayList <Integer> postsID = new ArrayList <Integer>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("HashTag");
		query.addFilter("hashtag", FilterOperator.EQUAL, hash);
		PreparedQuery pq = datastore.prepare(query);
		Entity hashEntity = pq.asSingleEntity();
		int nHashes = Integer.parseInt(hashEntity.getProperty("nHashes").toString());
		String post = "post";
		for (int i=1;i<=nHashes ; i++)
		{
			postsID.add( Integer.parseInt(hashEntity.getProperty(post+i).toString()));
		}
		return postsID ; 
		
	}
	
	public ArrayList <Hashtag> getAllHashtag ()
	{
		ArrayList <Hashtag> allHashtag = new ArrayList <Hashtag>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("HashTag");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable())
		{
			String hashName = entity.getProperty("hashtag").toString();
			int nhash = Integer.parseInt(entity.getProperty("nHashes").toString());
			Hashtag hashtag = new Hashtag (hashName,nhash);
			allHashtag.add(hashtag);
		}
		return allHashtag;
				
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getnHashes() {
		return nHashes;
	}

	public void setnHashes(int nHashes) {
		this.nHashes = nHashes;
	}

	public Hashtag(String hash, int nHashes) {
		super();
		this.hash = hash;
		this.nHashes = nHashes;
	}

	@Override
	public int compareTo(Hashtag o) {
		return this.nHashes - o.nHashes;
	}

}