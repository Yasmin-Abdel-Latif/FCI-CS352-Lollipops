<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Hashtag page</title>

</head>
<body
	background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<center>
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">HashTag Timeline <br>${it.message}</FONT></B>
		</p>
	</center>
	<div style="position: absolute; left: 20%; right: 20%; top: 10%">
		<div ALIGN="center">
		<br>
		<br>
			<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="500"
				CELLSPACING="1" CELLPADDING="3">
				<%
					for (int i = UserController.hashTimeline.posts.size()-1; i >= 0; i--) {
				%>
				<TR ALIGN="LEFT">
					<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I>
								<%
									String poster = UserController.hashTimeline.posts.get(i)
												.getPoster();
										String content = UserController.hashTimeline.posts.get(i)
												.getContent();
										int nLikes = UserController.hashTimeline.posts.get(i)
												.getnLikes();
								%> <B> <%
 	out.println(poster + " : ");
 %>
							</B> <%
 	out.println(content);
 %> <br> <FONT COLOR="#808080" SIZE="3"> <B> Likes: </B> <%
 	out.println(nLikes);
 %>
							</FONT>
						</I></FONT></TD>
				</TR>
				<%
					}
				%>

			</TABLE>

		</div>
	</div>
	<div style="position: absolute; right: 0; width: 20%;">
		<div ALIGN="center">
			<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="225"
				CELLSPACING="1" CELLPADDING="3">
				<TR>
					<TH COLSPAN="3"><FONT COLOR="#65267a" SIZE="4"><B>top
								10 Hashtag</B></FONT>
				</TR>
				<%
					for (int i = UserController.hashTimeline.hash.size()-1; i >=0; i--) {
				%>
				<TR ALIGN="CENTER">
					<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I><B>
									<%
										String Hash = UserController.hashTimeline.hash.get(i).getHash();
											out.println(Hash);
										int nPosts = UserController.hashTimeline.hash.get(i).getnHashes();
									%>
									 <br> <FONT COLOR="#808080" SIZE="3"> <B> number of posts: </B> <%
 										out.println(nPosts);
 %>
							</B></I></FONT></TD>
				</TR>
				<%
					}
				%>
			</TABLE>

			<br>

		</div>
	</div>
</body>
</html>