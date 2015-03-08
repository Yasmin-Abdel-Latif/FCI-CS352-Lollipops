<%@ page import="java.util.*" %>
<%@ page import="com.FCI.SWE.Controller.UserController" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p>  ${it.message} </p>
<br>
<TABLE BORDER="3" BORDERCOLOR="#C000C0" BGCOLOR="#FFDDFF" WIDTH="500" CELLSPACING="1" CELLPADDING="3">
	<TR>
		<TH COLSPAN = "3"><FONT COLOR="#D000D0" SIZE="4"><B>Your Friend Requests</B></FONT>
	</TR>
<%
String url = "";
for(int i = 0 ; i < UserController.Friends.size() ; i++)
{
%>
	<TR ALIGN="CENTER">
		<TD BGCOLOR="WHITE"><FONT COLOR="#4080FF" SIZE="4"><I><B><% String friend = UserController.Friends.get(i);out.println(friend); %></B></I></FONT></TD>
	</TR>
<%
}
%>
</TABLE>
<br>
<form action="/social/Accept" method = "POST">
 	Accepted Friend Request Name : <input type="text" name="fname" /> <br><br>
  	<input type="submit" value="Accept"> <br><br>
</form>
<br>
<form action="/social/request" method="post">
 	Friend Name : <input type="text" name="friend" /> <br><br>
  	<input type="submit" value="send friend request"> <br><br>
  	<a href="/social/signout">Sign out</a> <br>
</form>
</body>
</html>