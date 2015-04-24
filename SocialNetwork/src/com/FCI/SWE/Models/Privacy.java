package com.FCI.SWE.Models;

import java.util.ArrayList;

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
	public void fillAccToPrivacy(ArrayList<Post> posts,String ownerName,String viewerName);

}
