<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <style>
        .card-img-top {
            max-height: 600px; 
            width: auto;
            height: auto;
            object-fit: contain;
            object-position: center;
        }
    </style>
</head>
<%@ include file="header.jsp" %>
<body>
    <div class="d-flex justify-content-center align-items-center page-container">
        <div class="centered">
            <% 
            if (request.getAttribute("productId") != null) {
                ProductDao productDao = new ProductDao(HibernateUtil.getSessionFactory());
                Product product = productDao.getProduct((int) request.getAttribute("productId"));
            %>
                <div class="card centered" style="width: 40rem;">
                    <img src="<%= product.getImg() %>" class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title"><%= product.getName() %></h5>
                        <p class="card-text">Price: <%= product.getPrice() %></p>
                        <p class="card-text">Stock: <%= (product.getStock()==0?"Out of Stock.":product.getStock()) %></p>
                        <form method="POST" action="Market" class="card-text">
                            <input type="hidden" name="sellerId" value="<%= product.getUser().getId() %>">
                            <button type="submit" style="background: none; padding: 0; font-size: 16px;">Seller: <%= product.getUser().getUsername() %></button>
                        </form>
                        <br>
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
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
