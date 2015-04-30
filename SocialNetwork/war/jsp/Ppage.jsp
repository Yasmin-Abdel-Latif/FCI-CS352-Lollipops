<%@page import="com.FCI.SWE.Models.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>${it.message} Profile</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
<style>
    table.clickable tr:hover { background-color: White; }
    table.clickable tr > td { cursor: pointer; }
    table.clickable tr > td > a { display: block; text-decoration: none; }
</style>
<script>

jQuery(function () {

	$('#tableId').on('click', '.like', function()
	{
		var id = $(this).closest('tr').find('td:first').text().substring(0, $(this).closest('tr').find('td:first').text().indexOf("-"));
		$('input[name="ID"]').val(id);
		$( "#target" ).submit();
	});
});

</script>
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

		<form id="target" action="/social/likePost" method="POST">
		<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="500"
			CELLSPACING="1" CELLPADDING="3">
			<%
				for (int i = UserController.pageTimeline.posts.size()-1; i >=0 ; i--) {
			%>
			<TR ALIGN="LEFT">
				<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I>
							<%
								int ID = UserController.pageTimeline.posts.get(i).getiD();
								String poster = UserController.pageTimeline.posts.get(i).getPoster();
								String content =  UserController.pageTimeline.posts.get(i).getContent();
								int LikeID = FriendPageTimeline.getAllPosts(UserController.fpName).get(i).getnLikes();
								int nLikes = Like.nLikeByID(LikeID);
							%> 
							<FONT COLOR="WHITE" SIZE="4">
								<%
									out.println(ID + "-");
								%>
								</FONT>
							<B> <% out.println(poster + " : "); %>
						</B> <% out.println(content); %> <br> <FONT COLOR="#808080"
							SIZE="3"> <B> Likes: </B> <% out.println(nLikes); %>
						</FONT>
					</I></FONT>
					<input class="like" type="Submit" id="likeId" value="like" 
							style="background: #e5bdf2;
							border: 1px solid #e5bdf2;
							cursor: pointer;
							border-radius: 2px;
							color: #ffffff;
							font-size: 16px;
							font-weight: 400;
							padding: 6px;"/>
						<input type="hidden" id="ID" name="ID" value="<%=ID%>">
						</TD>
			</TR>
			<%
				}
			%>

		</TABLE>
	</form>
	</div>
	<div style="width: 100%; overflow: auto;">
		<div style="position: absolute; left: 0; width: 20%;">
			<div ALIGN="center"></div>
		</div>
	</div>
</body>
</html>