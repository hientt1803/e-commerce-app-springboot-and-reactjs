<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib
uri="http://www.springframework.org/tags/form" prefix="form" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
          href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style-admin.css" />
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/font-awesome.min.css"
          type="text/css" />
    <script src="https://kit.fontawesome.com/c0f581682c.js"
            crossorigin="anonymous"></script>
  </head>

  <body onload="$('#UpdateStatus').toast('show')">
    <div class="app-container position-relative ">
      <!-- Sidebar -->
      <jsp:include page="sidebar.jsp" />
      <div class="app-content">
        <!-- toast msg  -->
        <div class="toast position-absolute"
             id="${isUpdated == true ? 'UpdateStatus' : ''}"
             role="alert"
             aria-live="assertive"
             aria-atomic="true"
             data-delay="3000"
             style="top:0.5rem;
                     right: 2rem;
                     z-index: 100;">
          <div class="toast-header">
            <strong class="mr-auto text-center">Hệ thống</strong>
            <button type="button"
                    class="ml-2 mb-1 close"
                    data-dismiss="toast"
                    aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="toast-body alert-info">Cập nhật trạng thái đơn hàng thành công !</div>
        </div>
        <!-- toast msg  -->
        <!-- Top content -->
        <jsp:include page="top-content.jsp">
          <jsp:param name="title"
                     value="${title}" />
        </jsp:include>
        <div class="app-content-actions">
          <form action="/admin/order">
            <div class="input-group">
              <select class="custom-select"
                      value="${searchKey}"
                      id="searchKey"
                      name="searchKey">
                <option disabled
                        selected
                        value="defaultVal">Chọn tiêu chí tìm kiếm</option>
                <option value="date"
                        ${searchKey=='date'
                        ? 'selected'
                        : ''
                        }>Tìm theo ngày</option>
                <option value="month"
                        ${searchKey=='month'
                        ? 'selected'
                        : ''
                        }>Tìm theo tháng</option>
              </select>
              <div class="input-group-append">
                <input type="month"
                       class="form-control"
                       id="filterByDate"
                       value="${searchVal}"
                       name="searchVal">
              </div>
              <div class="input-group-append">
                <button class="btn btn-sm btn-primary">Xem</button>
              </div>
            </div>
          </form>
          <div class="filter-button-wrapper d-flex justify-content-betwwen">
            <a href="/admin/order"
               class="ml-3 btn btn-sm btn-outline-primary "
               title="Làm mới">Làm mới <i class="fa fa-refresh"
                 aria-hidden="true"></i></a>
          </div>
          <div class="w-50 d-flex justify-content-center">
            <form action="/admin/order">
              <select name="status"
                      class="custom-select w-100"
                      id=""
                      onchange="this.form.submit()">
                <option value=""
                        disabled>Chọn trạng thái </option>
                <option ${status=='C'
                        ? 'selected'
                        : ''
                        }
                        value="C">Đang chờ</option>
                <option ${status=='XL'
                        ? 'selected'
                        : ''
                        }
                        value="XL">Đang xử lý</option>
                <option ${status=='G'
                        ? 'selected'
                        : ''
                        }
                        value="G">Đang giao</option>
                <option ${status=='DG'
                        ? 'selected'
                        : ''
                        }
                        value="DG">Đã giao</option>
              </select>
            </form>
            <!-- * select row to display -->
            <div class="input-group input-group-sm  ml-3 w-50">
              <div class="product-cell status-cell d-flex flex-column justify-content-center ml-3">
                <span class="status active py-2">Hiển thị <strong class="mx-2">${page.numberOfElements} /
                    ${page.totalElements}</strong>
                  đơn hàng</span>
              </div>
            </div>
            <!-- * select row to display -->
            <!--* paging start  -->
            <c:choose>
              <c:when test="${page.content.size() > 0}">
                <div class="my-auto">Trang:</div>
                <nav aria-label="Page navigation example">
                  <ul class="pagination pagination-sm mb-0 py-1 ml-3">
                    <li class="page-item ${ p  == 0 ?'d-none'  : '' } "><a class="page-link"
                         href="/admin/order?p=0&eop=${eop}">First</a></li>
                    <c:forEach begin="0"
                               end="${page.totalPages  - 1 }"
                               varStatus="loop">
                      <li class="page-item ${ loop.index == page.number ? 'active': ''} ">
                        <a class="page-link"
                           href="/admin/user?p=${loop.index}&eop=${eop}">
                          ${loop.count}
                        </a>
                      </li>
                    </c:forEach>
                    <li class="page-item  ${ p  == page.totalPages - 1  ?'d-none'  : '' }"><a class="page-link"
                         href="/admin/user?p=${page.totalPages - 1 }&eop=${eop}">Last</a>
                    </li>
                  </ul>
                </nav>
              </c:when>
            </c:choose>
            <!--* paging end -->
          </div>
          <div class="app-content-actions-wrapper">
            <!-- order canceled -->
            <button class="btn btn-warning btn-sm mx-2"
                    title="Xem đơn hàng đã hủy "
                    data-toggle="modal"
                    data-target="#modalCanceledOrder"
                    style="z-index: 1000;">Đơn đã hủy</button>
            <!-- Modal  order canceled -->
            <div class="modal fade"
                 id="modalCanceledOrder"
                 tabindex="-1"
                 role="dialog"
                 aria-labelledby="modelTitleId"
                 aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered modal-lg "
                   style="min-width: 1200px;"
                   role="document">
                <div class="modal-content"
                     style="min-width: 1200px;">
                  <div class="modal-header alert alert-warning">
                    <h3 class="modal-title w-100 text-center "> ĐƠN HÀNG ĐÃ HỦY</h3>
                    <button type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <table class="table table-hover table-striped">
                      <thead class="thead-inverse">
                        <tr>
                          <th>#</th>
                          <th>Mã đơn hàng</th>
                          <th>Khách hàng</th>
                          <th>Tổng tiền </th>
                          <th>Ngày đặt</th>
                          <th>Lý do hủy</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach var="oc"
                                   items="${orderCanceleds}"
                                   varStatus="loop">
                          <tr>
                            <td>${loop.count}</td>
                            <td>${oc.id}</td>
                            <td>${oc.account.username}</td>
                            <td>
                              <fmt:formatNumber value="${oc.totalPrice}"
                                                type="currency"
                                                currencySymbol="" />
                              <sup>đ</sup>
                            </td>
                            <td>${oc.createDate}</td>
                            <td>${oc.notes}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <!-- Modal -->
          </div>
        </div>
        <div class="products-area-wrapper tableView">
          <div class="products-header">
            <div class=" mx-3">#</div>
            <div class="product-cell image">
              Họ tên khách hàng
            </div>
            <div class="product-cell category">
              Tổng tiền
            </div>
            <div class="product-cell status-cell">
              Trạng thái đơn
            </div>
            <div class="product-cell sales">
              Ngày đặt
            </div>
            <div class="product-cell stock">
              Địa chỉ
            </div>
            <div class="product-cell price">Thao tác</div>
          </div>
          <c:choose>
            <c:when test="${page.isEmpty()}">
              <div class="alert alert-info text-center mt-5 mx-auto w-75 py-3">Không tìm thấy đơn hàng trong ${
                searchKey == 'date' ? 'Ngày' : 'Tháng'}
                <fmt:formatDate pattern="MM-yyyy"
                                value="${searchVal}" />!
              </div>
            </c:when>
            <c:when test="${!page.isEmpty() && isFilterByStatusEmpty == null }">
              <c:forEach var="o"
                         items="${ page.content }"
                         varStatus="loop">
                <div class="products-row">
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
                  <div class="mx-4">
                    <span>${ loop.count } </span>
                  </div>
                  <div class="product-cell">
                    <span>${o.account.fullname}</span>
                  </div>
                  <div class="product-cell category">
                    <span class="cell-label">Tổng tiền</span>
                    <fmt:formatNumber value="${o.totalPrice}"
                                      type="currency"
                                      currencySymbol="" />
                    <sup>đ</sup>
                  </div>
                  <div class="product-cell status-cell">
                    <span class="cell-label">Trạng thái</span>
                    <span class="status ${o.status =='DG' ? 'active' : 'disabled'} ">
                      <c:choose>
                        <c:when test="${o.status =='C'}">
                          Đang chờ
                        </c:when>
                        <c:when test="${o.status =='XL'}">
                          Đang xử lý
                        </c:when>
                        <c:when test="${o.status =='G'}">
                          Đang giao
                        </c:when>
                        <c:when test="${o.status =='DG'}">
                          Đã giao
                        </c:when>
                        <c:when test="${o.status =='H'}">
                          Đã hủy
                        </c:when>
                        <c:otherwise>
                          Đang chờ
                        </c:otherwise>
                      </c:choose>
                    </span>
                  </div>
                  <div class="product-cell sales">
                    <span class="cell-label">Ngày đặt</span>${o.createDate}
                  </div>
                  <div class="product-cell address">
                    <span class="cell-label">Địa chỉ </span>${o.address}
                  </div>
                  <div class="product-cell price">
                    <span class="cell-label"> </span>
                    <button class="btn btn-sm btn-primary mr-3"
                            data-toggle="modal"
                            data-target="#updateModal${o.id}">
                      Cập nhật trạng thái
                    </button>
                    <button class="btn btn-sm btn-info"
                            data-toggle="modal"
                            data-target="#viewModal${o.id}">
                      <i class="fa fa-info-circle"
                         aria-hidden="true"></i>
                    </button>
                  </div>
                  <!-- Modal -->
                  <div class="modal fade"
                       id="viewModal${o.id}"
                       tabindex="-1"
                       role="dialog"
                       aria-labelledby="modelTitleId"
                       aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg "
                         style="min-width: 1200px;"
                         role="document">
                      <div class="modal-content"
                           style="min-width: 1200px;">
                        <div class="modal-header alert alert-info">
                          <h3 class="modal-title w-100 text-center ">CHI TIẾT ĐƠN HÀNG </h3>
                          <button type="button"
                                  class="close"
                                  data-dismiss="modal"
                                  aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <div class="row rounded">
                            <div class="col-4">
                              <div class="card border-primary ">
                                <div class="card-header">
                                  <h4 class="w-100 text-center mb-0">THÔNG TIN KHÁCH HÀNG</h4>
                                </div>
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item"><span>Mã khách hàng</span>
                                    <strong class="text-danger">${o.account.username}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Họ tên:</span>
                                    <strong class="text-danger">${o.account.fullname}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Địa chỉ</span>
                                    <strong class="text-danger">${o.address}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Số điện thoại</span>
                                    <strong class="text-danger">${o.phone}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Ngày đặt hàng</span>
                                    <strong class="text-danger">${o.createDate}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Email</span>
                                    <strong class="text-danger">${o.account.email}</strong>
                                  </li>
                                </ul>
                              </div>
                              <div class="card mt-3 border-success">
                                <div class="card-header">
                                  <h4 class="w-100 text-center mb-0">THÔNG TIN ĐƠN HÀNG</h4>
                                </div>
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item">
                                    <span>Mã đơn hàng</span>
                                    <strong class="text-danger">${o.id}</strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Tạm tính:</span>
                                    <strong class="text-danger">
                                      <fmt:formatNumber value=" ${o.totalPrice - (o.coupon.discountAmount * o.totalPrice)}"
                                                        type="currency"
                                                        currencySymbol="" />
                                      <sup>đ</sup>
                                    </strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Giảm giá:</span>
                                    <strong class="text-danger">
                                      <fmt:formatNumber value="${ o.totalPrice / (1- o.coupon.discountAmount ) * o.coupon.discountAmount }"
                                                        type="currency"
                                                        currencySymbol="" />
                                      <sup>đ</sup>
                                    </strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Thành tiền:</span>
                                    <strong class="text-danger">
                                      <fmt:formatNumber value="${o.totalPrice} "
                                                        type="currency"
                                                        currencySymbol="" />
                                      <sup>đ</sup>
                                    </strong>
                                  </li>
                                </ul>
                              </div>
                            </div>
                            <div class="col-8">
                              <table class="table table-hover table-striped">
                                <thead>
                                  <tr>
                                    <th>#</th>
                                    <th>Ảnh SP</th>
                                    <th>Tên SP</th>
                                    <th>Số lượng</th>
                                    <th class="mx-3">Tổng cộng</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <c:forEach var="orderDetail"
                                             items="${o.orderDetails}"
                                             varStatus="loop">
                                    <tr class="">
                                      <td>${loop.count}</td>
                                      <td class="product__cart__item">
                                        <img src="${pageContext.request.contextPath}/img/product/${orderDetail.product.image}"
                                             alt=""
                                             width="35px"
                                             height="35px"
                                             class="" />
                                      </td>
                                      <td class="product__cart__item">
                                        <h6 class="">${orderDetail.product.name}</h6>
                                      </td>
                                      <td class="quantity__item">
                                        <div class="quantity">
                                          <div class="pro-qty-2 text-center">
                                            <span>${orderDetail.quantity}</span>
                                          </div>
                                        </div>
                                      </td>
                                      <td class="cart__price">${orderDetail.price}<sup>đ</sup></td>
                                    </tr>
                                  </c:forEach>
                                </tbody>
                              </table>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- Modal -->
                  <!-- update modal -->
                  <div class="modal fade"
                       id="updateModal${o.id}"
                       tabindex="-1"
                       role="dialog"
                       aria-labelledby="modelTitleId"
                       aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg"
                         role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">Cập nhật trạng thái đơn hàng</h5>
                          <button type="button"
                                  class="close"
                                  data-dismiss="modal"
                                  aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <form action="/admin/order"
                                method="post">
                            <div class="row rounded">
                              <div class="col-12">
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Mã khách hàng</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.account.username}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Tên khách hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.account.fullname}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Tổng tiền đặt hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.totalPrice} <sup>đ</sup></span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Ngày đặt hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.createDate}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Trạng thái đơn hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <div class="input-group">
                                      <input type="hidden"
                                             name="id"
                                             class="id"
                                             value="${o.id}">
                                      <select class="custom-select custom-select-sm selectStatus"
                                              id=""
                                              name="status">
                                        <option selected>Chọn trạng thái</option>
                                        <c:choose>
                                          <c:when test="${o.status == 'C'}">
                                            <option onchange="console.log(value)"
                                                    value="XL"
                                                    class="text-primary">
                                              Đang xử lý
                                            </option>
                                          </c:when>
                                          <c:when test="${o.status == 'XL'}">
                                            <option onchange="console.log(value)"
                                                    value="G"
                                                    class="text-primary">
                                              Đang giao
                                            </option>
                                          </c:when>
                                          <c:when test="${o.status == 'G'}">
                                            <option onchange="console.log(value)"
                                                    value="DG"
                                                    class="text-primary">
                                              Đã giao
                                            </option>
                                          </c:when>


                                        </c:choose>
                                        <c:if test="${o.status != 'DG'}">
                                          <option onchange="console.log(value)"
                                                  value="H"
                                                  class="text-primary">
                                            Hủy
                                          </option>
                                        </c:if>
                                      </select>
                                    </div>
                                    <div class="my-2 notes "
                                         id="">
                                    </div>
                                  </div>
                                </div>
                                <!-- Submit Button -->
                                <div class="form-group col-lg-12 mx-auto mb-4">
                                  <hr />
                                  <button class="btn btn-danger float-right w-100">
                                    <span class="font-weight-bold">Cập nhật</span>
                                  </button>
                                </div>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- update modal -->
                </div>
              </c:forEach>
            </c:when>
            <c:when test="${!page.isEmpty() && searchKey != null }">
              <c:forEach var="o"
                         items="${ page.content }"
                         varStatus="loop">
                <div class="products-row">
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
                  <div class="mx-4">
                    <span>${ loop.count } </span>
                  </div>
                  <div class="product-cell">
                    <span>${o.account.fullname}</span>
                  </div>
                  <div class="product-cell category">
                    <span class="cell-label">Tổng tiền</span>${o.totalPrice}<sup>đ</sup>
                  </div>
                  <div class="product-cell status-cell">
                    <span class="cell-label">Trạng thái</span>
                    <span class="status ${o.status =='DG' ? 'active' : 'disabled'} ">
                      <c:choose>
                        <c:when test="${o.status =='C'}">
                          Đang chờ
                        </c:when>
                        <c:when test="${o.status =='XL'}">
                          Đang xử lý
                        </c:when>
                        <c:when test="${o.status =='G'}">
                          Đang giao
                        </c:when>
                        <c:when test="${o.status =='DG'}">
                          Đã giao
                        </c:when>
                        <c:when test="${o.status =='H'}">
                          Đã hủy
                        </c:when>
                        <c:otherwise>
                          Đang chờ
                        </c:otherwise>
                      </c:choose>
                    </span>
                  </div>
                  <div class="product-cell sales">
                    <span class="cell-label">Ngày đặt</span>${o.createDate}
                  </div>
                  <div class="product-cell address">
                    <span class="cell-label">Địa chỉ </span>${o.address}
                  </div>
                  <div class="product-cell price">
                    <span class="cell-label"> </span>
                    <button class="btn btn-sm btn-primary mr-3"
                            data-toggle="modal"
                            data-target="#updateModal${o.id}">
                      Cập nhật trạng thái
                    </button>
                    <button class="btn btn-sm btn-info"
                            data-toggle="modal"
                            data-target="#viewModal${o.id}">
                      <i class="fa fa-info-circle"
                         aria-hidden="true"></i>
                    </button>
                  </div>
                  <!-- Modal -->
                  <div class="modal fade"
                       id="viewModal${o.id}"
                       tabindex="-1"
                       role="dialog"
                       aria-labelledby="modelTitleId"
                       aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg "
                         style="min-width: 1200px;"
                         role="document">
                      <div class="modal-content"
                           style="min-width: 1200px;">
                        <div class="modal-header alert alert-info">
                          <h3 class="modal-title w-100 text-center ">CHI TIẾT ĐƠN HÀNG </h3>
                          <button type="button"
                                  class="close"
                                  data-dismiss="modal"
                                  aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <div class="row rounded">
                            <div class="col-4">
                              <div class="card border-primary ">
                                <div class="card-header">
                                  <h4 class="w-100 text-center mb-0">THÔNG TIN KHÁCH HÀNG</h4>
                                </div>
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item"><span>Mã khách hàng</span>
                                    <strong class="text-danger">${o.account.username}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Họ tên:</span>
                                    <strong class="text-danger">${o.account.fullname}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Địa chỉ</span>
                                    <strong class="text-danger">${o.address}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Số điện thoại</span>
                                    <strong class="text-danger">${o.phone}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Ngày đặt hàng</span>
                                    <strong class="text-danger">${o.createDate}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Email</span>
                                    <strong class="text-danger">${o.account.email}</strong>
                                  </li>
                                </ul>
                              </div>
                              <div class="card mt-3 border-success">
                                <div class="card-header">
                                  <h4 class="w-100 text-center mb-0">THÔNG TIN ĐƠN HÀNG</h4>
                                </div>
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item">
                                    <span>Mã đơn hàng</span>
                                    <strong class="text-danger">${o.id}</strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Tạm tính:</span>
                                    <strong class="text-danger">${o.totalPrice - (o.coupon.discountAmount *
                                      o.totalPrice)
                                      }<sup>đ</sup></strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Giảm giá:</span>
                                    <strong class="text-danger">
                                      <fmt:formatNumber value="${ o.totalPrice / (1- o.coupon.discountAmount ) * o.coupon.discountAmount }"
                                                        type="currency"
                                                        currencySymbol="" />
                                      <sup>đ</sup>
                                    </strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Thành tiền:</span>
                                    <strong class="text-danger">
                                      <fmt:formatNumber value="${o.totalPrice} "
                                                        type="currency"
                                                        currencySymbol="" /><sup>đ</sup>
                                    </strong>
                                  </li>
                                </ul>
                              </div>
                            </div>
                            <div class="col-8">
                              <table class="table table-hover table-striped">
                                <thead>
                                  <tr>
                                    <th>#</th>
                                    <th>Ảnh SP</th>
                                    <th>Tên SP</th>
                                    <th>Số lượng</th>
                                    <th class="mx-3">Tổng cộng</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <c:forEach var="orderDetail"
                                             items="${o.orderDetails}"
                                             varStatus="loop">
                                    <tr class="">
                                      <td>${loop.count}</td>
                                      <td class="product__cart__item">
                                        <img src="${pageContext.request.contextPath}/img/product/${orderDetail.product.image}"
                                             alt=""
                                             width="35px"
                                             height="35px"
                                             class="" />
                                      </td>
                                      <td class="product__cart__item">
                                        <h6 class="">${orderDetail.product.name}</h6>
                                      </td>
                                      <td class="quantity__item">
                                        <div class="quantity">
                                          <div class="pro-qty-2 text-center">
                                            <span>${orderDetail.quantity}</span>
                                          </div>
                                        </div>
                                      </td>
                                      <td class="cart__price">${orderDetail.price}<sup>đ</sup></td>
                                    </tr>
                                  </c:forEach>
                                </tbody>
                              </table>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- Modal -->
                  <!-- update modal -->
                  <div class="modal fade"
                       id="updateModal${o.id}"
                       tabindex="-1"
                       role="dialog"
                       aria-labelledby="modelTitleId"
                       aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg"
                         role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">Cập nhật trạng thái đơn hàng</h5>
                          <button type="button"
                                  class="close"
                                  data-dismiss="modal"
                                  aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <form action="/admin/order"
                                method="post">
                            <div class="row rounded">
                              <div class="col-12">
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Mã khách hàng</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.account.username}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Tên khách hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.account.fullname}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Tổng tiền đặt hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.totalPrice} <sup>đ</sup></span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Ngày đặt hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.createDate}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Trạng thái đơn hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <div class="input-group">
                                      <input type="hidden"
                                             name="id"
                                             value="${o.id}">
                                      <select class="custom-select custom-select-sm selectStatus"
                                              id=""
                                              name="status">
                                        <option selected>Chọn trạng thái</option>
                                        <c:forEach var="s"
                                                   items="${listStatus}">
                                          <option onchange="console.log(value)"
                                                  value="${s.key}"
                                                  class="text-primary">
                                            ${s.value}
                                          </option>
                                        </c:forEach>
                                      </select>
                                    </div>
                                    <div class="my-2 notes "
                                         id="">
                                    </div>
                                  </div>
                                </div>
                                <!-- Submit Button -->
                                <div class="form-group col-lg-12 mx-auto mb-4">
                                  <hr />
                                  <button class="btn btn-danger float-right w-100">
                                    <span class="font-weight-bold">Cập nhật</span>
                                  </button>
                                </div>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- update modal -->
                </div>
              </c:forEach>
            </c:when>
            <c:when test="${isFilterByStatusEmpty}">
              <div class="alert alert-info text-center mt-5 mx-auto w-75 py-3">Không tìm thấy đơn hàng có trạng thái
                ${status == 'C' ? 'Đang chờ' : ''}${status == 'XL' ? 'Đang xử lý' : ''} ${status == 'G' ? 'Đang giao' :
                ''} ${status ==
                'DG' ? 'Đã giao' : ''}
              </div>
            </c:when>
            <c:when test="${ !filterByStatus.isEmpty()}">
              <c:forEach var="o"
                         items="${ filterByStatus }"
                         varStatus="loop">
                <div class="products-row">
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
                  <div class="mx-4">
                    <span>${ loop.count } </span>
                  </div>
                  <div class="product-cell">
                    <span>${o.account.fullname}</span>
                  </div>
                  <div class="product-cell category">
                    <span class="cell-label">Tổng tiền</span>${o.totalPrice}<sup>đ</sup>
                  </div>
                  <div class="product-cell status-cell">
                    <span class="cell-label">Trạng thái</span>
                    <span class="status ${o.status =='DG' ? 'active' : 'disabled'} ">
                      <c:choose>
                        <c:when test="${o.status =='C'}">
                          Đang chờ
                        </c:when>
                        <c:when test="${o.status =='XL'}">
                          Đang xử lý
                        </c:when>
                        <c:when test="${o.status =='G'}">
                          Đang giao
                        </c:when>
                        <c:when test="${o.status =='DG'}">
                          Đã giao
                        </c:when>
                        <c:when test="${o.status =='H'}">
                          Đã hủy
                        </c:when>
                        <c:otherwise>
                          Đang chờ
                        </c:otherwise>
                      </c:choose>
                    </span>
                  </div>
                  <div class="product-cell sales">
                    <span class="cell-label">Ngày đặt</span>${o.createDate}
                  </div>
                  <div class="product-cell address">
                    <span class="cell-label">Địa chỉ </span>${o.address}
                  </div>
                  <div class="product-cell price">
                    <span class="cell-label"> </span>
                    <button class="btn btn-sm btn-primary mr-3"
                            data-toggle="modal"
                            data-target="#updateModal${o.id}">
                      Cập nhật trạng thái
                    </button>
                    <button class="btn btn-sm btn-info"
                            data-toggle="modal"
                            data-target="#viewModal${o.id}">
                      <i class="fa fa-info-circle"
                         aria-hidden="true"></i>
                    </button>
                  </div>
                  <!-- Modal -->
                  <div class="modal fade"
                       id="viewModal${o.id}"
                       tabindex="-1"
                       role="dialog"
                       aria-labelledby="modelTitleId"
                       aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg "
                         style="min-width: 1200px;"
                         role="document">
                      <div class="modal-content"
                           style="min-width: 1200px;">
                        <div class="modal-header alert alert-info">
                          <h3 class="modal-title w-100 text-center ">CHI TIẾT ĐƠN HÀNG </h3>
                          <button type="button"
                                  class="close"
                                  data-dismiss="modal"
                                  aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <div class="row rounded">
                            <div class="col-4">
                              <div class="card border-primary ">
                                <div class="card-header">
                                  <h4 class="w-100 text-center mb-0">THÔNG TIN KHÁCH HÀNG</h4>
                                </div>
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item"><span>Mã khách hàng</span>
                                    <strong class="text-danger">${o.account.username}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Họ tên:</span>
                                    <strong class="text-danger">${o.account.fullname}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Địa chỉ</span>
                                    <strong class="text-danger">${o.address}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Số điện thoại</span>
                                    <strong class="text-danger">${o.phone}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Ngày đặt hàng</span>
                                    <strong class="text-danger">${o.createDate}</strong>
                                  </li>
                                  <li class="list-group-item"><span>Email</span>
                                    <strong class="text-danger">${o.account.email}</strong>
                                  </li>
                                </ul>
                              </div>
                              <div class="card mt-3 border-success">
                                <div class="card-header">
                                  <h4 class="w-100 text-center mb-0">THÔNG TIN ĐƠN HÀNG</h4>
                                </div>
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item">
                                    <span>Mã đơn hàng</span>
                                    <strong class="text-danger">${o.id}</strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Tạm tính:</span>
                                    <strong class="text-danger">${o.totalPrice - (o.coupon.discountAmount *
                                      o.totalPrice)
                                      }<sup>đ</sup></strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Giảm giá:</span>
                                    <strong class="text-danger">
                                      <fmt:formatNumber value="${ o.totalPrice / (1- o.coupon.discountAmount ) * o.coupon.discountAmount }"
                                                        type="currency"
                                                        currencySymbol="" />
                                      <sup>đ</sup>
                                    </strong>
                                  </li>
                                  <li class="list-group-item">
                                    <span>Thành tiền:</span>
                                    <strong class="text-danger">
                                      <fmt:formatNumber value="${o.totalPrice} "
                                                        type="currency"
                                                        currencySymbol="" /><sup>đ</sup>
                                    </strong>
                                  </li>
                                </ul>
                              </div>
                            </div>
                            <div class="col-8">
                              <table class="table table-hover table-striped">
                                <thead>
                                  <tr>
                                    <th>#</th>
                                    <th>Ảnh SP</th>
                                    <th>Tên SP</th>
                                    <th>Số lượng</th>
                                    <th class="mx-3">Tổng cộng</th>
                                  </tr>
                                </thead>
                                <tbody>
                                  <c:forEach var="orderDetail"
                                             items="${o.orderDetails}"
                                             varStatus="loop">
                                    <tr class="">
                                      <td>${loop.count}</td>
                                      <td class="product__cart__item">
                                        <img src="${pageContext.request.contextPath}/img/product/${orderDetail.product.image}"
                                             alt=""
                                             width="35px"
                                             height="35px"
                                             class="" />
                                      </td>
                                      <td class="product__cart__item">
                                        <h6 class="">${orderDetail.product.name}</h6>
                                      </td>
                                      <td class="quantity__item">
                                        <div class="quantity">
                                          <div class="pro-qty-2 text-center">
                                            <span>${orderDetail.quantity}</span>
                                          </div>
                                        </div>
                                      </td>
                                      <td class="cart__price">${orderDetail.price}<sup>đ</sup></td>
                                    </tr>
                                  </c:forEach>
                                </tbody>
                              </table>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- Modal -->
                  <!-- update modal -->
                  <div class="modal fade"
                       id="updateModal${o.id}"
                       tabindex="-1"
                       role="dialog"
                       aria-labelledby="modelTitleId"
                       aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered modal-lg"
                         role="document">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title">Cập nhật trạng thái đơn hàng</h5>
                          <button type="button"
                                  class="close"
                                  data-dismiss="modal"
                                  aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                          </button>
                        </div>
                        <div class="modal-body">
                          <form action="/admin/order"
                                method="post">
                            <div class="row rounded">
                              <div class="col-12">
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Mã khách hàng</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.account.username}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Tên khách hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.account.fullname}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Tổng tiền đặt hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.totalPrice} <sup>đ</sup></span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Ngày đặt hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <span>${o.createDate}</span>
                                  </div>
                                </div>
                                <div class="row mb-3">
                                  <div class="col-6">
                                    <span>Trạng thái đơn hàng:</span>
                                  </div>
                                  <div class="col-6">
                                    <div class="input-group">
                                      <input type="hidden"
                                             name="id"
                                             value="${o.id}">
                                      <select class="custom-select custom-select-sm selectStatus"
                                              id=""
                                              name="status">
                                        <option selected>Chọn trạng thái</option>
                                        <c:forEach var="s"
                                                   items="${listStatus}">
                                          <option onchange="console.log(value)"
                                                  value="${s.key}"
                                                  class="text-primary">
                                            ${s.value}
                                          </option>
                                        </c:forEach>
                                      </select>
                                    </div>
                                    <div class="my-2 notes"
                                         id="">
                                    </div>
                                  </div>
                                </div>
                                <!-- Submit Button -->
                                <div class="form-group col-lg-12 mx-auto mb-4">
                                  <hr />
                                  <button class="btn btn-danger float-right w-100">
                                    <span class="font-weight-bold">Cập nhật</span>
                                  </button>
                                </div>
                              </div>
                            </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- update modal -->
                </div>
              </c:forEach>
            </c:when>
            <c:when test="${page.isEmpty()}">
              <div class="alert alert-info text-center mt-5 mx-auto w-75 py-3">Không tìm thấy đơn hàng trong ${
                searchKey == 'date' ? 'Ngày' : 'Tháng'}
                <fmt:formatDate pattern="MM-yyyy"
                                value="${searchVal}" />!
              </div>
            </c:when>
          </c:choose>
        </div>
      </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <!-- Js Plugins -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script>
      $("#viewModal").on("show.bs.modal", (event) => {
        var button = $(event.relatedTarget);
        var modal = $(this);
        // Use above variables to manipulate the DOM
      });
      const selects = document.querySelectorAll('.selectStatus');
      var notes = document.querySelectorAll(".notes")
      let label = document.createElement('label')
      let txt = document.createElement('textarea')
      let br = document.createElement('br')
      label.append("Lý do hủy đơn hàng")
      for (let i = 0; i < selects.length; i++) {
        selects[i].addEventListener('change', function handleChange(event) {
          if (event.target.value === 'H') {
            notes[i].append(label)
            notes[i].append(br)
            notes[i].append(txt)
            txt.name = 'notes'
          } else {
            notes[i].removeChild(txt)
            txt.value = ''
            notes[i].removeChild(br)
            notes[i].removeChild(label)
          }
        });

      }


      var searchKey = document.getElementById('searchKey')
      var filterByDate = document.getElementById('filterByDate')
      window.onload = function (e) {
        filterByDate.readOnly = true
        if (e.target.value === 'defaultVal') {
        }
        if (searchKey.value === 'date') {
          filterByDate.type = 'date'
        }
        if (searchKey.value === 'month') {
          filterByDate.type = 'month'
        }
      }
      searchKey.addEventListener('change', function handleSearchChange(e) {
        filterByDate.readOnly = false
        if (e.target.value === 'date') {
          filterByDate.type = 'date'
        } else if (e.target.value === 'month') {
          filterByDate.type = 'month'
        }
      })
    </script>
  </body>

</html>