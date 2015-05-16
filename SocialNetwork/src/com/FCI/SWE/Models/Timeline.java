package com.FCI.SWE.Models;

/**
 * this abstract class of all timeline
 * @author Nur
 *
 */
public abstract class Timeline {
	
	String profilePicture;
	String coverPhoto;
	
	/**
	 * Constructor 
	 */
	public Timeline(){}
	/**
	 * constructor
	 * @param profilePicture
	 * @param coverPhoto
	 */
	public Timeline(String profilePicture, String coverPhoto)
	{
		this.coverPhoto = coverPhoto;
		this.profilePicture = profilePicture;
	}
}
