package com.FCI.SWE.Models;

import java.util.ArrayList;

/**
 * this is the abstract class for privacy
 * @author Nur
 *
 */
public interface Privacy {
	/**
	 * 
	 * @param posts 
	 *              arraylist of all posts that will be displayed to the timeline viewer
	 * @param ownerName
	 *                  name of the owner of the timeline
	 * @param viewerName
	 *                  name of the viewer of the timeline
	 */
	public ArrayList<Post> fillAccToPrivacy(ArrayList<Post> posts,String ownerName,String viewerName);

}
