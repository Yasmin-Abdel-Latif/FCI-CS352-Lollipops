<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Create page</title>

</head>
<body
	background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<center>
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">Create Page<br>${it.message}</FONT></B>
		</p>
	</center>
	<br>
				<form method="post"  ALIGN="center" >
					<input type="text" name="PageName"
						value="Enter page Name" onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br> <input type="text" name="pageType"
						value="Enter page Type and Description"
						onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					<br> <br>
					<input type="text" name="pageCatg" value="Enter page category"
						onfocus="this.value='';"
						style="font-size: 16px; height: 50px; width: 500px; background: transparent; border: 1px solid #fff; border-radius: 2px; color: #ffffff; padding: 6px;">
					 <br><br><br>
					 <input type="image"
						src="https://cdn0.iconfinder.com/data/icons/huge-basic-icons/128/Create.png"
						alt="Submit" width="65px" height="60" onclick="form.action= '/social/CreatePage';">
				 <br> <br>
				</form>
		<div style="position: absolute; right: 0; width: 20%;">
			<div ALIGN="center">
				
			</div>
		</div>
</body>
</html>