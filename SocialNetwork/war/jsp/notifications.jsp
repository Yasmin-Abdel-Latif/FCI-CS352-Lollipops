<%@ page import="java.util.*"%>
<%@ page import="com.FCI.SWE.Controller.UserController"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Notifications</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
<style>
    table.clickable tr:hover { background-color: White; }
    table.clickable tr > td { cursor: pointer; }
    table.clickable tr > td > a { display: block; text-decoration: none; }
</style>
<script>

jQuery(function () {
	$('#tableId').click(function (e) {
		var info = $(e.target).text();
		var Id = $(e.target).text().substring(0, $(e.target).text().indexOf("-"));
		var TYpe = $(e.target).text().substring($(e.target).text().indexOf("-")+1, $(e.target).text().indexOf(" "));
		var PRint = $(e.target).text().substring($(e.target).text().indexOf(" ") + 1) + "  ";
		var NAme = PRint.substring(0, PRint.indexOf(":"));
		$('input[name="ID"]').val(Id);
		$('input[name="type"]').val(TYpe);
		$( "#target" ).submit();
	});
	
	$('#tableId').on('click', '.btn', function()
	{
		var PRint = $(this).closest('tr').find('td:first').text().substring($(this).closest('tr').find('td:first').text().indexOf(" ") + 1) + "  ";
		var NAme = PRint.substring(0, PRint.indexOf(":"));
		$('input[id="fname"]').val(NAme);
		$( "#target" ).attr('action', '/social/Accept');
	});
});

</script>
</head>
<body background="http://wallpapercolor.net/wallpapers/syslinux-background-wallpaper-13542.jpg">
	<center>
		<p>
			<B><FONT COLOR="#FFFFFF" SIZE="6">Notifications</FONT></B>
		</p>
	</center>
	<br>
	<div align="center">
	<form id="target" action="/social/chosenNotification" method="POST">
		<table id="tableId" class="clickable" BORDER="3" BORDERCOLOR="#65267a" BGCOLOR="#e5bdf2" WIDTH="750" CELLSPACING="1" CELLPADDING="3">
			<%
				for (int i = 0; i < UserController.userUnSeenNotifications.size(); i++) {
			%>
			<TR ALIGN="CENTER">
			
				<TD>
				<a href="#" style="text-decoration:none">
				<FONT COLOR="#65267a" SIZE="4"><I><B>
					<%
						String notification = UserController.userUnSeenNotifications.get(i);
						String ID = notification.substring(0, notification.indexOf("-"));
						String type = notification.substring(notification.indexOf("-")+1, notification.indexOf(" "));
						String print = notification.substring(notification.indexOf(" ") + 1) + "  ";
						String name = print.substring(0, print.indexOf(":"));
						%>
						<FONT COLOR="#e5bdf2" SIZE="4">
						<%
						out.println(ID + "-" + type + " ");
						%>
						</FONT>
						<%
						out.println(print);
						if(type.equals("2") && !UserController.friends.contains(name))
						{ 
						%>
							<input class="btn" type="Submit" id="acceptId" value="Accept" 
							style="background: #fff;
							border: 1px solid #fff;
							cursor: pointer;
							border-radius: 2px;
							color: #65267a;
							font-size: 16px;
							font-weight: 400;
							padding: 6px;"/>
						<% 
						}
						%>
					<input type="hidden" id="fname" name="fname" value="<%=name%>">
					<input type="hidden" id="ID" name="ID" value="<%=ID%>">
					<input type="hidden" id="type" name="type" value="<%=type%>">
				</B></I></FONT>
				</a>
				
				</TD>
			</TR>
			<%
				}
				for (int i = 0; i < UserController.userSeenNotifications.size(); i++) {
			%>
			<TR ALIGN="CENTER">
				<TD BGCOLOR="WHITE">
				<a href="#" style="text-decoration:none">
				<FONT COLOR="#65267a" SIZE="4"><I><B>
					<%
						String notification = UserController.userSeenNotifications.get(i);
						String ID = notification.substring(0, notification.indexOf("-"));
						String type = notification.substring(notification.indexOf("-")+1, notification.indexOf(" "));
						String print = notification.substring(notification.indexOf(" ") + 1) + "  ";
						String name = print.substring(0, print.indexOf(":"));
					%>
					<FONT COLOR="WHITE" SIZE="4">
					<%
						out.println(ID + "-" + type + " ");
					%>
					</FONT>
					<%
					out.println(print);
					if(type.equals("2") && !UserController.friends.contains(name))
					{ 
					%>
						<input class="btn" type="Submit" id="acceptId" value="Accept" 
						style="background: #e5bdf2;
						border: 1px solid #e5bdf2;
						cursor: pointer;
						border-radius: 2px;
						color: #ffffff;
						font-size: 16px;
						font-weight: 400;
						padding: 6px;"/>
					<% 
					}

					%>
					
					<input type="hidden" id="fname" name="fname" value="<%=name%>">
					<input type="hidden" id="ID" name="ID" value="<%=ID%>">
					<input type="hidden" id="type" name="type" value="<%=type%>">
				</B></I></FONT>
				</a>
				
				</TD>
			</TR>
			<%
				}
			%>
			
		</table>
		</form>
		
		<br>
		<form action="/social/seenNotifications" method= "POST">
			<input type="image"
				src="https://lh3.googleusercontent.com/-wAhXPNfCuGA/VSPt6fnj5QI/AAAAAAAAAMk/CXYn6j_qpXU/w600-h270-no/Seen.png"
				alt="Submit" width="230px" height="65">
		</form>
	</div>
</body>
</html>