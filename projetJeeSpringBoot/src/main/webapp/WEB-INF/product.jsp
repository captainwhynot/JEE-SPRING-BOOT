<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <style>
        .card {
            width: 90%;
            max-width: 400px; /* Adjust this value based on your design */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #fff;
            transition: transform 0.2s ease-in-out;
        }
        .card-img-top {
            width: 100%;
            height: auto;
            object-fit: contain;
            object-position: center;
        }

        .card-body {
            padding: 1.25rem;
        }
    </style>
</head>
<%@ include file="header.jsp" %>
<body>
            <% 
            if (request.getAttribute("productId") != null) {
            	ProductService ps = (ProductService) session.getAttribute("productService");
                Product product = ps.getProduct((int) request.getAttribute("productId"));
            %>
                <div class="card centered">
                    <img src="<%= product.getImg() %>" class="card-img-top" alt="Product Image">
                    <div class="card-body">
                        <h5 class="card-title"><%= product.getName() %></h5>
                        <p class="card-text">Price: <%= product.getPrice() %></p>
                        <p class="card-text">Stock: <%= (product.getStock()==0?"Out of Stock.":product.getStock()) %></p>
                        <form method="POST" action="Market" class="card-text">
                            <input type="hidden" name="sellerId" value="<%= product.getUser().getId() %>">
                            <button type="submit" style="background: none; padding: 0; font-size: 16px;">Seller: <%= product.getUser().getUsername() %></button>
                        </form><br>
                        <div class="d-flex justify-content-center">
                            <form method="POST" action="Market">
                                <button type="submit" style="margin-right: 15px; background-color: black; color: white;">Return to Market</button>
                            </form>
                            <form method="POST" action="Product" class="mr-2">
                                <input type="hidden" name="action" value="addOrder">
                                <input type="hidden" name="productId" value="<%= product.getId() %>">
                                <button type="submit" >Add to Basket</button>
                            </form>
                        </div>
                    </div>    
                </div>
            <% } else { %>
                <script>showAlert("This product does not exist.", "error", "./Market")</script>
            <% } %>
    <%@ include file="footer.jsp"%>
</body>
<%= session.getAttribute("showAlert") %>
<% session.removeAttribute("showAlert"); %>
</html>
