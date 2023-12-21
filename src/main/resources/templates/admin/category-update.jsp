<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Product Manager</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style-admin.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min.css"
	type="text/css" />
<script src="https://kit.fontawesome.com/c0f581682c.js"
	crossorigin="anonymous"></script>

<!-- Bootstrap 4.4.1
		<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/script.js"></script> -->

<style>
.video-edit-preview {
	width: 100%;
	min-height: 300px;
	border: 1px dashed #000;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	color: #000;
	cursor: pointer;
	position: relative;
}

.video-edit-preview i {
	font-size: 2rem;
}

.video-edit-preview span {
	margin-top: 30px;
	font-size: 1rem;
}

.video-edit-preview img {
	position: absolute;
	width: 95%;
	height: 95%;
	top: 95%;
	left: 95%;
	transform: translate(-95%, -95%);
	background: #fff;
	object-fit: cover;
}

.error {
	width: 600px;
	font-size: 2rem;
	color: #000;
}

.toast {
	position: absolute;
	top: 0;
	right: 3.5rem;
	transform: translateX(100%);
	z-index: 100;
	top: 3.5rem;
	transition: all 0.5s;
}

.toast.show {
	transform: translateX(0);
}
</style>
</head>

<body>

	<c:if test="${not empty messageProductManager}">
		<!-- toast msg  -->
		<div class="toast show" role="alert" aria-live="assertive"
			aria-atomic="true" data-delay="3000">
			<div class="toast-header">
				<strong class="mr-auto text-center">Quản lý sản phẩm</strong>
				<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="toast-body alert-info">${messageProductManager}</div>
		</div>
		<!-- toast msg  -->

		<script>
				setTimeout(() => {
					const toast = document.querySelector('.toast');
					toast.classList.remove('show');
				}, 3000);
			</script>

		<%
		session.removeAttribute("messageProductManager");
		%>
	</c:if>

	<div class="app-container">
		<!-- Sidebar -->
		<jsp:include page="sidebar.jsp" />

		<!-- Right content -->
		<div class="app-content">
			<!-- Top content -->
			<jsp:include page="top-content.jsp">
				<jsp:param name="title" value="${title}" />
			</jsp:include>

			<h4>QUẢN LÝ SẢN PHẨM</h4>

			<!-- -------------------------------------------------------- -->
			<!-- App action -->
			<div class="app-content-actions">
				<!-- App action bar -->
				<div class="app-content-actions-wrapper">
					<div class="filter-button-wrapper">
						<a href="/admin/category-manager"
							class="btn btn-outline-dark mr-3" style="width: 200px">Danh
							sách danh mục</a>
					</div>
				</div>
			</div>

			<div class="container">
				<h5 class="font-weight-bold text-center mb-5">
					<i class="fa-regular fa-pen-to-square"></i> Cập nhật danh mục mới
				</h5>

				<form:form action="/admin/category-manager/update"
					modelAttribute="category" cssClass="row">
					<div class="col-12">
						<p class="text-danger">${message}</p>
					</div>
					<div class="col-6">
						<div class="form-group">
							<label for="id" class="font-weight-bold">Mã Danh mục</label> <input
								type="text" name="id" value="${category.id}"
								class="form-control" readonly="readonly" placeholder="" />

						</div>
					</div>
					<div class="col-6">
						<div class="form-group">
							<label for="name" class="font-weight-bold">Tên danh mục</label>
							<form:input path="name" type="text" cssClass="form-control"
								id="name" aria-describedby="nameHelp" placeholder="" />
							<form:errors path="name" cssClass="msg_errols text-danger" />
						</div>
					</div>

					<!-- Button form -->
					<div class="col-12 mt-5 d-flex justify-content-end">
						<a href="/admin/category-manager"
							class="btn btn-outline-dark font-weight-bold mr-3"
							style="width: 200px;">Trở về</a>
						<button class="btn btn-dark font-weight-bold"
							style="width: 200px;">Cập nhật danh mục</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<!-- Bootstrap 4.4.1
		<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/script.js"></script> -->


	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<!-- Js Plugins -->
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>

</html>