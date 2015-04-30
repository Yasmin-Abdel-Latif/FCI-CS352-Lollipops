package com.FCI.SWE.Models;

public abstract class Timeline {
	
	String profilePicture;
	String coverPhoto;
	
	public Timeline(){}
	public Timeline(String profilePicture, String coverPhoto)
	{
		this.coverPhoto = coverPhoto;
		this.profilePicture = profilePicture;
	}
}
