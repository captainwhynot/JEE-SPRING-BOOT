<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<% if (isLogged && loginUser.getTypeUser().equals("Administrator")) { %>
<body>
	<div class="content" id="min-taille">
        <div class="centered">
	        <h1>Moderator List</h1>
	        <form method="POST" action="ManageModerator">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Username</th>
                            <th>AddProduct</th>
                            <th>ModifyProduct</th>
                            <th>DeleteProduct</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        List<Moderator> moderatorList = (List<Moderator>) request.getAttribute("moderatorList");
                        for (Moderator moderator : moderatorList) {
                        %>
                            <tr>
                                <td><%= moderator.getId() %></td>
                                <td><%= moderator.getEmail() %>
                                <input type="hidden" name="moderatorList" value="<%= moderator.getEmail() %>"></td>
                                <td><%= moderator.getUsername() %></td>
                                <td><input type="checkbox" name="addProductList" value="<%= moderator.getEmail() %>"
                                <%= moderator.canAddProduct() ? "checked" : "" %>></td>
                                <td><input type="checkbox" name="modifyProductList" value="<%= moderator.getEmail() %>"
                                <%= moderator.canModifyProduct() ? "checked" : "" %>></td>
                                <td><input type="checkbox" name="deleteProductList" value="<%= moderator.getEmail() %>"
                                <%= moderator.canDeleteProduct() ? "checked" : "" %>></td>
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