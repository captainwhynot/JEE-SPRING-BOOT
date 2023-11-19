<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
<%@ include file="header.jsp" %>
<% 
boolean canModifyProduct = false;
boolean canDeleteProduct = false;
if (isLogged) {
	if (loginUser.getTypeUser().equals("Moderator")) {
		ModeratorDao moderatorDao = new ModeratorDao(HibernateUtil.getSessionFactory());
		Moderator moderator = moderatorDao.getModerator(loginUser.getId());
		canModifyProduct = moderator.canModifyProduct();
		canDeleteProduct = moderator.canDeleteProduct();
	}
	if (loginUser.getTypeUser().equals("Administrator")) {
		canModifyProduct = true;
		canDeleteProduct = true;
	}
}
if (canModifyProduct || canDeleteProduct) {
%>
<body>
    <div class="content" id="min-taille">
        <div class="centered">
            <h1>Product List</h1>
            <form method="post" action="ManageProduct" enctype="multipart/form-data">
            	<table class="table">
                	<thead>
                    	<tr>
                        	<th>Image</th>
	                        <th>Name</th>
	                        <th>Price</th>
	                        <th>Stock</th>
	                        <th>Seller</th>
	                        <th>See details</th>
	                        <% if (canDeleteProduct) { %>
	                    		<th>Delete Product</th>
	                    	<% } %>
	                	</tr>
	                </thead>
	                <tbody>
	                    <% 
	                    List<Product> productList = (List<Product>) request.getAttribute("productList");
	                    for (Product product : productList) {
	                    %>
	                        <tr>
	                            <td><img onclick="addFile('img_<%= product.getId() %>')" src="<%= product.getImg() %>" style="height: 28px;"></img>
	                            <input type="file" class="d-none" id="img_<%= product.getId() %>" name="imgFile" accept="image/png, image/jpeg">
	                            <input type="hidden" name="img" value="<%= product.getImg() %>"></td>
	                            <td><input type="text" name="name" value="<%= product.getName() %>"></td>
	                            <td><input type="number" name="price" value="<%= product.getPrice() %>" step="0.01"></td>
	                            <td><input type="number" name="stock" value="<%= product.getStock() %>"></td>
	                            <td><%= product.getUser().getUsername() %></td>
	                            <td><button type="button" style="padding : 0; background : none;" onclick="seeDetails(<%= product.getId() %>)">
	                            <i class="fas fa-eye" style="color: #007bff;"></i></button></td>
	                            <% if (canDeleteProduct) { %>
		                            <td><button type="button" style="padding : 0; background : none;" onclick="deleteProduct(<%= product.getId() %>)">
		                            <i class="fas fa-trash" style="color: red;"></i></button></td>
	                            <% } %>
	                        </tr>
	                    <% } %>
	                </tbody>
	            </table>
	            <% if (canModifyProduct) { %>
	            	<input type="hidden" name="action" value="updateProduct">
	            	<button type="submit">Confirm</button>
	            <% } %>
	    	</form>
	    </div>
    </div>
    <%@ include file="footer.jsp"%>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script>
    	<% if (!canModifyProduct) { %>
	    	var inputs = document.querySelectorAll('input');
	
	    	//Disable all input
	        inputs.forEach(function(input) {
	            input.disabled = true;
	        });
    	<% } %>
    	
    	function addFile(productId) {
    		document.getElementById(productId).click();
    	}
    	
	    function seeDetails(productId) {
	    	//Create a form to go to product's detail
	        var form = document.createElement('form');
	        form.method = 'post';
	        form.action = './Product';
	
	        var productIdInput = document.createElement('input');
	        productIdInput.type = 'hidden';
	        productIdInput.name = 'productId';
	        productIdInput.value = productId;
	
	        form.appendChild(productIdInput);
	
	        document.body.appendChild(form);
	        form.submit();
	    }
	    
	    function deleteProduct(productId) {
	    	//Delete the product
	    	$.ajax({
	            type: "POST",
	            url: "ManageProduct",
	            data: { productId: productId, action: "deleteProduct" },
	            dataType: "json",
	            success: function (response) {
	            	showAlert("Success : " + response.status, "success", "./ManageProduct");
	            },
	            error: function (jqXHR, textStatus, errorThrown) {
	            	showAlert("Error " + jqXHR.status + ": " + jqXHR.responseJSON.status, "error", "./ManageProduct");
	            }
	        });
	    }
    </script>
</body>
<% } else { %>
<script type="text/javascript">showAlert();</script>
<% } %>
</html>