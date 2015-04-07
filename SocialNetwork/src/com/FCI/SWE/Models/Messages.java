package com.FCI.SWE.Models;
public abstract class Messages {
	
	public static String sender;
	Boolean type; // 0: one to one - 1: one to many
	String reciever;
	String msg;
	
	public Messages(String reciever, String sender, String msg) 
	{
		this.msg = msg;
		this.reciever = reciever;
		Messages.sender = sender;
	}
	public Boolean sendMessage()
	{
		System.out.println("I'm The Parent :P");
		return true;
	}
}
