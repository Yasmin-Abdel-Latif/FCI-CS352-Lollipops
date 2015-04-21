package com.FCI.SWE.Models;
/** <h1>Messages class</h1>
* <p>
* This abstract class will act as an interface to message classes (both one to one & group chat)
* </p>
*
* @author  Yasmine Abdel Latif, Nour Mohammed Srour Alwani, Mariam Fouad, Salwa
*         Ahmed, Huda Mohammed
* @version 1.0
* @since 9 - 4 - 2015
*/
public abstract class Messages {
	
	public static String sender;
	public static Boolean type; // 0: one to one - 1: one to many
	public static String reciever;
	public static String msg;
	
	/**
	 * Constructor 
	 * 
	 * @param reciever of the message
	 * @param sender of the message
	 * @param msg 
	 */
	public Messages(String reciever, String sender, String msg) 
	{
		Messages.msg = msg;
		Messages.reciever = reciever;
		Messages.sender = sender;
	}
	/**
	 * SendMessage Method
	 * @return Boolean indicating the status
	 */
	public Boolean sendMessage()
	{
		System.out.println("I'm The Parent :P");
		return true;
	}
}
