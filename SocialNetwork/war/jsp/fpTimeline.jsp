<%@page import="com.FCI.SWE.Models.uTimeline"%>
<%@page import="com.FCI.SWE.Models.fpTimeline"%>
<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>${it.message} Profile</title>

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
		<form action="/social/createPostOnFP" method="POST">
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
				for (int i = fpTimeline.getAllPosts(UserController.fpName).size()-1; i >=0 ; i--) {
			%>
			<TR ALIGN="LEFT">
				<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I>
							<%
								String poster = fpTimeline.getAllPosts(UserController.fpName).get(i).getPoster();
								String content = fpTimeline.getAllPosts(UserController.fpName).get(i).getContent();
								String feeling= fpTimeline.getAllPosts(UserController.fpName).get(i).getFeeling();
								int nLikes = fpTimeline.getAllPosts(UserController.fpName).get(i).getnLikes();
							%> <B> <% out.println(poster + " : "); %>
			         		</B> <% out.println(content); %> <br>
			         		<%
			         		if (!feeling.equals("notValid") && (!feeling.equals("")))
			         			{%>
					         	   <FONT COLOR="#808080" SIZE="3"> <B> -Feeling: </B> <% out.print(feeling); %> </FONT>
			         		<%	}
			         		%>
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