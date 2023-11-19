<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="com.mainapp.repository.*"%>
<%@ page import="com.mainapp.entity.*"%>
<%@ page import="com.mainapp.demo.*"%>
<%@ page import="com.mainapp.service.*"%>
<%@ page import="com.mainapp.controller.*"%>


<%
User loginUser = (User) session.getAttribute("user");
boolean isLogged = loginUser != null && loginUser.getId() != 0;
%>
<head>
<title>MANGASTORE</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/productDetails.css">
<link rel="stylesheet" href="css/market.css">

<link href='https://unpkg.com/boxicons@2.1.2/css/boxicons.min.css'
	rel='stylesheet'>
<link rel="stylesheet"
	href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">
<link rel="stylesheet" type="text/css"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
    integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
    integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
    integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
	function showAlert(text = "You can't access this page.", icon = "warning", redirection = "./Index") {
		Swal.fire({
		    title: text,
		    icon: icon,
		    position: 'center',
		    showConfirmButton: true,
		}).then((result) => {
			if (redirection != "") {
		    	window.location.href = redirection;
			}
		});
	}
</script>
</head>
<header>
	<!-- navbar top -->
	<div class="container">
		<div class="navbar-top">
			<div class="social-link">
				<a href="./Index"><i><img src="img/logo.png" alt=""
						width="90"></i></a>
			</div>
			<div class="logo">
				<h3>MANGASTORE</h3>
			</div>
			<div class="icons">
				<div class="input-box">
					<form method="post" action="Market"
						class="mb-3">
						<input type="text" id="search" name="search"
							placeholder="Search..."> <span class="icon"> <i
							class="uil uil-search search-icon"></i> 
							<% //if (isLogged && loginUser.getTypeUser().equals("Customer")) { %>
								<a href="./History"><i><img src="img/historique.png"
										alt="" width="24px"></i></a>
								<a id="decal2" href="./Basket"><i><img
										src="img/shopping-cart.png" alt="" width="32px"></i></a> 
							<% //} %>
						</span> <i class="uil uil-times close-icon"></i>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- navbar top -->

	<!-- main content -->

	<nav class="navbar navbar-expand-md" id="navbar-color">
		<div class="container">
			<!-- Toggler/collapsible Button -->
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#collapsibleNavbar">
				<span><i><img src="img/menu.png" alt="" width="30px"></i></span>
			</button>

			<!-- Navbar links -->
			<div class="collapse navbar-collapse" id="collapsibleNavbar">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="./Index">Home</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="./Market">Market</a>
					</li>
					<% //if (!isLogged) { %>
						<li><a class="nav-link" aria-current="page" href="./Login">Login</a></li>
					<% //} else { %>
					<% //if (loginUser.getTypeUser().equals("Administrator")) { %>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#"
							id="navbarDropdownMenuLink" role="button" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Moderator</a>
							<div class="dropdown-menu"
								aria-labelledby="navbarDropdownMenuLink">
								<a class="dropdown-item" href="./ManageModerator">Manage
									Rights</a> <a class="dropdown-item" href="./AddModerator">Add
									Moderator</a>
							</div></li>
						<li class="nav-item"><a class="nav-link"
							href="./ManageFidelityPoint">Manage Fidelity Points</a></li>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#"
							id="navbarDropdownMenuLink" role="button" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Products</a>
							<div class="dropdown-menu"
								aria-labelledby="navbarDropdownMenuLink">
								<a class="dropdown-item" href="./ManageProduct">Manage
									Product</a> <a class="dropdown-item" href="./AddProduct">Add Product</a>
							</div></li>
					<% /*} else if (loginUser.getTypeUser().equals("Moderator")) {
						ModeratorDao moderatorDao = new ModeratorDao(HibernateUtil.getSessionFactory());
						Moderator moderator = moderatorDao.getModerator(loginUser.getId());*/
					%>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#"
							id="navbarDropdownMenuLink" role="button" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Products</a>
							<div class="dropdown-menu"
								aria-labelledby="navbarDropdownMenuLink">
								<% //if (moderator.canModifyProduct() || moderator.canDeleteProduct()) { %>
								<a class="dropdown-item" href="./ManageProduct">Manage
									Product</a>
								<% //} if (moderator.canAddProduct()) { %>
									<a class="dropdown-item" href="./AddProduct">Add Product</a>
								<% //} %>
							</div>
						</li>
					<% //} %>
					<li><a class="nav-link" aria-current="page" href="./Profil">Profil</a></li>
					<li><a class="nav-link" aria-current="page" href="./Logout">Logout</a></li>
					<% //} %>
				</ul>
			</div>
		</div>
	</nav>
	<script>
        let inputBox = document.querySelector(".input-box"),
            searchIcon = document.querySelector(".icon"),
            closeIcon = document.querySelector(".close-icon");
        searchIcon.addEventListener("click", () => inputBox.classList.add("open"));
        closeIcon.addEventListener("click", () => inputBox.classList.remove("open"));
    </script>
</header>