<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<body>
	<div class="d-flex justify-content-center align-items-center login-container">
		<form method="post" class="login-form text-center" action="Login">
			<div class="form-group">
                <label for="email">E-mail :</label>
                <input type="text" id="email" name="email" class="form-control" required>
            </div>
            
            <div class="form-group">
                <label for="password">Password :</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

			<button type="submit"
				class="btn-lg btn-custom btn-block text-uppercase">Login</button>
			<p class="mt-3 font-weight-normal">
				Don't have an account? <a href="./Registration"><strong>Sign up</strong></a>
			</p>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>