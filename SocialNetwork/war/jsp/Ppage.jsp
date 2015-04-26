<%@page import="sun.misc.UCEncoder"%>
<%@page import="com.google.api.server.spi.auth.common.User"%>
<%@page import="com.FCI.SWE.Models.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>${it.message}</title>

</head>
<body
	background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<center>
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">${it.message}</FONT></B> <br>
			<B><FONT COLOR="#FFFFFF" SIZE="4">type : <% if (UserController.pageTimeline.page.getPageName()!=null)
			out.print(UserController.pageTimeline.page.getType()); %></FONT></B>
			<br> <B><FONT COLOR="#FFFFFF" SIZE="4">Category : <% if (UserController.pageTimeline.page.getPageName()!=null)
			out.print(UserController.pageTimeline.page.getCategory()) ;%></FONT></B>
			<br> <B><FONT COLOR="#FFFFFF" SIZE="4">more than  <% if (UserController.pageTimeline.page.getPageName()!=null)
			out.print(Like.nLikeByID(UserController.pageTimeline.page.getLikeID())) ;%> person like this page</FONT></B>

		</p>
	</center>
	<div ALIGN="center">

		<br> <br>
		<form action="/social/likePage" method="POST">
			<input type="image"
				src="<% if (!UserController.pageTimeline.like)
					out.print("http://icons.iconarchive.com/icons/iconsmind/outline/512/Like-icon.png");
				else
					out.print("http://www.mrmai.info/products/musicrank/resources/images/like_rate_vote_ok_thumbs_up.png");
				%>"
				alt="Submit" width="65px" height="65"><br>
		</form>
		<br>


		<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="500"
			CELLSPACING="1" CELLPADDING="3">
			<%
				for (int i = UserController.pageTimeline.posts.size()-1; i >=0 ; i--) {
			%>
			<TR ALIGN="LEFT">
				<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I>
							<%
								String poster = UserController.pageTimeline.posts.get(i).getPoster();
								String content =  UserController.pageTimeline.posts.get(i).getContent();
								//int LikeID = fpTimeline.getAllPosts(UserController.fpName).get(i).getnLikes();
								//int nLikes = Like.nLikeByID(LikeID);
							%> <B> <% out.println(poster + " : "); %>
						</B> <% out.println(content); %> <br> <FONT COLOR="#808080"
							SIZE="3"> <B> Likes: </B> <% out.println(67); %>
						</FONT>
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