<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

      <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible"
                  content="IE=edge">
            <meta name="viewport"
                  content="width=device-width, initial-scale=1.0">
            <!-- Google Font -->
            <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
                  rel="stylesheet" />
            <!-- Css Styles -->
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/bootstrap.min.css"
                  type="text/css" />
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/font-awesome.min.css"
                  type="text/css" />
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/elegant-icons.css"
                  type="text/css" />
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/magnific-popup.css"
                  type="text/css" />
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/nice-select.css"
                  type="text/css" />
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/owl.carousel.min.css"
                  type="text/css" />
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/slicknav.min.css"
                  type="text/css" />
            <link rel="stylesheet"
                  href="${pageContext.request.contextPath}/css/style.css"
                  type="text/css" />
            <title>3MEMS | Sign in </title>
            <style>
                  body {
                        position: relative;
                  }

                  .img-fluid {
                        width: 100%;
                        height: 100%;
                        overflow: hidden;
                  }

                  .container {
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        transform: translate(-50%, -50%);
                  }

                  .row .col-6 {
                        background-color: rgba(255, 255, 255, 0.266);
                  }
            </style>
      </head>

      <body class="">
            <img src="${pageContext.request.contextPath}/img/signup/bg-signup.jpg"
                 alt=""
                 class="img-fluid">
            <div class="container">
                  <div class="row py-5  align-items-center">
                        <div class="col-8 offset-2 shadow rounded ">
                              <form action="/account/verify"
                                    method="post">
                                    <div class="${msg !=  null? 'alert' : ''} alert-danger text-center mt-3">${msg}
                                    </div>
                                    <h2 class="text-center pt-5 mb-5 font-weight-bold">Xác nhận tài khoản email</h2>
                                    <small class="text-center d-block my-2 font-weight-bold ">Vui lòng kiểm tra email
                                          <strong><%= session.getAttribute("signupMail") %></strong> để
                                          nhập mã mã xác nhận
                                          vào ô bên dưới</small>
                                    <div class="row  bg rounded ">
                                          <div class="input-group col-lg-6 offset-3 my-4">
                                                <div class="input-group-prepend">
                                                      <span
                                                            class="input-group-text bg-white px-4 border-md border-right-0">
                                                            <i class="fa fa-code"
                                                               aria-hidden="true"></i>
                                                      </span>
                                                </div>
                                                <input id="username"
                                                       type="text"
                                                       name="code"
                                                       placeholder="Mã xác nhận"
                                                       class="form-control bg-white border-left-0 border-md">
                                          </div>
                                          <!-- Submit Button -->
                                          <div class="form-group col-lg-6 offset-3 mx-auto mb-5">
                                                <button type="submit"
                                                        class="btn btn-primary btn-block py-2">
                                                      <span class="font-weight-bold">Tạo tài khoản</span>
                                                </button>
                                          </div>
                                    </div>
                              </form>
                        </div>
                  </div>
            </div>
            <!-- Js Plugins -->
            <script src="js/jquery-3.3.1.min.js"></script>
            <script src="js/bootstrap.min.js"></script>
            <script src="js/jquery.nice-select.min.js"></script>
            <script src="js/jquery.nicescroll.min.js"></script>
            <script src="js/jquery.magnific-popup.min.js"></script>
            <script src="js/jquery.countdown.min.js"></script>
            <script src="js/jquery.slicknav.js"></script>
            <script src="js/mixitup.min.js"></script>
            <script src="js/owl.carousel.min.js"></script>
      </body>

</html>