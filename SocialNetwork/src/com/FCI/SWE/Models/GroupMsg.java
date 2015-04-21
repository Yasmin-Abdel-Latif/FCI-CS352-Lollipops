package com.FCI.SWE.Models;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.Controller.UserController;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
/**
 * <h1>Group Message class</h1>
 * <p>
 * This class will act as a model to chat with a group of friends via exchanging messages
 * </p>
 *
 * @author  Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
 *         Ahmed, Huda Mohammed
 * @version 1.0
 * @since 9 - 4 - 2015
 */
public class GroupMsg extends Messages{
	
	static ArrayList<String> recievers;

	/**
	 * Constructor accepts GroupMsg class data
	 * 
	 * @param cName
	 *            conversation name
	 * @param msg
	 *            message content
	 */
	public GroupMsg(String cName, String sender, String msg) 
	{
		super(cName, sender, msg);
		type = true;
	}

	/**
	 * This method will send a message
	 * @return Boolean indicating the state
	 */
	public Boolean sendMessage()
	{
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

	/**
	 * 
	 * This method will get the conversation with the specified name
	 * @param cName
	 *             conversation name
	 *             
	 * @return GroupMsg
	 */
	
	public static GroupMsg getConversationWithName(String cName) 
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("GroupMessages");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) 
		{
			if (entity.getProperty("ID").toString().equals(cName)) 
			{
				GroupMsg returnedConversation = new GroupMsg(entity.getProperty("ID").toString(), entity.getProperty("Creator").toString(), "");
				return returnedConversation;
			}
		}

		return null;
	}

	/**
	 * 
	 * This method will save the conversation vars in the datastore
	 * @param friendsNames
	 *                    names of the receivers
	 *             
	 * @return Boolean indicating the status
	 */
	
	public Boolean saveConversation(String friendsNames)
	{
		recievers = new ArrayList<String>();
		String[] FriendsNames = friendsNames.split(";");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Entity conv = new Entity("GroupMessages", reciever);
	    
		conv.setProperty("ID", reciever);
		conv.setProperty("Creator", UserController.userData.getName());
		conv.setProperty("nRecievers", FriendsNames.length);
		
		String r = "Reciever";
		for(int i = 1 ; i <= FriendsNames.length ; i++)
		{
			recievers.add(FriendsNames[i-1]);
			conv.setProperty((String)(r+i), FriendsNames[i-1]);
		}
		datastore.put(conv);
		return true;
	}
	
	/**
	 * 
	 * This method will get all the receivers from the datastore
	 * @param cName
	 *              conversation name
	 *             
	 * @return arraylist of all receivers of a certain conversation
	 */
	
	@SuppressWarnings("deprecation")
	public static ArrayList<String> getAllRecievers(String cName)
	{
		recievers = new ArrayList<String>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("GroupMessages");
		query.addFilter("ID", FilterOperator.EQUAL, cName);
		PreparedQuery pq = datastore.prepare(query);
		Entity GMsg = pq.asSingleEntity();
		
		String r = "Reciever";
		for(int i = 1 ; i <= Integer.parseInt(GMsg.getProperty("nRecievers").toString()) ; i++)
		{
			String RecieverName = GMsg.getProperty((String)(r+i)).toString();
			recievers.add(RecieverName);
		}
		return recievers;
	}
	
	
	/** 
	 * This method will get the creator of a certain conversation
	 * @param cName
	 *             conversation name
	 * @return creator of the conversation in a string format
	 */	
	@SuppressWarnings("deprecation")
	public static String getCreator(String cName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("GroupMessages");
		query.addFilter("ID", FilterOperator.EQUAL, cName);
		PreparedQuery pq = datastore.prepare(query);
		Entity GMsg = pq.asSingleEntity();
		
		return GMsg.getProperty("Creator").toString();
	}
	
	
	/** 
	 * This method will attach a certain friend to a certain conversation
	 * @param friendName
	 *             name of the friend whom we wish to attach to this conversation
	 * @param cName
	 *             conversation name
	 * @return Boolean indicating the status of the attachment
	 */
	@SuppressWarnings("deprecation")
	public Boolean attach(String friendName, String cName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("GroupMessages");
		query.addFilter("ID", FilterOperator.EQUAL, cName);
		PreparedQuery pq = datastore.prepare(query);
		Entity GMsg = pq.asSingleEntity();
		
		if(!getAllRecievers(cName).contains(friendName))
		{
			if(GMsg.getProperty("Creator").equals(UserController.userData.getName()))
			{
				recievers.add(friendName);
				GMsg.setProperty("nRecievers", recievers.size());
				
				String r = "Reciever" + recievers.size();
				GMsg.setProperty(r, friendName);
				datastore.put(GMsg);
				return true;
			}
			else
			{
				UserController.echo = "You have no authority";//Not the creator
				return false;
			}
		}
		else
		{
			UserController.echo = friendName + " already in this conversation";
			return false;
		}
	}
	
	/** 
	 * This method will detach a certain friend from a certain conversation
	 * @param friendName
	 *             name of the friend whom we wish to detach from this conversation
	 * @param cName
	 *             conversation name
	 * @return Boolean indicating the status of the attachment
	 */
	@SuppressWarnings("deprecation")
	public Boolean deattach(String friendName, String cName)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Query query = new Query("GroupMessages");
		query.addFilter("ID", FilterOperator.EQUAL, cName);
		PreparedQuery pq = datastore.prepare(query);
		Entity GMsg = pq.asSingleEntity();
		
		if(getAllRecievers(cName).contains(friendName))
		{
			if(GMsg.getProperty("Creator").equals(UserController.userData.getName()) || UserController.userData.getName().equals(friendName))
			{
				recievers.remove(friendName);
				String r = "Reciever";
				for(int i = 1 ; i <= recievers.size() ; i++)
				{
					GMsg.setProperty((String)(r+i), recievers.get(i-1));
				}
				
				GMsg.setProperty("nRecievers", recievers.size());
				int oldReciverPos = recievers.size()+1;
				r += "" + oldReciverPos;
				GMsg.setProperty(r, null);
				datastore.put(GMsg);
				UserController.echo = friendName + " deattached from the conversation";
				return true;
			}
			else
			{
				UserController.echo = "you aren't the creator of this conversation";
				return false;
			}
			
		}
		else
		{
			UserController.echo = friendName + " already not in this conversation";
			return false;
		}
	}
}
