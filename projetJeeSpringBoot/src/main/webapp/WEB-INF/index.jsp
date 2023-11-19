<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<body>
	<div class="container-fluid">
        <div class="row">
            <!-- slider banner	 -->
            <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <form method="POST" action="Product">
                            <input type="hidden" name="productId" value="1">
                            <button type="submit" class="btn btn-3">See more</button>
                        </form>
                    </div>
                    <div class="carousel-item">
                    	<form method="POST" action="Product">
                            <input type="hidden" name="productId" value="2">
                            <button type="submit" class="btn btn-3">See more</button>
                        </form>
                    </div>
                    <div class="carousel-item">
                    	<form method="POST" action="Product">
                            <input type="hidden" name="productId" value="3">
                            <button type="submit" class="btn btn-3">See more</button>
                        </form>
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                </a>
            </div>
        </div>
        <!-- scripts -->
        <!-- bootstrap javascript cdn -->
    </div>
   <%@ include file="footer.jsp" %>
</body>
</html>
