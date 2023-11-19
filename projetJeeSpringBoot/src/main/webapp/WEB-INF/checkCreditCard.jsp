<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<% if (isLogged && loginUser.getTypeUser().equals("Customer")) { %>
<body>
	<div class="d-flex align-items-center page-container" id="addPD">
        <div class="interieurAddProduct centered">
            <h1>Credit Card</h1>
                <form method="POST" action="Basket">
                	<table class="table">
                        <tr>
	               			<td>Card Number : </td>
	               			<td><input type="text" style="text-align: center;" pattern="\d*" name="cardNumber" required></td>
                        </tr>
                        <tr>
	               			<td>CVV : </td>
	               			<td><input type="text" style="text-align: center;" pattern="\d*" name="cvv" maxlength="3" required></td>
                        </tr>
                        <tr>
	               			<td>Expiration Date : </td>
	               			<td><input style="text-align: center;" type="date" name="expirationDate" required></td>
                        </tr>
	                </table>
	                <input type="hidden" id="action" name="action" value="finalizePaiement">
				    <button type="button" onclick="updateAction('confirmOrder');">Cancel</button>
	              	<button type="button" onclick="updateAction('finalizePaiement');">Confirm</button>
	              	<button type="submit" id="submitButton" class="d-none">Submit</button>
	          </form>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
    <script>
	    function updateAction(action) {
	    	//Update the action and submit the form
	        document.getElementById('action').value = action;
	        if (action == "confirmOrder") {
	        	var inputs = document.querySelectorAll('input');
		            inputs.forEach(function (input) {
		                input.required = false;
	            });
	        }
	        document.getElementById('submitButton').click();
	    }
	</script>
</body>
<% } else { %>
<script type="text/javascript">showAlert();</script>
<% } %>
</html>