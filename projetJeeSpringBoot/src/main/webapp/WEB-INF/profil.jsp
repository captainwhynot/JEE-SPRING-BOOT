<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
<style>
    .card-img-top {
        max-width: 360px;
        max-height: 360px;
        width: auto; 
        height: auto; 
    }
</style>
</head> 
<%@ include file="header.jsp" %>
<%
if (isLogged) {
%>
<body>
	<div class="content" id="min-taille">
	<br>
		<div class="card centered" style="width: 20rem; position: relative;">
			<form method="post" action="Profil"  enctype="multipart/form-data">
				<input type="hidden" id="action" name="action" value="updateProfil">
				<input type="hidden" id="profilInfo" name="profilInfo" value="">
				<input type="hidden" id="newValueInput" name="newValueInput" value="">
				<input type="hidden" id="passwordInput" name="passwordInput" value="">
				<img src="<%= loginUser.getProfilePicture() %>" class="card-img-top">
				<input type="file" class="d-none" id="imgFile" name="imgFile" accept="image/png, image/jpeg" onchange="fileSelected(this)">
				<div class="card-body">
					<div>
					<p>Avatar : <i class="fas fa-edit" style="background-color: white;" onclick="addFile();"></i>
					<i class="fas fa-trash" style="color: red; background-color: white;" onclick="deleteProfilePicture();"></i></p>
					<br>
					</div>
					<p class="card-text">E-mail : <%= loginUser.getEmail() %> <i class="fas fa-edit" onclick="newInfo('email');"></i></p>
					<p class="card-text">Username : <%= loginUser.getUsername() %> <i class="fas fa-edit" onclick="newInfo('username');"></i></p>
					<p class="card-text">Password : ********* <i class="fas fa-edit" onclick="newInfo('password');"></i></p>
					<% if (loginUser.getTypeUser().equals("Customer")) {
						CustomerDao customerDao = new CustomerDao(HibernateUtil.getSessionFactory());
						Customer customer = customerDao.getCustomer(loginUser.getId());%>
						<p class="card-text">Fidelity Points : <%= customer.getFidelityPoint() %></p>
					<% } else if (loginUser.getTypeUser().equals("Moderator")) {
						ModeratorDao moderatorDao = new ModeratorDao(HibernateUtil.getSessionFactory());
						Moderator moderator = moderatorDao.getModerator(loginUser.getId());%>
						<p class="card-text">Can add product : <%= moderator.canAddProduct() %></p>
						<p class="card-text">Can modify product : <%= moderator.canModifyProduct() %></p>
						<p class="card-text">Can delete product : <%= moderator.canDeleteProduct() %></p>
					<% } %>
					<br>
	              	<button type="submit" id="submitButton" class="d-none">Submit</button>
				</div>
			</form>
		</div>
	</div>
    <%@ include file="footer.jsp"%>
    <script>
		function addFile() {
			document.getElementById("imgFile").click();
		}
		
	    function fileSelected(input) {
	        if (input.files.length > 0) {
		        document.getElementById('profilInfo').value = "profilePicture";
	            document.getElementById("submitButton").click();
	        }
	    }
	    
	    function deleteProfilePicture() {
	    	document.getElementById('profilInfo').value = "deleteProfilePicture";
            document.getElementById("submitButton").click();
	    }
		
    	function newInfo(profilInfo) {
	        document.getElementById('profilInfo').value = profilInfo;
    		Swal.fire({
    			  title: "Enter your new " + profilInfo,
    			  html:'<div class="labName">' +
    		        '<label for="' + profilInfo + '" class="form-label">New ' + profilInfo + '</label>' +
    		        '<input type="text" id="new_' + profilInfo + '" class="swal2-input" required>' +
    		        '</div>' +
    		        '<div class="labName">' +
    		        '<label for="confirmPassword" class="form-label">Confirm your password </label>' +
    		        '<input type="password" id="confirmPassword" class="swal2-input" required>' +
    		        '</div>',
    			  showCancelButton: true,
    			  showLoaderOnConfirm: true,
    			}).then((result) => {
    			  if (result.isConfirmed) {
					  const newValue = document.getElementById('new_' + profilInfo).value;
			          const password = document.getElementById('confirmPassword').value;
		          	  document.getElementById('newValueInput').value = newValue;
		          	  document.getElementById('passwordInput').value = password;
    				  document.getElementById("submitButton").click();
    			  }
   			});
    	}
    </script>
</body>
<% } else { %>
<script type="text/javascript">showAlert();</script>
<% } %>
</html>
