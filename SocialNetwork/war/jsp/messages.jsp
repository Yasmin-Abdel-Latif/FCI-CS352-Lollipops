<!DOCTYPE>
<html>
<head>

</head>
<body background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<div align="CENTER">
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">Messages</FONT></B><br><br>
			<B><FONT COLOR="#FFFFFF" SIZE="6">${it.message}</FONT></B>
		</p>
		<form action="/social/SendMsg" method="post">
			<input type="text" name="conversationName" value="Enter Conversation Title ;" onfocus="this.value='';"
				style="font-size: 16px; 
				height: 50px; 
				width: 500px; 
				background: transparent; 
				border: 1px solid #fff; 
				border-radius: 2px; 
				color: #ffffff; 
				padding: 6px;">
			<br> <br>
			<textarea rows="20" cols="50" name="groupMsg"
				style="font-size: 18px;
				background: transparent; 
				border: 1px solid #fff; 
				border-radius: 2px; 
				color: #ffffff; 
				padding: 6px;"></textarea>
			<br> <br> 
			<input type="image" src="https://lh3.googleusercontent.com/-Ms2_bL8jeI4/VR_z9n6vHzI/AAAAAAAAAJs/DYbGJFAb8AQ/w600-h270-no/Send.png" alt="Submit" width="230px" height="65">
			<br> <br>
		</form>
		<form action= "/social/CreateConversation" method="post">
			<input type="text" name="conversationName" value="Enter Conversation Title ;" onfocus="this.value='';"
				style="font-size: 16px; 
				height: 50px; 
				width: 500px; 
				background: transparent; 
				border: 1px solid #fff; 
				border-radius: 2px; 
				color: #ffffff; 
				padding: 6px;">
			<br> <br>
			<input type="text" name="friendsNames" value="Enter Friends' Names separated with ;" onfocus="this.value='';"
				style="font-size: 16px; 
				height: 50px; 
				width: 500px; 
				background: transparent; 
				border: 1px solid #fff; 
				border-radius: 2px; 
				color: #ffffff; 
				padding: 6px;">
			<br> <br>
			<input type="image" src="https://lh3.googleusercontent.com/-0Aeud_FxIkE/VSEhVHUZ2mI/AAAAAAAAAKc/xxZXZSBcOTg/w600-h270-no/CreateMsgi.png" alt="Submit" width="460px" height="130">
			<br> <br>
		</form>
	</div>




</body>
</html>