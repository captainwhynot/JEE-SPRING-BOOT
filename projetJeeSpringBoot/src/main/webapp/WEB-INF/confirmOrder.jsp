<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<% if (isLogged && loginUser.getTypeUser().equals("Customer")) { %>
<body>
    <div class="content d-flex align-items-center " id="min-taille">
	    <div class="centered">
	        <h1>Basket</h1>
	        <form method="POST" action="Basket">
	        	<% double totalOrderPrice = 0; %>
                <table class="table">
                    <thead>
                        <tr>
                        	<th>Id</th>
                        	<th>Image</th>
                            <th>Product</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Seller</th>
                            <th>Total Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        List<Basket> basketList = (List<Basket>) request.getAttribute("basketList");
                        for (Basket basket : basketList) {
                        %>
	                        <tr>
	                        	<td><%= basket.getId() %></td>
	                        	<td><img src="<%= basket.getProduct().getImg()%>" style="height: 28px;"></td>
	                            <td><button type="button" style="background: none; padding: 0; font-size: 16px; color: #007bff;" 
	                            	onclick="seeProduct('<%= basket.getProduct().getId() %>')">
	                            	<%= basket.getProduct().getName() %></button></td>
	                            <td><%= basket.getProduct().getPrice() %></td>
	                            <td><%= basket.getQuantity() %></td>
	                            <td><button type="button" style="background: none; padding: 0; font-size: 16px; color: #007bff;" 
	                             onclick="seeMarket('<%= basket.getProduct().getUser().getId() %>')">
	                             <%= basket.getProduct().getUser().getUsername() %></button></td>
	                            <% double totalPrice = basket.getProduct().getPrice() * basket.getQuantity(); 
	                            String totalPriceString = String.format("%.2f", totalPrice);
	                            totalOrderPrice += totalPrice;
	                            %>
	                            <td><span class="totalPrice"><%= totalPriceString %></span></td>
	                        </tr>
                    	<% } %>
                        <tr>
	                        <td colspan=6>Total Order before discount</td>
                                <% String totalOrderPriceString = String.format("%.2f", totalOrderPrice); %>
	                        <td><%= totalOrderPriceString %></td>
                        </tr>
                        <tr>
	                        <td colspan=6>Discount</td>
		                        <% 
		                        CustomerDao customerDao = new CustomerDao(HibernateUtil.getSessionFactory());
	                        	Customer customer = customerDao.getCustomer(loginUser.getId());
	                        	double fidelityPoint = customer.getFidelityPoint();
	                        	double discount = (fidelityPoint > totalOrderPrice) ? totalOrderPrice : fidelityPoint;
                                String fidelityPointString = String.format("%.2f", fidelityPoint);
                                String discountString = String.format("%.2f", discount);
                                %>
	                        <td><%= discountString %></td>
                        </tr>
                        <tr>
	                        <td colspan=6>Total Order Price :</td>
		                        <% 
		                        totalOrderPrice = totalOrderPrice - discount;
                                totalOrderPriceString = String.format("%.2f", totalOrderPrice); 
                                %>
	                        <td><%= totalOrderPriceString %></td>
                        </tr>
                    </tbody>
                </table>
                <input type="hidden" id="action" name="action" value="confirmCreditCard">
				<button type="button" onclick="updateAction('');">Cancel</button>
	            <button type="button" onclick="updateAction('confirmCreditCard');">Confirm Order</button>
              	<button type="submit" id="submitButton" class="d-none">Submit</button>
			</form>
		</div>
    </div>
    <%@ include file="footer.jsp"%>
    <script src="./js/basket.js"></script>
    <script>
	    function updateAction(action) {
	    	//Update the action and submit the form
	        document.getElementById('action').value = action;
	        document.getElementById('submitButton').click();
	    }
	</script>
</body>
<% } else { %>
<script type="text/javascript">showAlert();</script>
<% } %>
</html>