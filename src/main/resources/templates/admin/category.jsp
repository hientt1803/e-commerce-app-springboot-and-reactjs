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
		<title>Category</title>
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/style-admin.css" />
		<link rel="stylesheet"
			  href="${pageContext.request.contextPath}/css/font-awesome.min.css"
			  type="text/css" />
		<!-- Bootstrap 4.4.1 -->
		<!-- <link rel="stylesheet"
			  href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
			  integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
			  crossorigin="anonymous" /> -->
		<script src="https://kit.fontawesome.com/c0f581682c.js"
				crossorigin="anonymous"></script>
		<style>
			.video-edit-preview {
				width: 250px;
				height: 250px;
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

		<c:if test="${not empty messageCategoryManager}">
			<!-- toast msg  -->
			<div class="toast show"
				 role="alert"
				 aria-live="assertive"
				 aria-atomic="true"
				 data-delay="3000">
				<div class="toast-header">
					<strong class="mr-auto text-center">Quản lý danh mục</strong>
					<button type="button"
							class="ml-2 mb-1 close"
							data-dismiss="toast"
							aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="toast-body alert-info">${messageCategoryManager}</div>
			</div>
			<!-- toast msg  -->

			<script>
				setTimeout(() => {
					const toast = document.querySelector('.toast');
					toast.classList.remove('show');
				}, 5000);
			</script>

			<%
		session.removeAttribute("messageCategoryManager");
		%>
		</c:if>

		<div class="app-container">
			<!-- Sidebar -->
			<jsp:include page="sidebar.jsp" />

			<!-- Content -->
			<div class="app-content">
				<!-- Top content -->
				<jsp:include page="top-content.jsp">
					<jsp:param name="title"
							   value="${title}" />
				</jsp:include>

				<!-- -------------------------------------------------------- -->
				<!-- App actions -->
				<div class="app-content-actions">
					<!-- Search input -->
					<form action="/admin/category-manager/search-category"
						  method="POST">
						<input class="search-bar"
							   name="keywords"
							   value="${keywords}"
							   placeholder="Tìm kiếm..."
							   type="text" />
					</form>
					<div class="app-content-actions-wrapper">
						<div class="filter-button-wrapper d-flex justify-content-betwwen">
							<a href="/admin/category-manager/add"
							   class="btn btn-dark">Thêm
								Danh mục</a>
						</div>
					</div>
				</div>

				<!-- Table list -->
				<div class="products-area-wrapper tableView overflow-auto">
					<!-- 	Product table header -->
					<div class="products-header">
						<div class="product-cell image">Mã danh mục</div>
						<div class="product-cell category">
							Tên danh mục<a href="/admin/category-manager?field=id&p=${p}&eop=${eop}&d=${!d}"
							   class="sort-button  ${field == 'name' ? 'text-primary' : '' }">
								${field == 'name' && d == true
								? '<i class="fa fa-chevron-up"
								   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
								   aria-hidden="true"></i>'}
							</a>
						</div>
						<div class="product-cell category">
							Thao tác

						</div>
					</div>

					<!-- 	Loop	 -->
					<c:forEach var="category"
							   items="${page.content}"
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

							<div class="product-cell id">
								<span>${category.id}</span>
							</div>
							<div class="product-cell name">
								<span>${category.name}</span>
							</div>
							<div class="product-cell action">
								<div class="row">
									<div class="col-6">
										<a href="/admin/category-manager/edit/${category.id}"
										   class="btn btn-outline-primary font-weight-bold"
										   style="width: 150px">Cập nhật </a>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>

					<!-- Pagination -->
					<div class="row">
						<div class="col-lg-12">
							<div class="product__pagination d-flex justify-content-center">
								<!--* paging start  -->
								<c:choose>
									<c:when test="${page.content.size() > 1}">
										<nav aria-label="Page navigation example">
											<ul class="pagination pagination-sm mb-0 py-1 ml-3">
												<li class="page-item ${ p  == 0 ?'d-none'  : '' } "><a
													   class="page-link mr-3"
													   href="/admin/category-manager?field=${field}&p=0&eop=${eop}&d=${d}">First</a>
												</li>
												<c:forEach begin="0"
														   end="${page.totalPages  - 1 }"
														   varStatus="loop">
													<li class="page-item ${ loop.index == page.number ? 'active': ''} ">
														<a class="page-link"
														   href="/admin/category-manager?field=${field}&p=${loop.index}&eop=${eop}&d=${d}">
															${loop.count} </a>
													</li>
												</c:forEach>
												<li class="page-item  ${ p  == page.totalPages - 1  ?'d-none'  : '' }">
													<a class="page-link ml-3"
													   href="/admin/category-manager?field=${field}&p=${page.totalPages - 1 }&eop=${eop}&d=${d}">Last</a>
												</li>
											</ul>
										</nav>
									</c:when>
								</c:choose>
								<!--* paging end -->
							</div>
						</div>
						</ </div>
					</div>
				</div>
				<!-- Bootstrap 4.4.1 -->
				<!-- <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
				integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
				crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
				integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
				crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js"
				integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
				crossorigin="anonymous"></script>
		<script src="${pageContext.request.contextPath}/js/script.js"></script> -->

				<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
						integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
						crossorigin="anonymous"></script>
				<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
						integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
						crossorigin="anonymous"></script>
				<!-- Js Plugins -->
				<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
				<script src="${pageContext.request.contextPath}/js/script.js"></script>
	</body>

</html>