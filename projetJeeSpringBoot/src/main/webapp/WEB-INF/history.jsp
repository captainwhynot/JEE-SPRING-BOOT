<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<% if (isLogged && loginUser.getTypeUser().equals("Customer")) { %>
<body>
    <div class="content" id="min-taille">
	    <div class="centered">
	        <h1>History</h1>
	        <table class="table">
	            <thead>
	                <tr>
	                	<th>Id</th>
	                    <th>Product</th>
	                    <th>Price</th>
	                    <th>Quantity</th>
	                    <th>Seller</th>
	                    <th>Total Price</th>
	                    <th>Purchase Date</th>
	                </tr>
	            </thead>
	            <tbody>
	                <% List<Basket> basketList = (List<Basket>) request.getAttribute("basketList");
	                    for (Basket basket : basketList) {
	                %>
                    <tr>
                    	<td><%= basket.getId() %></td>
                        <td><form method="POST" action="Product" class="mr-2">
					    	<input type="hidden" name="productId" value="<%= basket.getProduct().getId() %>">
					    	<button type="submit" style="background: none; padding: 0; font-size: 16px; color: #007bff;"><%= basket.getProduct().getName() %></button>
					  	</form></td>
                        <% 
                        double price = basket.getProduct().getPrice();
                        String priceString = String.format("%.2f", price);
                        %>
                        <td><%= priceString %></td>
                        <td><%= basket.getQuantity() %></td>
                        <td><a href="./Market"><%= basket.getProduct().getUser().getUsername() %></a>
                        <input type="hidden" name="sellerId" value="<%= basket.getProduct().getUser().getId() %>"></td>
                        <% 
                        double total = basket.getProduct().getPrice() * basket.getQuantity(); 
                        String totalString = String.format("%.2f", total);
                        %>
                        <td><%= totalString %></td>
                        <%
                        Date purchaseDate = basket.getPurchaseDate();
                        int year = purchaseDate.getYear() + 1900;
                        int month = purchaseDate.getMonth() + 1;
                        int day = purchaseDate.getDate();
                        String formattedDate = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
                        %>
                        <td><%= formattedDate %></td>
                    </tr>
                <% } %>
            	</tbody>
        	</table>
        	<input type="hidden" name="action" value="confirmOrder">
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
<% } else { %>
<script type="text/javascript">showAlert();</script>
<% } %>
</html>