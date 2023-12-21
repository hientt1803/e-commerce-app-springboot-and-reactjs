<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Dashboard</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style-admin.css" />
<script src="https://kit.fontawesome.com/c0f581682c.js"
	crossorigin="anonymous"></script>
<style type="text/css">
.msg_errols {
	color: red;
	font-style: "italic";
}
</style>
</head>

<body>
	<div class="app-container">
		<jsp:include page="sidebar.jsp" />
		<div class="app-content">
			<jsp:include page="top-content.jsp" />
			<div class="app-content-header">
				<h3>THÊM GIẢM GIÁ</h3>
				<a href="/admin/coupon" class="btn btn-secondary btn-lg active mt-2"
					role="button" aria-pressed="true">DANH SÁCH</a>
			</div>
			<div class="app-content-actions mt-5">
				<form:form action="/admin/coupon/create" modelAttribute="coupon">
					<div class="container">
						<div class="row">
							<div class="col-lg-6">
								<div class="form-group">
									<label for="coupon_code" class="form-label fw-bold">Mã
										giảm giá</label>
									<form:input path="couponCode" id="coupon_code"
										cssClass="form-control" />
									<form:errors path="couponCode" cssClass="msg_errols"></form:errors>
								</div>
							</div>
							<div class="col-lg-6">
								<div class="form-group">
									<label for="coupon_name" class="form-label fw-bold">Tên
										giảm giá</label>
									<form:input path="couponName" id="coupon_name"
										cssClass="form-control" />
									<form:errors path="couponName" cssClass="msg_errols"></form:errors>
								</div>
							</div>
							<div class="col-lg-6 mt-3">
								<div class="form-group">
									<label for="reduced_price" class="form-label fw-bold">
										Phần trăm giảm</label>
									<form:input path="discountAmount" id="reduced_price"
										cssClass="form-control" />
									<form:errors path="discountAmount" cssClass="msg_errols"></form:errors>
								</div>
							</div>
							<div class="col-lg-6 mt-3">
								<div class="form-group">
									<label for="reduced_date" class="form-label fw-bold">Ngày
										bắt đầu</label> <input type="date" name="startDate"
										class="form-control" id="reduced_date">
									<form:errors path="startDate" cssClass="msg_errols"></form:errors>
									<p class="msg_errols">${ sussces }</p>
								</div>
							</div>
							<div class="col-lg-6 mt-3">
								<div class="form-group">
									<label for="reduced_date" class="form-label fw-bold">Ngày
										hết hạn</label> <input type="date" name="expirationDate"
										class="form-control" id="reduced_date">
									<form:errors path="expirationDate" cssClass="msg_errols"></form:errors>
									<p class="msg_errols">${ sussces }</p>
								</div>
							</div>
							<div class="col-lg-6 mt-4">
								<div class="form-group">
									<label>Trạng thái</label><br>
									<div class="form-check-inline">
										<input class="form-check-input" type="radio" name="activated"
											id="hoatdong" value="true" checked> <label
											class="form-check-label" for="hoatdong"> Hoạt động </label>
									</div>
									<div class="form-check-inline">
										<input class="form-check-input" type="radio" name="activated"
											id="khonghoatdong" value="false"> <label
											class="form-check-label" for="khonghoatdong"> Không
											hoạt động </label>
									</div>
								</div>
							</div>
						</div>
						<div>
							<c:if test="${not empty success}">
								<div class="alert alert-success alert-dismissible fade show"
									${ success == null ? 'hidden' : '' } role="alert">
									<button type="button" class="close" data-dismiss="alert"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									${success}
								</div>
							</c:if>
							<button type="submit" data-dismiss="false"
								class="btn btn-primary mt-2 font-weight-bold float-right px-4 py-2">THÊM</button>
						</div>

					</div>
				</form:form>
			</div>

			<script
				src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/js/jquery.nice-select.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/js/jquery.nicescroll.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/js/jquery.magnific-popup.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/js/jquery.countdown.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/js/jquery.slicknav.js"></script>
			<script src="${pageContext.request.contextPath}/js/mixitup.min.js"></script>
			<script src="${pageContext.request.contextPath}/js/script.js"></script>
			<script
				src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
				integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
				crossorigin="anonymous"></script>
</body>
</html>