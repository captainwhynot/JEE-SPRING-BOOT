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
            <h1>Customer List</h1>
            <form method="POST" action="ManageFidelityPoint">
	             <table class="table">
	                 <thead>
	                     <tr>
	                         <th>ID</th>
	                         <th>Email</th>
	                         <th>Username</th>
	                         <th>Fidelity Points</th>
	                     </tr>
	                 </thead>
	                 <tbody>
	                     <% 
	                     List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");
	                     for (Customer customer : customerList) {
	                     %>
	                         <tr>
	                             <td><%= customer.getId() %></td>
	                             <td><%= customer.getEmail() %>
	                             <input type="hidden" name="customerList" value="<%= customer.getEmail() %>"></td>
	                             <td><%= customer.getUsername() %></td>
	                             <td><input type="number" name="fidelityPointList" value="<%= customer.getFidelityPoint() %>"></td>
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