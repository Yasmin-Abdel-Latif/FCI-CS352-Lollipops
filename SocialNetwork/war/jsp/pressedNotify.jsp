<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Services.Service"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Notifications</title>
</head>
<body background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<center>
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">${it.message}</FONT></B>
		</p>
	</center>
	<br>
	<div align="center">
		<table class="clickable" BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="750" CELLSPACING="1" CELLPADDING="3">
			<%
				for (int i = 0; i < Service.messages.size(); i++) {
			%>
			<TR>
				<TD BGCOLOR="WHITE">
				<FONT COLOR="#65267a" SIZE="4"><I><B>
				<%
					String message = Service.messages.get(i);
					out.println(message);
				%>
				</B></I></FONT>
				</TD>
			</TR>
			<%
				}
			%>
		</table>
		<br>
<!-- 		<form action="/social/seenNotifications" method= "POST"> -->
<!-- 			<input type="image" -->
<!-- 				src="https://lh3.googleusercontent.com/-wAhXPNfCuGA/VSPt6fnj5QI/AAAAAAAAAMk/CXYn6j_qpXU/w600-h270-no/Seen.png" -->
<!-- 				alt="Submit" width="230px" height="65"> -->
<!-- 		</form> -->
	</div>
</body>
</html>