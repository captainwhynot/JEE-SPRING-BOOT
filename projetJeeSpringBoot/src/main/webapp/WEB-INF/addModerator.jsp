<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp"%>
<% if (isLogged && loginUser.getTypeUser().equals("Administrator")) { %>
<body>
	<div class="content" id="min-taille">
        <div class="centered">
			<h1>User List</h1>
			<form method="POST" action="AddModerator">
				<table class="table">
					<thead>
						<tr>
							<th>ID</th>
							<th>Email</th>
							<th>Username</th>
							<th>Moderator</th>
						</tr>
					</thead>
					<tbody>
						<%
						List<User> userList = (List<User>) request.getAttribute("userList");
						for (User user : userList) {
						%>
						<tr>
							<td><%=user.getId()%></td>
							<td><%=user.getEmail()%> <input type="hidden"
								name="userList" value="<%=user.getEmail()%>"></td>
							<td><%=user.getUsername()%></td>
							<td><input type="checkbox" name="transferList"
								value="<%=user.getEmail()%>"
								<%=user.getTypeUser().equals("Moderator") ? "checked" : ""%>></td>
						</tr>
						<% } %>
					</tbody>
				</table>
				<button type="submit">Confirm</button>
			</form>
		</div>
    </div>
	<%@ include file="footer.jsp"%>
</body>
<% } else { %>
	<script type="text/javascript">showAlert();</script>
<% } %>
</html>