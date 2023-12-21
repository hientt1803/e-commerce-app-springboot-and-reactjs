<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible"
			  content="IE=edge" />
		<meta name="viewport"
			  content="width=device-width, initial-scale=1.0" />
		<title>Dashboard</title>
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/bootstrap.min.css"
			  type="text/css" />
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/style-admin.css" />
		<script src="https://kit.fontawesome.com/c0f581682c.js"
				crossorigin="anonymous"></script>
	</head>

	<body>
		<div class="app-container">
			<jsp:include page="sidebar.jsp" />
			<div class="app-content">
				<jsp:include page="top-content.jsp" />
				<div class="app-content-header">
					<h1 class="app-content-headerText">GIẢM GIÁ</h1>
					<button class="mode-switch d-none"
							title="Switch Theme">
						<svg class="moon"
							 fill="none"
							 stroke="currentColor"
							 stroke-linecap="round"
							 stroke-linejoin="round"
							 stroke-width="2"
							 width="24"
							 height="24"
							 viewBox="0 0 24 24">
							<defs></defs>
							<path d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z"></path>
						</svg>
					</button>
				</div>
				<div class="app-content-actions">
					<form action="/admin/coupon/search"
						  class="d-flex justify-content-center">
						<div class="input-group"
							 style="width: 270px;">
							<div class="input-group-prepend">
								<label class="input-group-text"
									   for="inputGroupSelect01">Tìm
									theo</label>
							</div>
							<select class="custom-select"
									id="inputGroupSelect01"
									onchange="handleSearchChange()"
									name="search">
								<option value="select">Chọn</option>
								<option ${
										isEd
										? 'selected'
										: ''
										}
										value="ed">Ngày hết
									hạn</option>
								<option ${
										isCd
										? 'selected'
										: ''
										}
										value="cd">Ngày tạo</option>
								<option ${
										isName
										? 'selected'
										: ''
										}
										value="name">Tên
									giảm giá</option>
							</select>
						</div>
						<input class="search-bar ml-2"
							   placeholder="Search..."
							   type="text"
							   id="searchInput"
							   style="height: 40px;"
							   name="keyword"
							   ${
							   isSuscess
							   ? 'disabled'
							   : ''
							   }>
						<button type="submit"
								class="btn btn-primary ml-2">Tìm</button>
					</form>
					<form action="/admin/coupon/filter"
						  method="post"
						  class="d-flex justify-content-center">
						<div class="input-group ml-5"
							 style="width: 220px;">
							<div class="input-group-prepend">
								<label class="input-group-text"
									   for="inputGroupSelect01">Lọc</label>
							</div>
							<select class="custom-select"
									id="inputGroupSelect01"
									name="status">
								<option value="select">Chọn</option>
								<option value="active"
										${
										isActive
										? 'selected'
										: ''
										}>Hoạt
									động</option>
								<option value="inactive"
										${
										isInactive
										? 'selected'
										: ''
										}>Không
									hoạt động</option>
							</select>
						</div>
						<button type="submit"
								class="btn btn-primary ml-2">Lọc</button>
					</form>
					<div class="app-content-actions-wrapper">
						<!-- 					<button type="button" class="btn btn-primary" data-toggle="modal" -->
						<!-- 						data-target="#addCoupon">THÊM KHUYẾN MÃI</button> -->
						<a href="/admin/coupon/add"
						   class="btn btn-secondary"
						   role="button"
						   aria-pressed="true"
						   style="height: 40px;">THÊM GIẢM GIÁ</a>
						<div class="filter-button-wrapper d-none">
							<button class="action-button filter jsFilter">
								<span>Filter</span>
								<svg xmlns="http://www.w3.org/2000/svg"
									 width="16"
									 height="16"
									 viewBox="0 0 24 24"
									 fill="none"
									 stroke="currentColor"
									 stroke-width="2"
									 stroke-linecap="round"
									 stroke-linejoin="round"
									 class="feather feather-filter">
									<polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3" />
								</svg>
							</button>
							<div class="filter-menu">
								<label>Category</label> <select>
									<option>All Categories</option>
									<option>Furniture</option>
									<option>Decoration</option>
									<option>Kitchen</option>
									<option>Bathroom</option>
								</select> <label>Status</label> <select>
									<option>All Status</option>
									<option>Active</option>
									<option>Disabled</option>
								</select>
								<div class="filter-menu-buttons">
									<button class="filter-button reset">Reset</button>
									<button class="filter-button apply">Apply</button>
								</div>
							</div>
						</div>
						<button class="action-button list active d-none"
								title="List View">
							<svg xmlns="http://www.w3.org/2000/svg"
								 width="16"
								 height="16"
								 viewBox="0 0 24 24"
								 fill="none"
								 stroke="currentColor"
								 stroke-width="2"
								 stroke-linecap="round"
								 stroke-linejoin="round"
								 class="feather feather-list">
								<line x1="8"
									  y1="6"
									  x2="21"
									  y2="6" />
								<line x1="8"
									  y1="12"
									  x2="21"
									  y2="12" />
								<line x1="8"
									  y1="18"
									  x2="21"
									  y2="18" />
								<line x1="3"
									  y1="6"
									  x2="3.01"
									  y2="6" />
								<line x1="3"
									  y1="12"
									  x2="3.01"
									  y2="12" />
								<line x1="3"
									  y1="18"
									  x2="3.01"
									  y2="18" />
							</svg>
						</button>
						<button class="action-button grid d-none"
								title="Grid View">
							<svg xmlns="http://www.w3.org/2000/svg"
								 width="16"
								 height="16"
								 viewBox="0 0 24 24"
								 fill="none"
								 stroke="currentColor"
								 stroke-width="2"
								 stroke-linecap="round"
								 stroke-linejoin="round"
								 class="feather feather-grid">
								<rect x="3"
									  y="3"
									  width="7"
									  height="7" />
								<rect x="14"
									  y="3"
									  width="7"
									  height="7" />
								<rect x="14"
									  y="14"
									  width="7"
									  height="7" />
								<rect x="3"
									  y="14"
									  width="7"
									  height="7" />
							</svg>
						</button>
					</div>
				</div>
				<div class="products-area-wrapper tableView">
					<div class="products-header">
						<div class="product-cell category">
							Tên giảm giá <a href="/admin/coupon?field=couponName&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'couponName' ? 'text-primary' : '' }">
								${field == 'couponName' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell image">
							Mã giảm giá <a href="/admin/coupon?field=couponCode&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'couponCode' ? 'text-primary' : '' }">
								${field == 'couponCode' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell status-cell">
							Phần trăm <a href="/admin/coupon?field=discountAmount&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'discountAmount' ? 'text-primary' : '' }">
								${field == 'discountAmount' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell sales">
							Ngày bắt đầu <a href="/admin/coupon?field=startDate&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'startDate' ? 'text-primary' : '' }">
								${field == 'startDate' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell sales">
							Ngày hết hạn <a href="/admin/coupon?field=expirationDate&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'expirationDate' ? 'text-primary' : '' }">
								${field == 'expirationDate' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell sales">
							Ngày tạo <a href="/admin/coupon?field=createdDate&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'createdDate' ? 'text-primary' : '' }">
								${field == 'createdDate' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell stock">
							Trạng Thái <a href="/admin/coupon?field=activated&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'activated' ? 'text-primary' : '' }">
								${field == 'activated' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell price">Thao tác</div>
					</div>
					<c:forEach var="coupon"
							   items="${ coupons.content }"
							   varStatus="loop">
						<div class="products-row ${loop.index % 2 != 0 ? 'product-row-even':''}">
							<button class="cell-more-button">
								<svg xmlns="http://www.w3.org/2000/svg"
									 width="18"
									 height="18"
									 viewBox="0 0 24 24"
									 fill="none"
									 stroke="currentColor"
									 stroke-width="2"
									 stroke-linecap="round"
									 stroke-linejoin="round"
									 class="feather feather-more-vertical">
									<circle cx="12"
											cy="12"
											r="1" />
									<circle cx="12"
											cy="5"
											r="1" />
									<circle cx="12"
											cy="19"
											r="1" />
								</svg>
							</button>
							<div class="product-cell category">
								<span class="cell-label">Category:</span>${ coupon.couponName }
							</div>
							<div class="product-cell image">
								<span>${ coupon.couponCode }</span>
							</div>
							<div class="product-cell status-cell">
								<span>${Math.round(coupon.discountAmount)}</span>
							</div>
							<div class="product-cell sales">
								<span class="cell-label">Sales:</span>
								<fmt:formatDate value="${ coupon.startDate }"
												pattern="dd/MM/yyyy" />
							</div>
							<div class="product-cell sales">
								<span class="cell-label">Sales:</span>
								<fmt:formatDate value="${coupon.expirationDate}"
												pattern="dd/MM/yyyy" />
							</div>
							<div class="product-cell sales">
								<span class="cell-label">Sales:</span>
								<fmt:formatDate value="${ coupon.createdDate }"
												pattern="dd/MM/yyyy" />
							</div>
							<div class="product-cell stock">
								<span class="cell-label">Stock:</span> ${ coupon.activated ? 'Hoạt động' : 'Không hoạt
								động' }
							</div>
							<div class="product-cell price">
								<span class="cell-label">Price:</span>
								<!-- 							<button type="button" class="btn btn-primary" data-toggle="modal" -->
								<%-- 								data-target="#updateCoupon${coupon.couponCode}">Sửa</button> --%>
								<a href="/admin/coupon/edit/${ coupon.couponCode }"
								   class="btn btn-dark"
								   role="button"
								   aria-pressed="true"
								   style="height: 40px;">Sửa</a>
							</div>
						</div>
						<!-- The Modal -->
						<div class="modal fade"
							 id="updateCoupon${coupon.couponCode}">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<!-- Modal Header -->
									<div class="modal-header">
										<h4 class="modal-title">CẬP NHẬT GIẢM GIÁ</h4>
										<button type="button"
												class="close"
												data-dismiss="modal">&times;</button>
									</div>
									<!-- Modal body -->
									<div class="modal-body">
										<form action="/admin/coupon/update"
											  method="post">
											<div class="container">
												<div class="row">
													<div class="col-lg-6">
														<div class="form-group">
															<label for="coupon_code"
																   class="form-label fw-bold">Mã
																giảm giá</label> <input type="text"
																   name="couponCode"
																   class="form-control"
																   id="coupon_code"
																   value="${ coupon.couponCode }"
																   readonly>
														</div>
													</div>
													<div class="col-lg-6">
														<div class="form-group">
															<label for="coupon_name"
																   class="form-label fw-bold">Tên
																giảm giá</label> <input type="text"
																   name="couponName"
																   class="form-control"
																   id="coupon_name"
																   value="${ coupon.couponName }">
														</div>
													</div>
													<div class="col-lg-6 mt-3">
														<div class="form-group">
															<label for="reduced_price"
																   class="form-label fw-bold">Giá
																giảm</label> <input type="text"
																   name="discountAmount"
																   class="form-control"
																   id="reduced_price"
																   value="${ coupon.discountAmount }"> <input
																   type="date"
																   hidden
																   name="createdDate"
																   class="form-control"
																   value="${ coupon.createdDate }">
														</div>
													</div>
													<div class="col-lg-6 mt-3">
														<div class="form-group">
															<label for="reduced_date"
																   class="form-label fw-bold">Ngày
																hết hạn</label> <input type="date"
																   name="expirationDate"
																   class="form-control"
																   id="reduced_date"
																   value="${ coupon.expirationDate }">
														</div>
													</div>
													<div class="col-lg-12 mt-3">
														<label>Trạng thái</label><br>
														<div class="form-check-inline">
															<input class="form-check-input"
																   type="radio"
																   name="activated"
																   id="active${ coupon.couponCode }"
																   value="true"
																   ${
																   coupon.activated
																   ? 'checked'
																   : ''
																   }>
															<label class="form-check-label"
																   for="active${ coupon.couponCode }"> Hoạt động
															</label>
														</div>
														<div class="form-check-inline">
															<input class="form-check-input"
																   type="radio"
																   name="activated"
																   id="dis${ coupon.couponCode }"
																   value="false"
																   ${
																   coupon.activated
																   ? ''
																   : 'checked'
																   }>
															<label class="form-check-label"
																   for="dis${ coupon.couponCode }"> Không hoạt động
															</label>
														</div>
													</div>

												</div>
												<button
														class="btn btn-primary mt-2 font-weight-bold float-right px-4 py-2">CẬP
													NHẬT</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<!--* paging start  -->
					<c:choose>
						<c:when test="${coupons.content.size() > 0}">
							<nav aria-label="Page navigation example"
								 class="mt-3 ml-4">
								<ul class="pagination pagination-sm justify-content-center">
									<li class="page-item ${ p  == 0 ?'d-none'  : '' } "><a class="page-link"
										   href="/admin/coupon?field=${field}&p=0&eop=${eop}&d=${d}">First</a></li>
									<c:forEach begin="0"
											   end="${coupons.totalPages  - 1 }"
											   varStatus="loop">
										<li class="page-item ${ loop.index == coupons.number ? 'active': ''} ">
											<a class="page-link"
											   href="/admin/coupon?field=${field}&p=${loop.index}&eop=${eop}&d=${d}">
												${loop.count} </a>
										</li>
									</c:forEach>
									<li class="page-item  ${ p  == coupons.totalPages - 1  ?'d-none'  : '' }"><a
										   class="page-link"
										   href="/admin/coupon?field=${field}&p=${coupons.totalPages - 1 }&eop=${eop}&d=${d}">Last</a>
									</li>
								</ul>
							</nav>
						</c:when>
					</c:choose>
					<!--* paging end -->
					<c:if test="${not empty success}">
						<div class="alert alert-success alert-dismissible fade show mt-5 text-center"
							 role="alert">
							<button type="button"
									class="close"
									data-dismiss="alert"
									aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							${success} <br> <a href="/admin/coupon"
							   class="btn btn-info btn-lg active h-100"
							   role="button"
							   aria-pressed="true">Làm mới</a>
						</div>
					</c:if>
				</div>
			</div>
		</div>

		<!-- The Modal  -->
		<div class="modal fade"
			 id="addCoupon">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">THÊM GIẢM GIÁ</h4>
						<button type="button"
								class="close"
								data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<form:form action="/admin/coupon/create"
								   modelAttribute="cp"
								   method="post">
							<div class="container">
								<div class="row">
									<div class="col-lg-6">
										<div class="form-group">
											<label for="coupon_code"
												   class="form-label fw-bold">Mã
												giảm giá</label> <input type="text"
												   name="couponCode"
												   class="form-control"
												   id="coupon_code">
											<form:errors path="couponCode"></form:errors>
										</div>
									</div>
									<div class="col-lg-6">
										<div class="form-group">
											<label for="coupon_name"
												   class="form-label fw-bold">Tên
												giảm giá</label> <input type="text"
												   name="couponName"
												   class="form-control"
												   id="coupon_name">
										</div>
									</div>
									<div class="col-lg-6 mt-3">
										<div class="form-group">
											<label for="reduced_price"
												   class="form-label fw-bold">Giá
												giảm</label> <input type="text"
												   name="discountAmount"
												   class="form-control"
												   id="reduced_price">
										</div>
									</div>
									<div class="col-lg-6 mt-3">
										<div class="form-group">
											<label for="reduced_date"
												   class="form-label fw-bold">Ngày
												hết hạn</label> <input type="date"
												   name="expirationDate"
												   class="form-control"
												   id="reduced_date">
										</div>
									</div>
									<div class="col-lg-12 mt-3">
										<label>Trạng thái</label><br>
										<div class="form-check-inline">
											<input class="form-check-input"
												   type="radio"
												   name="activated"
												   id="hoatdong"
												   value="true"
												   checked> <label class="form-check-label"
												   for="hoatdong"> Hoạt động </label>
										</div>
										<div class="form-check-inline">
											<input class="form-check-input"
												   type="radio"
												   name="activated"
												   id="khonghoatdong"
												   value="false"> <label class="form-check-label"
												   for="khonghoatdong"> Không
												hoạt động </label>
										</div>
									</div>
								</div>
								<button type="submit"
										data-dismiss="false"
										class="btn btn-primary mt-2 font-weight-bold float-right px-4 py-2"
										formaction="/admin/coupon/create">THÊM</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>

		<script>
			function handleSearchChange() {
				const searchInput = document.getElementById("searchInput");
				const searchOption = document.getElementById("inputGroupSelect01").value;
				if (searchOption === "ed") {
					searchInput.setAttribute("type", "date");
				}
				if (searchOption === "cd") {
					searchInput.setAttribute("type", "date");
				}
				if (searchOption === "name") {
					searchInput.setAttribute("type", "text");
				}
			}
		</script>

		<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.nice-select.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.nicescroll.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.magnific-popup.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.countdown.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.slicknav.js"></script>
		<script src="${pageContext.request.contextPath}/js/mixitup.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/script.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
				integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
				crossorigin="anonymous"></script>
	</body>

</html>