<%@page import="com.FCI.SWE.Models.*"%>
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

		<form action="/social/setPrivacy" method="POST">
			<select name="privacySelect">
				<option value="notValid">Select Privacy Option</option>	<!-- important -->		
				<option value="Public">Public</option>
				<option value="Private">Private</option>
				<option value="Custom">Custom</option>
		
			</select>
			<input type="text" value="Set Custom List" name ="cList">
			<input type="submit" value="Set Privacy">
		</form>
		
		<form action="/social/setFeelings" method="POST">
			<select name="feelingSelect">
				<option value="notValid">Select Feeling</option>	<!-- important -->		
				<option value="Happy">Happy</option>
				<option value="Relaxed">Relaxed</option>
				<option value="Hyper">Hyper</option>
				<option value="Meh">Meh</option>
				<option value="Sad">Sad</option>
				<option value="Angry">Angry</option>
			</select>
			 <input type="submit" value="Set Feeling">
		</form>

		<br>
		
		
		<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="500"
			CELLSPACING="1" CELLPADDING="3">
			<%
				for (int i = uTimeline.getAllPosts(UserController.userData.getName()).size()-1; i >=0 ; i--) {
			%>
			<TR ALIGN="LEFT">
				<TD BGCOLOR="WHITE">
					<FONT COLOR="#65267a" SIZE="4">
						<I>
							<%
								String poster = uTimeline.getAllPosts(UserController.userData.getName()).get(i).getPoster();
								String content = uTimeline.getAllPosts(UserController.userData.getName()).get(i).getContent();
  								String feeling= uTimeline.getAllPosts(UserController.userData.getName()).get(i).getFeeling();
								int likeID = uTimeline.getAllPosts(UserController.userData.getName()).get(i).getnLikes();
								int nLikes = Like.nLikeByID(likeID);
								int seen = uTimeline.getAllPosts(UserController.userData.getName()).get(i).getSeen();
							%>
							<B>
							<% 
								out.println(poster + " : "); 
							%>
			         		</B> 
			         		<% 
			         			out.println(content); 
			         		%> 
			         		<br>
			         		<%
				         		if (!feeling.equals("notValid"))
			         			{
			         		%>
					         	   <FONT COLOR="#808080" SIZE="3"> <B> -Feeling: </B> <% out.print(feeling); %> </FONT>
				         	<%	
				         		}
			         		%>
			         	   <FONT COLOR="#808080" SIZE="3"> 
			         	   <B> Likes: </B> <% out.println(+nLikes); %> 
			         	    <B> seens: </B> <% out.println(+seen); %></FONT>
						</I>
					</FONT>
				</TD>
			</TR>
			<%
				}
				for (int i = uTimeline.getAllSharedPosts(UserController.userData.getName()).size()-1; i >=0 ; i--) {
			%>
			<TR ALIGN="LEFT">
				<TD BGCOLOR="WHITE">
					<FONT COLOR="#65267a" SIZE="4">
						<I>
							<%
								String poster = uTimeline.getAllSharedPosts(UserController.userData.getName()).get(i).getPoster();
								String content = uTimeline.getAllSharedPosts(UserController.userData.getName()).get(i).getContent();
  								String feeling= uTimeline.getAllSharedPosts(UserController.userData.getName()).get(i).getFeeling();
								int nLikes = uTimeline.getAllSharedPosts(UserController.userData.getName()).get(i).getnLikes();
							%>
							<B>
							<% 
								out.println(UserController.userData.getName() + " Shared " + poster + "'s Post : "); 
							%>
			         		</B> 
			         		<% 
			         			out.println(content); 
			         		%> 
			         		<br>
			         		<%
				         		if (!feeling.equals("notValid"))
			         			{
			         		%>
					         	   <FONT COLOR="#808080" SIZE="3"> <B> -Feeling: </B> <% out.print(feeling); %> </FONT>
				         	<%	
				         		}
			         		%>
			         	   <FONT COLOR="#808080" SIZE="3"> <B> Likes: </B> <% out.print(+nLikes); %> </FONT>
						</I>
					</FONT>
				</TD>
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