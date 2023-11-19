<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<body>
    <div class="d-flex justify-content-center align-items-center login-container" id="Inscr">
        <form method="post" class="login-form text-center" action="Registration">
            <div class="form-group">
                <label for="email">E-mail :</label>
                <input type="text" id="email" name="email" class="form-control" required>
            </div>
			
			<div class="form-group">
                <label for="username">Username :</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>
            
            <div class="form-group">
                <label for="password">Password :</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            
            <button type="submit" class="btn-lg btn-custom btn-block text-uppercase">Register</button>
            <p class="mt-3 font-weight-normal">Already have an account? 
            	<a href="./Login"><strong>Login</strong></a>
           	</p>
        </form>
    </div>
    <%@ include file="footer.jsp" %>
</body>

</html>