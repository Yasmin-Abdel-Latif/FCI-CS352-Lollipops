package com.FCI.SWE.Models;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class ChatMsg extends Messages {
	
	public ChatMsg(String reciever, String sender, String msg )
	{
		super(reciever, sender, msg);
		type = false;
	}
	public Boolean sendMessage() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("message");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		Entity message = new Entity("message", list.size()+1);

		message.setProperty("Type", type);
		message.setProperty("Sender", sender);
		message.setProperty("Reciever", reciever);
		message.setProperty("Msg", msg);
		message.setProperty("Seen", false);
		datastore.put(message);
		return true;
	}
}
