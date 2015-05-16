package com.FCI.SWE.Models;

import java.util.ArrayList;


/** <h1>Notification class</h1>
* <p>
* This class will act as an interface command class 
* </p>
*
* @author  Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
*         Ahmed, Huda Mohammed
* @version 1.0
* @since 9 - 4 - 2015
*/
public interface Notification {
	/**
	 * add new notification to data store
	 */
	public void addNotification();
	/**
	 * Action function enters the data store and get all related records to the pressed 
	 * notification
	 * @param ID the notification ID
	 * @return ArrayList of all the history of this notification
	 */
	public ArrayList<String> pressedNotification(String ID);
	/**
	 * Action function enters the data store and change the notifications of 
	 * this user to seen notification
	 */
	public void seenNotification();
	public String toString();
}
