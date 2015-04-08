<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Insert title here</title>

</head>
<body
	background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<center>
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">${it.message}</FONT></B>
		</p>
	</center>
	<br>
	<div style="width: 100%; overflow: auto;">
		<div style="position: absolute; left: 0; width: 20%;">
			<div ALIGN="center">
				<form action="/social/ChatMsg" method="POST">
					<input type="text" name="friendsNames" value="Reciever Name"
						onfocus="this.value='';"
						style="font-size: 16px; height: 35px; width: 270px; background: transparent; font-family: 'Exo', sans-serif; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;"><br>
					<br>
					<textarea rows="10" cols="25" name="chatMsg"
						style="font-size: 18px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;"></textarea>
					<br> <input type="image"
						src="https://lh3.googleusercontent.com/-Ms2_bL8jeI4/VR_z9n6vHzI/AAAAAAAAAJs/DYbGJFAb8AQ/w600-h270-no/Send.png"
						alt="Submit" width="230px" height="65"><br>
				</form>
				<form action="/social/msg">
					<input type="image"
						src="https://lh5.googleusercontent.com/-L57yI2Cy1nQ/VR_uRQSAXVI/AAAAAAAAAJA/M6ge35wLGLg/w600-h270-no/Message.png"
						alt="Submit" width="230px" height="65"><br>
				</form>
				<form action="/social/notify">
					<input type="image"
						src="https://lh3.googleusercontent.com/-bKC68NVKF98/VSPt17n8hPI/AAAAAAAAAMA/Ouwj5HvxA3s/w600-h270-no/Notification.png"
						alt="Submit" width="230px" height="65">
				</form>
			</div>
		</div>
		<div style="position: absolute; left: 20%; right: 20%;">
			<div ALIGN="center">
				<!-- 				<form action="/social/notify"> -->
				<!-- 					<input type="image" -->
				<!-- 						src="https://lh3.googleusercontent.com/-bKC68NVKF98/VSPt17n8hPI/AAAAAAAAAMA/Ouwj5HvxA3s/w600-h270-no/Notification.png" -->
				<!-- 						alt="Submit" width="230px" height="65"> -->
				<!-- 				</form> -->
			</div>
		</div>
		<div style="position: absolute; right: 0; width: 20%;">
			<div ALIGN="center">
				<TABLE BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2"
					WIDTH="225" CELLSPACING="1" CELLPADDING="3">
					<TR>
						<TH COLSPAN="3"><FONT COLOR="#65267a" SIZE="4"><B>Your
									Friend Requests</B></FONT>
					</TR>
					<%
						for (int i = 0; i < UserController.friendRequests.size(); i++) {
					%>
					<TR ALIGN="CENTER">
						<TD BGCOLOR="WHITE"><FONT COLOR="#65267a" SIZE="4"><I><B>
										<%
											String friend = UserController.friendRequests.get(i);
												out.println(friend);
										%>
								</B></I></FONT></TD>
					</TR>
					<%
						}
					%>
					<TR>
						<TH COLSPAN="3"><FONT COLOR="#65267a" SIZE="4"><B>Your
									Sent Requests</B></FONT>
					</TR>
					<%
						for (int i = 0; i < UserController.userSentRequests.size(); i++) {
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
					%>
					<TR>
						<TH COLSPAN="3"><FONT COLOR="#65267a" SIZE="4"><B>Your
									Friends</B></FONT>
					</TR>
					<%
						for (int i = 0; i < UserController.friends.size(); i++) {
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

				<form action="/social/Accept" method="POST">
					<input type="text" name="fname" align="right"
						value="Accepted Friend Request Name"
						onfocus="this.style.color='#fff'; this.value='';"
						style="font-size: 12px; height: 25px; width: 200px; font-family: 'Exo', sans-serif; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #fff; padding: 6px;">
					<br> <br> <input type="image"
						src="http://www.clker.com/cliparts/M/C/n/R/d/n/accept-button-hi.png"
						alt="Submit" width="230px" height="60"><br> <br>
				</form>
				<br>
				<form action="/social/request" method="post">
					<input type="text" name="friend" value="Friend Name"
						onfocus="this.style.color='#fff'; this.value='';"
						style="font-size: 12px; height: 25px; width: 200px; font-family: 'Exo', sans-serif; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #fff; padding: 6px;">
					<br> <br> <input type="image"
						src="https://lh3.googleusercontent.com/-GLSU52J7C3A/VP87V3XsrKI/AAAAAAAAAIE/tA0QbPmeSYg/w600-h270-no/ghhray-thin-button-blank-hi.png"
						alt="Submit" width="230px" height="65"><br> <br>
				</form>
				<form action="/social/signout">
					<input type="image"
						src="http://www.clker.com/cliparts/H/1/c/C/6/F/gray-login-thin-button-hi.png"
						alt="Submit" width="230px" height="50"><br> <br>
				</form>
			</div>
		</div>
	</div>
</body>
</html>