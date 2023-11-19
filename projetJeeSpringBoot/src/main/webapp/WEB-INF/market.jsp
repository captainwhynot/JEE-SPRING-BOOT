<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Market</title>
    <link rel="stylesheet" href="path/to/your/css/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
    <style>
    .product-grid {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }
    .product-item {
        flex-basis: 16%;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
        margin: 10px;
        padding: 15px;
        text-align: center;
    }
    .product-item img {
        max-width: 100%;
        height: auto;
    }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="content" id="min-taille">
        <div class="centered">
            <form method="post" action="Market">
                <div class="mb-3 centered2">
                    <input type="text" class="form-control" id="search" name="search" placeholder="Search..." required>
                    <button type="submit" id="SR">Search</button>
                </div>
            </form>        

            <% 
            List<Product> productList = (List<Product>) request.getAttribute("productList");
            int size = productList.size();
            if (request.getAttribute("search") != null) {
            %>
                <div> <%= size %> result<%= (size > 1 ? "s" : "") %> for "<%= request.getAttribute("search") %>".
                </div>
            <% } else if (request.getAttribute("sellerId") != null) {
                int sellerId = (int) request.getAttribute("sellerId");
                UserDao userDao = new UserDao(HibernateUtil.getSessionFactory());
                User user = userDao.getUser(sellerId);%>
                <div> <%= size %> result<%= (size > 1 ? "s" : "") %> for <%= user.getUsername() %>'s products.
                </div>
            <% } else { %>
                <div> All products
                </div>
            <% } %>

            <div class="product-grid">
                <% 
                for (Product product : productList) { 
                %>
                    <div class="product-item">
                        <img src="<%= product.getImg() %>" alt="<%= product.getName() %>" class="product-image">
                        <h3><%= product.getName() %></h3>
                        <p>Price: <%= product.getPrice() %></p>
                        <p>Stock: <%= product.getStock() == 0 ? "Out of Stock" : product.getStock() %></p>
                        <form method="POST" action="Market">
                            <input type="hidden" name="sellerId" value="<%= product.getUser().getId() %>">
                            Seller: 
                        	<button type="submit" style="background: none; padding: 0; font-size: 16px;">
                        		<span style="color: #007bff;"><%= product.getUser().getUsername() %></span>
                        	</button>
                       	</form>
                       	<br>
                        <form method="POST" action="Product">
                            <input type="hidden" name="productId" value="<%= product.getId() %>">
                            <button type="submit" style="padding: 0; background: none;" >
                                <i class="fas fa-eye" style="color: #007bff;"></i> See details
                            </button>
                        </form>
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <%@ include file="footer.jsp"%>
</body>
</html>
