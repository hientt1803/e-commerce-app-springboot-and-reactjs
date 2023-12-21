<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible"
          content="IE=edge">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">
    <title>Dashboard </title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style-admin.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/font-awesome.min.css"
          type="text/css" />
    <script src="https://kit.fontawesome.com/c0f581682c.js"
            crossorigin="anonymous"></script>
  </head>

  <body onload="$('#deleteToast').toast('show')">
    <div class="app-container  ">
      <!-- Sidebar -->
      <jsp:include page="sidebar.jsp" />
      <div class="app-content  h-100 position-relative">
        <!-- toast msg  -->
        <div class="toast position-absolute"
             id="${isUpdated == true ? 'deleteToast' : ''}"
             role="alert"
             aria-live="assertive"
             aria-atomic="true"
             data-delay="3000"
             style="top: 3.5rem;
             right:20px;
             transform: translate(-50%,-50%);
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
          <div class="toast-body alert-info">Cập nhật trạng thái người dùng thành công !</div>
        </div>
        <!-- toast msg  -->
        <!-- Top content -->
        <jsp:include page="top-content.jsp">
          <jsp:param name="title"
                     value="${title}" />
        </jsp:include>
        <!-- Top content -->
        <div class="app-content-actions">
          <form action="/admin/search"
                class="">
            <input class="search-bar"
                   placeholder="Tìm kiếm..."
                   type="text"
                   name="keyword"
                   list="user">
            <datalist id="user">
              <c:forEach var="u"
                         items="${users}">
                <option value="${u.fullname}"></option>
              </c:forEach>
            </datalist>
          </form>
          <a href="/admin/user?field=${field}&p=${p}&eop=${eop}&d=${d}"
             class="ml-3 btn btn-sm btn-outline-primary "
             title="Làm mới"><i class="fa fa-refresh"
               aria-hidden="true"></i></a>
          <div class=" ml-3">Trang:</div>
          <!--* paging start  -->
          <c:choose>
            <c:when test="${page.content.size() > 0}">
              <nav aria-label="Page navigation example">
                <ul class="pagination pagination-sm mb-0 py-1 ml-3">
                  <li class="page-item ${ p  == 0 ?'d-none'  : '' } "><a class="page-link"
                       href="/admin/user?field=${field}&p=0&eop=${eop}&d=${d}">First</a></li>
                  <c:forEach begin="0"
                             end="${page.totalPages  - 1 }"
                             varStatus="loop">
                    <li class="page-item ${ loop.index == page.number ? 'active': ''} ">
                      <a class="page-link"
                         href="/admin/user?field=${field}&p=${loop.index}&eop=${eop}&d=${d}">
                        ${loop.count}
                      </a>
                    </li>
                  </c:forEach>
                  <li class="page-item  ${ p  == page.totalPages - 1  ?'d-none'  : '' }"><a class="page-link"
                       href="/admin/user?field=${field}&p=${page.totalPages - 1 }&eop=${eop}&d=${d}">Last</a>
                  </li>
                </ul>
              </nav>
            </c:when>
          </c:choose>
          <!--* paging end -->
          <div class="w-100 d-flex justify-content-center">

            <!-- * select row to display -->
            <div class="input-group input-group-sm  ml-3 w-50">
              <div class="input-group-prepend">
                <label class="input-group-text"
                       for="inputGroupSelect01">Hiển thị</label>
              </div>
              <form action="/admin/user?eop&field=${field}&p=${p}&d=${d}"
                    name="topicSelectionFormId"
                    class=""
                    method="get">
                <select class="custom-select"
                        id="inputGroupSelect01"
                        name="eop"
                        onchange="this.form.submit()">
                  <option ${eop==5
                          ? 'selected'
                          : ''
                          }
                          value="5">5 khách hàng</option>
                  <option ${eop==10
                          ? 'selected'
                          : ''
                          }
                          value="10">10 khách hàng</option>
                  <option ${eop==15
                          ? 'selected'
                          : ''
                          }
                          value="15">15 khách hàng</option>
                </select>
              </form>
              <div class="product-cell status-cell d-flex flex-column justify-content-center ml-3">
                <span class="status active py-2">Hiển thị <strong class="mx-2">${page.numberOfElements} /
                    ${page.totalElements}</strong>
                  người
                  dùng</span>
              </div>
            </div>
            <!-- * select row to display -->
          </div>
          <div class="app-content-actions-wrapper">
            <div class="filter-button-wrapper d-flex justify-content-betwwen">
            </div>
            <button class="action-button list active"
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
            <button class="action-button grid"
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
            <div class="product-cell "
                 style="max-width: 50px;">
              #
            </div>
            <div class="product-cell category">Họ tên<a href="/admin/user?field=fullname&p=${p}&eop=${eop}&d=${!d}"
                 class="sort-button  ${field == 'fullname' ? 'text-primary' : '' }"> ${field == 'fullname' && d == true
                ? '<i class="fa fa-chevron-up"
                   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
                   aria-hidden="true"></i>'}
              </a></div>
            <div class="product-cell image">
              Tên đăng nhập
              <a href="/admin/user?field=username&p=${p}&eop=${eop}&d=${!d}"
                 class="sort-button ${field == 'username' ? 'text-primary' : '' }"> ${field == 'username' &&
                d == true ? '<i class="fa fa-chevron-up"
                   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
                   aria-hidden="true"></i>'}
              </a>
            </div>
            <div class="product-cell status-cell">Trạng thái<a
                 href="/admin/user?field=activated&p=${p}&eop=${eop}&d=${!d}"
                 class="sort-button  ${field == 'activated' ? 'text-primary' : '' }"> ${field == 'activated' && d ==
                true ? '<i class="fa fa-chevron-up"
                   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
                   aria-hidden="true"></i>'}
              </a></div>
            <div class="product-cell sales">Email<a href="/admin/user?field=email&p=${p}&eop=${eop}&d=${!d}"
                 class="sort-button  ${field == 'email' ? 'text-primary' : '' }"> ${field == 'email' && d == true ? '<i
                   class="fa fa-chevron-up"
                   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
                   aria-hidden="true"></i>'}
              </a></div>
            <div class="product-cell stock">Vai trò<a href="/admin/user?field=admin&p=${p}&eop=${eop}&d=${!d}"
                 class="sort-button  ${field == 'admin' ? 'text-primary' : '' }"> ${field == 'admin' && d == true ? '<i
                   class="fa fa-chevron-up"
                   aria-hidden="true"></i>' : '<i class="fa fa-chevron-down"
                   aria-hidden="true"></i>'}
              </a></div>
            <div class="product-cell price">Thao tác
            </div>
          </div>
          <c:choose>
            <c:when test="${isPageEmpty == true}">
              <div class="alert alert-info text-center mt-5 mx-auto w-75 py-3">Không tìm thấy khách hàng có tên <strong>
                  ${keyword}</strong> !
                <br>
                <br>
                <a href="/admin/user?field=${field}&p=${p}&eop=${eop}&d=${d}"
                   class="ml-3 btn btn-sm btn-outline-primary "
                   title="Làm mới">Làm mới <i class="fa fa-refresh"
                     aria-hidden="true"></i></a>
              </div>
            </c:when>
            <c:when test="${isPageEmpty == false}">
              <c:forEach var="u"
                         items="${ page.content }"
                         varStatus="loop">
                <div class="products-row ${loop.index % 2 != 0 ? 'product-row-even':''}">
                  <div class="product-cell "
                       style="max-width: 50px;"><span class="cell-label">STT</span>${loop.count}</div>
                  <div class="product-cell category"><span class="cell-label">Họ và tên</span>${u.fullname}</div>
                  <div class="product-cell image">
                    <img src="${pageContext.request.contextPath}/img/user-management/${u.photo}"
                         alt=""
                         data-bigimage="${pageContext.request.contextPath}/img/user-management/u1.jpg"
                         style="z-index: 1000;"
                         data-toggle="modal"
                         data-target="#prevImg"
                         title="${u.photo == null ? 'Không có hình ảnh': u.photo }">
                    <span>${u.username}</span>
                  </div>
                  <div class="product-cell status-cell">
                    <span class="cell-label">Trạng thái</span>
                    <span class="status ${u.activated == true ? 'active' : 'disabled'}">${u.activated == true ? 'Hoạt
                      động'
                      :
                      'Vô hiệu hóa'}${activated}</span>
                  </div>
                  <div class="product-cell sales"><span class="cell-label">Email</span>${u.email}</div>
                  <div class="product-cell stock"><span class="cell-label">Vai trò</span><span
                          class="status ${u.admin == true ? 'bg-primary' : 'bg-secondary'}  text-white ">
                      ${u.admin == true ? 'Quản trị viên' : 'Khách hàng'}</span></div>
                  <div class="product-cell price"><span class="cell-label"></span>
                    <c:if test="${username != u.username}">
                      <div class="btn btn-sm btn-${u.activated == true ? 'danger' : 'secondary'}"
                           data-toggle="modal"
                           data-target="#deleteModal${u.username}">
                        ${u.activated == true ? 'Vô hiệu hóa' : 'Khôi phục'}</div>
                    </c:if>
                    <c:if test="${username == u.username}">
                      <div class="btn btn-sm btn-success">
                        Bạn</div>
                    </c:if>
                  </div>
                </div>
                <!-- * Start Modal -->
                <div class="modal fade"
                     id="deleteModal${u.username}"
                     tabindex="-1"
                     role="dialog"
                     aria-labelledby="modelTitleId"
                     aria-hidden="true">
                  <div class="modal-dialog modal-dialog-centered"
                       role="document">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title ">${u.activated == true ? 'Vô hiệu hóa' : 'Khôi phục'} người dùng <span
                                class="text-danger">"${u.username}"</span>
                        </h5>
                        <button type="button"
                                class="close"
                                data-dismiss="modal"
                                aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        <form action="/admin/user/${u.activated == true ? 'delete' : 'restore'}/${u.username}"
                              method="post">
                          <div class="row bg rounded ">
                            <div class="col-12 d-flex justify-content-center">
                              <img src="${pageContext.request.contextPath}/img/user-management/confirm-delete.svg"
                                   alt=""
                                   width="75%">
                            </div>
                            <div class="col-12">
                              <div class="alert alert-warning">Bạn có chắc muốn ${u.activated == true ? 'vô hiệu hóa' :
                                'khôi phục'} người dùng
                                <strong class="text-danger font-weight-bold">${u.username}</strong> không ?
                              </div>
                            </div>
                            <div class="form-group col-lg-12 mx-auto mb-4 ">
                              <hr>
                              <button type="submit"
                                      class="btn btn-danger float-right w-50">
                                <span class="font-weight-bold">Có, ${u.activated == true ? 'Vô hiệu hóa' : 'Khôi
                                  phục'}</span>
                              </button>
                            </div>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- * End modal -->
                <span></span>
              </c:forEach>
            </c:when>
          </c:choose>
        </div>
      </div>
    </div>

    <!-- Js Plugins -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script>
      $('#deleteModal').on('show.bs.modal', event => {
        var button = $(event.relatedTarget);
        var modal = $(this);
        // Use above variables to manipulate the DOM
      });

    </script>
  </body>

</html>