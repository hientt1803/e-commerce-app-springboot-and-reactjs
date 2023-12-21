<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible"
			  content="IE=edge">
		<meta name="viewport"
			  content="width=device-width, initial-scale=1.0">
		<title>Dashboard</title>
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/bootstrap.min.css"
			  type="text/css">
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/style-admin.css">
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath }/css/font-awesome.min.css"
			  type="text/css">
		<script src="https://kit.fontawesome.com/c0f581682c.js"
				crossorigin="anonymous"></script>
	</head>

	<body>
		<!-- 	demo -->
		<div class="app-container">
			<jsp:include page="sidebar.jsp" />
			<div class="app-content">
				<jsp:include page="top-content.jsp" />
				<div class="app-content-header">
					<h1 class="app-content-headerText">ĐÁNH GIÁ</h1>
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
				<form action="/admin/review/search">
					<div class="app-content-actions">
						<div class="app-content-actions-wrapper mb-3">
							<div class="input-group"
								 style="width: 300px;">
								<div class="input-group-prepend">
									<label class="input-group-text"
										   for="inputGroupSelect01">Tìm
										theo</label>
								</div>
								<select class="custom-select"
										id="inputGroupSelect01"
										name="search">
									<option value="select">Chọn</option>
									<option value="nameSP"
											${
											isNameSP
											? 'selected'
											: ''
											}>Tên
										sản phẩm</option>
									<option value="countS"
											${
											iscountS
											? 'selected'
											: ''
											}>Số
										sao</option>
									<option value="nameKH"
											${
											isnameKH
											? 'selected'
											: ''
											}>Tên
										khách hàng</option>
								</select>
							</div>
							<input class="search-bar ml-2"
								   placeholder="Tìm kiếm..."
								   type="text"
								   name="keyword"
								   style="height: 40px;">
							<button type="submit"
									class="btn btn-primary ml-2">Tìm</button>
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
				</form>
				<div class="products-area-wrapper tableView">
					<div class="products-header">
						<div class="product-cell stt">
							Mã đánh giá <a href="/admin/review?field=id&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'id' ? 'text-primary' : '' }">
								${field == 'id' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell customer-name">
							Tên khách hàng <a href="/admin/review?field=account.fullname&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'account.fullname' ? 'text-primary' : '' }">
								${field == 'account.fullname' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell product-name">
							Tên sản phẩm <a href="/admin/review?field=product.name&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'product.name' ? 'text-primary' : '' }">
								${field == 'product.name' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell rating">
							Đánh giá <a href="/admin/review?field=rating&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'rating' ? 'text-primary' : '' }">
								${field == 'rating' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell review">
							Bình luận <a href="/admin/review?field=textReview&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'textReview' ? 'text-primary' : '' }">
								${field == 'textReview' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell time">
							Thời gian <a href="/admin/review?field=dateReview&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button ${field == 'dateReview' ? 'text-primary' : '' }">
								${field == 'dateReview' &&
								d == true ? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell delete">Xóa</div>
					</div>
					<c:forEach var="review"
							   items="${ reviews.content }"
							   varStatus="status">
						<div class="products-row ${status.index % 2 != 0 ? 'product-row-even':''}">
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
							<div class="product-cell image">
								<span>${ review.id }</span>
							</div>
							<div class="product-cell customer-name">
								<span>${ review.account.fullname }</span>
							</div>
							<div class="product-cell product-name">
								<span>${ review.product.name }</span>
							</div>
							<div class="product-cell rating">
								<c:forEach begin="1"
										   end="${review.rating}">
									<i class="fa fa-star"
									   style="color: #e1c32d;"></i>
								</c:forEach>
							</div>
							<div class="product-cell review">
								<span>${ review.textReview }</span>
							</div>
							<div class="product-cell time">
								<span>
									<fmt:formatDate type="both"
													dateStyle="short"
													timeStyle="short"
													value="${ review.dateReview}"
													pattern="dd-MM-yyyy hh:mm" />
								</span>
							</div>
							<div class="product-cell view">
								<span class="cell-label">Xóa:</span>
								<button class="btn btn-danger font-weight-bold"
										data-toggle="modal"
										data-target="#deleteCategoryModal${ review.id }">
									<i class="fa fa-trash-o"
									   aria-hidden="true"></i>
								</button>
								<div class="modal fade"
									 id="deleteCategoryModal${ review.id }"
									 tabindex="-1"
									 role="dialog"
									 aria-labelledby="deleteCategoryModalLabel"
									 aria-hidden="true">
									<div class="modal-dialog"
										 role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h6>
													Bạn có chắc muốn xóa đánh giá của <strong>${ review.account.fullname
														}</strong>
													?
												</h6>
												<button type="button"
														class="close"
														data-dismiss="modal"
														aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<div class="modal-body">
												<div class="container d-flex justify-content-center">
													<img src="${pageContext.request.contextPath}/img/user-management/confirm-delete.svg"
														 class="img-fluid m-auto"
														 style="height: 400px; width: 100%" />
												</div>
												<div class="modal-footer d-flex justify-content-end">
													<a class="btn btn-danger ml-3"
													   href="/admin/review/delete/${ review.id }">Xóa</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<!--* paging start  -->
					<c:choose>
						<c:when test="${reviews.content.size() > 0}">
							<nav aria-label="Page navigation example"
								 class="mt-3 ml-4">
								<ul class="pagination pagination-sm justify-content-center">
									<li class="page-item ${ p  == 0 ?'d-none'  : '' } "><a class="page-link"
										   href="/admin/review?field=${field}&p=0&eop=${eop}&d=${d}">First</a></li>
									<c:forEach begin="0"
											   end="${reviews.totalPages  - 1 }"
											   varStatus="loop">
										<li class="page-item ${ loop.index == reviews.number ? 'active': ''} ">
											<a class="page-link"
											   href="/admin/review?field=${field}&p=${loop.index}&eop=${eop}&d=${d}">
												${loop.count} </a>
										</li>
									</c:forEach>
									<li class="page-item  ${ p  == reviews.totalPages - 1  ?'d-none'  : '' }"><a
										   class="page-link"
										   href="/admin/review?field=${field}&p=${reviews.totalPages - 1 }&eop=${eop}&d=${d}">Last</a>
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
							${success} <br> <a href="/admin/review"
							   class="btn btn-info btn-lg active h-100"
							   role="button"
							   aria-pressed="true">Làm mới</a>
						</div>
					</c:if>
				</div>
			</div>

			<script src="${pageContext.request.contextPath}/js/script.js"></script>
			<script src="${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
			<script src="${pageContext.request.contextPath }/js/bootstrap.min.js"></script>
			<script src="${pageContext.request.contextPath }/js/jquery.nice-select.min.js"></script>
			<script src="${pageContext.request.contextPath }/js/jquery.nicescroll.min.js"></script>
			<script src="${pageContext.request.contextPath }/js/jquery.magnific-popup.min.js"></script>
			<script src="${pageContext.request.contextPath }/js/jquery.countdown.min.js"></script>
			<script src="${pageContext.request.contextPath }/js/jquery.slicknav.js"></script>
			<script src="${pageContext.request.contextPath }/js/mixitup.min.js"></script>
	</body>

</html>