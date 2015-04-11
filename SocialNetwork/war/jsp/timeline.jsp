<%@page import="com.FCI.SWE.Models.uTimeline"%>
<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>My Profile</title>

</head>
<body
	background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<center>
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">${it.message}</FONT></B>
		</p>
		</center>
	<div ALIGN="center">

		<br> <br>
		<form action="/social/createPost" method="POST">
			<textarea rows="2" cols="40" name="postContent"
				placeholder="What's on your mind?"
				style="font-size: 18px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;"></textarea>
			<input type="image"
				src="http://icons.iconarchive.com/icons/uiconstock/dynamic-flat-android/128/check-icon.png"
				alt="Submit" width="65px" height="65"><br>
		</form>
		<br>
		
		
		<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="500"
			CELLSPACING="1" CELLPADDING="3">
			<%
				for (int i = UserController.timeline.getAllPosts(UserController.userData.getName()).size()-1; i >=0 ; i--) {
			%>
			<TR ALIGN="LEFT">
				<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I>
							<%
								String poster = UserController.timeline.getPosts().get(i).getPoster();
								String content = UserController.timeline.getPosts().get(i).getContent();
								int nLikes = UserController.timeline.getPosts().get(i).getnLikes();
							%> <B> <% out.println(poster + " : "); %>
			         		</B> <% out.println(content); %> <br>
			         	   <FONT COLOR="#808080" SIZE="3"> <B> Likes: </B> <% out.println(nLikes); %> </FONT>
					</I></FONT></TD>
			</TR>
			<%
				}
			%>

		</TABLE>

	</div>
	<div style="width: 100%; overflow: auto;">
		<div style="position: absolute; left: 0; width: 20%;">
			<div ALIGN="center"></div>
		</div>
	</div>
</body>
</html>