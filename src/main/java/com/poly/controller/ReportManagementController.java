package com.poly.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.poly.model.Category;
import com.poly.model.ReportByCategory;
import com.poly.model.ReportByProduct;
import com.poly.model.ReportByUser;
import com.poly.model.ReportTop10;
import com.poly.repository.CategoryDAO;
import com.poly.repository.OrderDAO;
import com.poly.repository.OrderDetailDAO;
import com.poly.service.ParamService;

@Controller
@RequestMapping("/admin")
@CrossOrigin("*")
public class ReportManagementController {
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    ParamService paramService;

    @GetMapping("/report/report-top-ten")
    @ResponseBody
    public List<ReportTop10> reportTop10View(Model model) {
        // model.addAttribute("reports", orderDetailDAO.getTopProduct());
        List<ReportTop10> list = orderDetailDAO.getTopProduct();
        List<ReportTop10> top10 = new ArrayList<>();
        int length = list.size();
        if (length < 10) {
            length = list.size();
        }
        for (int i = 0; i < length; i++) {
            top10.add(list.get(i));
            System.out.println(top10.get(i));
        }
        return top10;
    }

    @PostMapping("/report/report-top-ten")
    public String reportTop10Search(Model model) {
        String searchKey = paramService.getString("searchKey", "");
        List<ReportTop10> reports = null;
        if (!searchKey.isBlank()) {
            Date searchVal = null;
            LocalDate localDate = null;
            int day = 0;
            int month = 0;
            int year = 0;
            if (searchKey.equals("date")) {
                searchVal = paramService.getDate("searchVal", "yyyy-MM-dd");
                day = localDate.getDayOfMonth();
                month = localDate.getMonthValue();
                year = localDate.getYear();
                reports = orderDetailDAO.getTopProductByDate(day, month, year);
                model.addAttribute("searchVal", day + " tháng " + month + " năm " + year);
                model.addAttribute("searchKey", searchKey);
            } else if (searchKey.equals("week")) {
                String searchValString = paramService.getString("searchVal", "");
                String weekStr = searchValString.substring(6, searchValString.length());
                String yearStr = searchValString.substring(0, 4);
                reports = orderDetailDAO.getTopProductByWeek(Integer.parseInt(weekStr), Integer.parseInt(yearStr));
                model.addAttribute("searchVal", " thứ " + weekStr + " của  " + " năm " + yearStr);
                model.addAttribute("searchKey", searchKey);
            } else if (searchKey.equals("month")) {
                searchVal = paramService.getDate("searchVal", "yyyy-MM");
                month = localDate.getMonthValue();
                year = localDate.getYear();
                reports = orderDetailDAO.getTopProductByMonth(month, year);
                model.addAttribute("searchKey", searchKey);
                model.addAttribute("searchVal", month + " năm " + year);
            } else if (searchKey.equals("year")) {
                searchVal = paramService.getDate("searchVal", "yyyy");
                year = localDate.getYear();
                reports = orderDetailDAO.getTopProductByYear(year);
                model.addAttribute("searchVal", year);
                model.addAttribute("searchKey", "year");
            }
            model.addAttribute("reports", reports);
        } else {
            model.addAttribute("msg", "Vui lòng chọn tiêu chí thống kê");
            model.addAttribute("reports", orderDetailDAO.getTopProduct());

        }
        model.addAttribute("title", "BÁO CÁO - THỐNG KÊ");
        model.addAttribute("reportPage", "top");
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("isPageActive", "statistic");
        return "/admin/report-top-ten";
    }

    @GetMapping("/report/report-top-ten/chart")
    @ResponseBody
    public List<ReportTop10> getReportTop10(Model model) {
        return orderDetailDAO.getTopProduct();
    }

    @GetMapping("/report/report-by-category")
    @ResponseBody
    public List<ReportByCategory> reportByCategoryView(Model model) {
        return orderDetailDAO.getReportByCategories();
    }

    @GetMapping("/report/report-by-category/filter")
    @ResponseBody
    public List<ReportByCategory> reportByCategoryFilter(@RequestParam("startDate") String sDate,
            @RequestParam("endDate") String eDate) {

        Date startDate = paramService.getDate2(sDate, "yyyy-MM-dd");
        Date endDate = paramService.getDate2(eDate, "yyyy-MM-dd");
        return orderDetailDAO.getReportByCategoriesByDate(startDate, endDate);
    }

    @GetMapping("/report/report-by-product/filter")
    @ResponseBody
    public List<ReportByProduct> reportByProductFilter(@RequestParam("startDate") String sDate,
            @RequestParam("endDate") String eDate) {

        Date startDate = paramService.getDate2(sDate, "yyyy-MM-dd");
        Date endDate = paramService.getDate2(eDate, "yyyy-MM-dd");
        return orderDetailDAO.getReportByProductsByDate(startDate, endDate);
    }

    @GetMapping("/report/report-by-user/filter")
    @ResponseBody
    public List<ReportByUser> reportByUserFilter(@RequestParam("startDate") String sDate,
            @RequestParam("endDate") String eDate) {

        Date startDate = paramService.getDate2(sDate, "yyyy-MM-dd");
        Date endDate = paramService.getDate2(eDate, "yyyy-MM-dd");
        return orderDetailDAO.getReportByUsersByDate(startDate, endDate);
    }

    @GetMapping("/report/report-by-product")
    @ResponseBody
    public List<ReportByProduct> reportByProductView(Model model) {

        return orderDetailDAO.getReportByProducts();
    }

    @GetMapping("/report/report-by-user")
    @ResponseBody
    public List<ReportByUser> reportByUserView(Model model) {

        return orderDetailDAO.getReportByUsers();
    }

    @GetMapping("report")
    public String getReportView(Model model) {
        // Date date =
        model.addAttribute("reportByMonth", orderDAO.getTurnoverByMonth(new Date()));
        model.addAttribute("reportByYear", orderDAO.getTurnoverByYear(new Date()));
        model.addAttribute("reportByDate", orderDAO.getTurnoverByDay(new Date()));
        model.addAttribute("reportPage", "total");
        model.addAttribute("isPageActive", "statistic");
        model.addAttribute("title", "BÁO CÁO - THỐNG KÊ");
        return "/admin/report";
    }
}
