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
	
	$('#tableId').on('click', '.btn', function()
	{
		var id = $(this).closest('tr').find('td:first').text().substring(0, $(this).closest('tr').find('td:first').text().indexOf("-"));
		$('input[name="ID"]').val(id);
		$( "#target" ).submit();
	});
	
	$('#tableId').on('click', '.like', function()
	{
		var id = $(this).closest('tr').find('td:first').text().substring(0, $(this).closest('tr').find('td:first').text().indexOf("-"));
		$('input[name="ID"]').val(id);
		$( "#target" ).attr('action', '/social/likePost');
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
		<form action="/social/createPostOnFP" method="POST">
			<textarea rows="2" cols="40" name="postContent"
				placeholder="What's on your mind?"
				style="font-size: 18px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;"></textarea>
			<input type="image"
				src="http://icons.iconarchive.com/icons/uiconstock/dynamic-flat-android/128/check-icon.png"
				alt="Submit" width="65px" height="65"><br>
		</form>
		<br>
		
		<form id="target" action="/social/sharePost" method="POST">
			<TABLE id="tableId" BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="500"
				CELLSPACING="1" CELLPADDING="3">
				<%
					for (int i = FriendPageTimeline.getAllPosts(UserController.fpName).size()-1; i >=0 ; i--) {
				%>
				<TR ALIGN="LEFT">
					<TD BGCOLOR="WHITE">
						<FONT COLOR="#65267a" SIZE="4">
							<I>
								<%
									String poster = "";
									String content = "";
									String feeling = "";
									int likeID = 0;
									int nLikes = 0;
									int ID = 0;
									ID = FriendPageTimeline.getAllPosts(UserController.fpName).get(i).getiD();
									poster = FriendPageTimeline.getAllPosts(UserController.fpName).get(i).getPoster();
									content = FriendPageTimeline.getAllPosts(UserController.fpName).get(i).getContent();
									feeling= FriendPageTimeline.getAllPosts(UserController.fpName).get(i).getFeeling();
									likeID = FriendPageTimeline.getAllPosts(UserController.fpName).get(i).getnLikes();
									nLikes = Like.nLikeByID(likeID);
								%> 
								<FONT COLOR="WHITE" SIZE="4">
								<%
									out.println(ID + "-");
								%>
								</FONT>
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
					         		if (!feeling.equals("notValid") && (!feeling.equals("")))
					         		{
				         		%>
						         	   <FONT COLOR="#808080" SIZE="3"> <B> -Feeling: </B> <% out.print(feeling); %> </FONT>
				         		<%	
				         			}
				         		%>
				         	   	<FONT COLOR="#808080" SIZE="3"> <B> Likes: </B> <% out.println(nLikes); %> </FONT>
							</I>
						</FONT>
						<input class="btn" type="Submit" id="shareId" value="Share" 
							style="background: #e5bdf2;
							border: 1px solid #e5bdf2;
							cursor: pointer;
							border-radius: 2px;
							color: #ffffff;
							font-size: 16px;
							font-weight: 400;
							padding: 6px;"/>
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
					for (int i = FriendPageTimeline.getAllSharedPosts(UserController.fpName).size()-1; i >=0 ; i--) {
				%>
				<TR ALIGN="LEFT">
					<TD BGCOLOR="WHITE">
						<FONT COLOR="#65267a" SIZE="4">
							<I>
								<%
									String poster = "";
									String content = "";
									String feeling = "";
									int likeID = 0;
									int nLikes = 0;
									int ID = 0;
									ID = FriendPageTimeline.getAllSharedPosts(UserController.fpName).get(i).getiD();
									poster = FriendPageTimeline.getAllSharedPosts(UserController.fpName).get(i).getPoster();
									content = FriendPageTimeline.getAllSharedPosts(UserController.fpName).get(i).getContent();
									feeling= FriendPageTimeline.getAllSharedPosts(UserController.fpName).get(i).getFeeling();
									likeID = FriendPageTimeline.getAllSharedPosts(UserController.fpName).get(i).getnLikes();
									nLikes = Like.nLikeByID(likeID);
								%> 
								<FONT COLOR="WHITE" SIZE="4">
								<%
									out.println(ID + "-");
								%>
								</FONT>
								<B> 
								<% 
									out.println(UserController.fpName + " Shared " + poster + "'s Post : "); 
								%>
				         		</B> 
				         		<% 
				         			out.println(content); 
				         		%> 
				         		<br>
				         		<%
					         		if (!feeling.equals("notValid") && (!feeling.equals("")))
					         		{
				         		%>
						         	   <FONT COLOR="#808080" SIZE="3"> <B> -Feeling: </B> <% out.print(feeling); %> </FONT>
				         		<%	
				         			}
				         		%>
				         	   	<FONT COLOR="#808080" SIZE="3"> <B> Likes: </B> <% out.println(nLikes); %> </FONT>
							</I>
						</FONT>
						<input class="btn" type="Submit" id="shareId" value="Share" 
							style="background: #e5bdf2;
							border: 1px solid #e5bdf2;
							cursor: pointer;
							border-radius: 2px;
							color: #ffffff;
							font-size: 16px;
							font-weight: 400;
							padding: 6px;"/>
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