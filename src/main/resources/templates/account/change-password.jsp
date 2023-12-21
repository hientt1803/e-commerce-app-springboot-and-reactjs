<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible"
			  content="IE=edge" />
		<meta name="viewport"
			  content="width=device-width, initial-scale=1.0" />
		<title>Home</title>
		<!-- Google Font -->
		<link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
			  rel="stylesheet" />
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/bootstrap.min.css"
			  type="text/css" />
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/font-awesome.min.css"
			  type="text/css" />
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/style.css"
			  type="text/css" />

		<style>
			body {
				background: url(${pageContext.request.contextPath}/img/login/6764552_3433839.jpg) center/cover no-repeat fixed;
				width: 100wh;
				height: 100vh;
				overflow: hidden;
			}

			.glassmorphism {
				max-width: 23rem;
				background: linear-gradient(135deg, rgba(255, 255, 255, 0.1),
						rgba(255, 255, 255, 0));
				backdrop-filter: blur(5px);
				-webkit-backdrop-filter: blur(5px);
				border-radius: 5px;
				border: 1px solid rgba(255, 255, 255, 0.18);
				box-shadow: 0 8px 32px rgba(0, 0, 0, 0.4);
			}
		</style>
	</head>

	<body class="d-flex justify-content-center align-items-center">
		<div class="container glassmorphism">
			<div class="card text-center bg-transparent border-0">
				<div class="card-body">
					<h3 class="card-title my-3 font-weight-bold">Đổi mật khẩu</h3>
					<h6 class="card-text font-italic">Thay đổi mật khẩu của bạn một
						cách dễ dàng.</h6>
					<form action="/account/change-password"
						  method="POST"
						  class="mt-5">
						<c:if test="${not empty message}">
							<div class="${success?'text-success':'text-danger'} text-left"
								 role="alert">${message}</div>
						</c:if>
						<div class="form-group mt-3">
							<input type="text"
								   class="form-control"
								   name="username"
								   id="username"
								   value="${sessionScope.username}"
								   aria-describedby="helpId"
								   placeholder="Tên đăng nhập"
								   readonly="readonly" /> <small id="helpId"
								   class="form-text text-muted"></small>
						</div>
						<div class="form-group">
							<input type="password"
								   class="form-control"
								   name="password"
								   value="${sessionScope.password}"
								   id="username"
								   aria-describedby="helpId"
								   placeholder="Mật khẩu" /> <small id="helpId"
								   class="form-text text-muted"></small>
						</div>
						<div class="form-group">
							<input type="password"
								   class="form-control"
								   name="newpass"
								   id="newpass"
								   aria-describedby="helpId"
								   placeholder="Mật khẩu mới" />
							<small id="helpId"
								   class="form-text text-muted"></small>
						</div>
						<div class="form-group">
							<input type="password"
								   class="form-control"
								   name="confirm"
								   id="confirm"
								   aria-describedby="helpId"
								   placeholder="Xác nhận mật khẩu" /> <small id="helpId"
								   class="form-text text-muted"></small>
						</div>
						<p class="text-left">
							Quên mật khẩu? <a href="#"
							   class="font-weight-bold"
							   style="color: #0c2b4b">Lấy lại mật khẩu</a>
						</p>

						<button class="btn site-btn my-4 w-100 text-white font-weight-bold p-3"
								type="submit">Đổi mật khẩu</button>
					</form>

					<p class="text-left mt-3">
						<c:choose>
							<c:when test="${not empty sessionScope.stateAdmin}">
								<a href="/admin"
								   class="font-weight-bold"
								   style="color: #0c2b4b; text-decoration: underline;">Trở về</a>

							</c:when>
							<c:otherwise>
								<a href="/"
								   class="font-weight-bold"
								   style="color: #0c2b4b; text-decoration: underline;">Trở về</a>
							</c:otherwise>
						</c:choose>


					</p>
				</div>
			</div>
		</div>

		<!-- Js Plugins -->
		<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/main.js"></script>
	</body>

</html>