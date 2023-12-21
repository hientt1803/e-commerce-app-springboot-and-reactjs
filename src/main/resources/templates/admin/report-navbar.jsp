<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<a href="/admin/report"
   class="btn btn${reportPage == 'total' ? '': '-outline'}-info">Tổng doanh thu</a>
<a href="/admin/report/report-top-ten"
   class="btn btn${reportPage == 'top' ? '': '-outline'}-primary">Top 10 sản phẩm bán chạy</a>
<a href="/admin/report/report-by-category"
   class="btn btn${reportPage == 'category' ? '': '-outline'}-warning">Doanh thu theo loại</a>
<a href="/admin/report/report-by-product"
   class="btn btn${reportPage == 'product' ? '': '-outline'}-secondary">Doanh thu theo sản phẩm</a>
<a href="/admin/report/report-by-user"
   class="btn btn${reportPage == 'user' ? '': '-outline'}-info">Người dùng</a>