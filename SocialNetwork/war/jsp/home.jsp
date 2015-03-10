<%@ page import="java.util.*" %>
<%@ page import="com.FCI.SWE.Controller.UserController" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>

</head>
<body background= "http://www.dualimg.com/wp-content/wallpapers/20130115/ooliizxpm5q734.jpg">
<center><p><B><FONT COLOR="#FFFFFF" SIZE="6">${it.message}</FONT></B></p></center>
<br>
<div align="right">
<TABLE BORDER="3" BORDERCOLOR="#CC0000" BGCOLOR="#FFCCCC" WIDTH="225" CELLSPACING="1" CELLPADDING="3">
	<TR>
		<TH COLSPAN = "3"><FONT COLOR="#CC0000" SIZE="4"><B>Your Friend Requests</B></FONT>
	</TR>
<%
for(int i = 0 ; i < UserController.FriendRequests.size() ; i++)
{
%>
	<TR ALIGN="CENTER">
		<TD BGCOLOR="WHITE"><FONT COLOR="#CC0000" SIZE="4"><I><B>
		<% 
		String friend = UserController.FriendRequests.get(i);
		out.println(friend);
		%>
		</B></I></FONT></TD>
	</TR>
<%
}
%>
	<TR>
		<TH COLSPAN = "3"><FONT COLOR="#CC0000" SIZE="4"><B>Your Sent Requests</B></FONT>
	</TR>
<%
for(int i = 0 ; i < UserController.UserSentRequests.size() ; i++)
{
%>
	<TR ALIGN="CENTER">
		<TD BGCOLOR="WHITE"><FONT COLOR="#CC0000" SIZE="4"><I><B>
		<% 
		String friend = UserController.UserSentRequests.get(i);
		out.println(friend); 
		%>
		</B></I></FONT></TD>
	</TR>
<%
}
%>
	<TR>
		<TH COLSPAN = "3"><FONT COLOR="#CC0000" SIZE="4"><B>Your Friends</B></FONT>
	</TR>
<%
for(int i = 0 ; i < UserController.Friends.size() ; i++)
{
%>
	<TR ALIGN="CENTER">
		<TD BGCOLOR="WHITE"><FONT COLOR="#CC0000" SIZE="4"><I><B>
		<%
		String friend = UserController.Friends.get(i);
		out.println(friend); 
		%>
		</B></I></FONT></TD>
	</TR>
<%
}
%>
</TABLE>

<br>

<form action="/social/Accept" method = "POST" >
 	<input type="text" name="fname" align="right" value="Accepted Friend Request Name" onfocus="this.style.color='#000'; this.value='';"
 	style="font-size:12px;
 	height:25px;
 	width:200px;
 	font-family: 'Exo', sans-serif;
 	background: transparent;
 	border: 1px solid #fff;
	border-radius: 2px;
	color: #fff;
	padding: 6px;"> <br><br>
 	<input type="image" src="http://www.clker.com/cliparts/M/C/n/R/d/n/accept-button-hi.png" alt="Submit" width="230px" height="45"><br><br>
</form>
<br>
<form action="/social/request" method="post">
 	<input type="text" name="friend" value="Friend Name" onfocus="this.style.color='#000'; this.value='';"
 	style="font-size:12px; 
 	height:25px; 
 	width:200px; 
 	font-family: 'Exo', sans-serif; 
 	background: transparent;
 	border: 1px solid #fff;
	border-radius: 2px;
	color: #fff;
	padding: 6px;"> <br><br>
  	<input type="image" src="https://lh3.googleusercontent.com/-GLSU52J7C3A/VP87V3XsrKI/AAAAAAAAAIE/tA0QbPmeSYg/w600-h270-no/ghhray-thin-button-blank-hi.png" alt="Submit" width="230px" height="50"><br><br>
</form>
<form action="/social/signout">
	<input type="image" src="http://www.clker.com/cliparts/H/1/c/C/6/F/gray-login-thin-button-hi.png" alt="Submit" width="230px" height="50"><br><br>
</form>
</div>
</body>
</html>