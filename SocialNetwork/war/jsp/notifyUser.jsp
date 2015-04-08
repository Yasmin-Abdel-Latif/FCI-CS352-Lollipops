<%@ page import="java.util.*" %>
<%@ page import="com.FCI.SWE.Controller.UserController" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>

</head>
<body background= "http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
<center><p><B><FONT COLOR="#FFFFFF" SIZE="6">Notifications</FONT></B></p></center>
<br>

<div align="center">
<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="750" CELLSPACING="1" CELLPADDING="3">
<%
for(int i = 0 ; i < UserController.userSentRequests.size() ; i++)
{
%>
	<TR ALIGN="CENTER">
		<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I><B>
		<% 
		String friend = UserController.userSentRequests.get(i);
		out.println(friend); 
		%>
		</B></I></FONT></TD>
	</TR>
<%
}
for(int i = 0 ; i < UserController.friends.size() ; i++)
{
%>
	<TR ALIGN="CENTER">
		<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I><B>
		<%
		String friend = UserController.friends.get(i);
		out.println(friend); 
		%>
		</B></I></FONT></TD>
	</TR>
<%
}
%>
</TABLE>

<br>
<form action="/social/notifyService">
	<input type="image" src="https://lh3.googleusercontent.com/-wAhXPNfCuGA/VSPt6fnj5QI/AAAAAAAAAMk/CXYn6j_qpXU/w600-h270-no/Seen.png" alt="Submit" width="230px" height="65">
</form>
</div>
</body>
</html>