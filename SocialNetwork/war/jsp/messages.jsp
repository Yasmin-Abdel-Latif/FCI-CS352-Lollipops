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
			<B><FONT COLOR="#FFFFFF" SIZE="6">Messages<br>${it.message}</FONT></B>
		</p>
	</center>
	<br>
	<div style="width: 100%; overflow: auto;">
		<div style="position: absolute; left: 0; width: 20%;">
			<div ALIGN="center">
				
			</div>
		</div>
		<div style="position: absolute; left: 20%; right: 20%;">
			<div ALIGN="center">
				<form action="/social/SendMsg" method="post">
					<input type="text" name="conversationName"
						value="Enter Conversation Title" onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br>
					<textarea rows="20" cols="50" name="groupMsg"
						style="font-size: 18px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;"></textarea>
					<br> <br> <input type="image"
						src="https://lh3.googleusercontent.com/-Ms2_bL8jeI4/VR_z9n6vHzI/AAAAAAAAAJs/DYbGJFAb8AQ/w600-h270-no/Send.png"
						alt="Submit" width="230px" height="65"> <br> <br>
				</form>
				<form action="/social/CreateConversation" method="post">
					<input type="text" name="conversationName"
						value="Enter Conversation Title" onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br> <input type="text" name="friendsNames"
						value="Enter Friends' Names separated with ;"
						onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br> <input type="image"
						src="https://lh3.googleusercontent.com/-0Aeud_FxIkE/VSEhVHUZ2mI/AAAAAAAAAKc/xxZXZSBcOTg/w600-h270-no/CreateMsgi.png"
						alt="Submit" width="300px" height="90"> <br> <br>
				</form>
				<form action="/social/AttachToConversation" method="post">
					<input type="text" name="conversationName"
						value="Enter Conversation Title" onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br> <input type="text" name="friendsNames"
						value="Enter Friends' Names separated with ;"
						onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br> <input type="image"
						src="https://lh3.googleusercontent.com/-6l_SsCAE5k8/VSUTHA5D9AI/AAAAAAAAANY/aUc5gnEWwzk/w600-h270-no/attach.png"
						alt="Submit" width="300px" height="90"> <br> <br>
				</form>
				<form action="/social/DeattachFromConversation" method="post">
					<input type="text" name="conversationName"
						value="Enter Conversation Title" onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br> <input type="text" name="friendsNames"
						value="Enter Friends' Names separated with ;"
						onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br> <input type="image"
						src="https://lh3.googleusercontent.com/-tfH1xEDw7RY/VSUTHAlMEFI/AAAAAAAAANk/vjoGRFHusmg/w600-h270-no/deattach.png"
						alt="Submit" width="300px" height="90"> <br> <br>
				</form>
			</div>
		</div>
		<div style="position: absolute; right: 0; width: 20%;">
			<div ALIGN="center">
				
			</div>
		</div>
	</div>
</body>
</html>