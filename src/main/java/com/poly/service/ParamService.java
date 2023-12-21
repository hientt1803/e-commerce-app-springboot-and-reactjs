package com.poly.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ParamService {
	@Autowired
	HttpServletRequest request;

	@Autowired
	ServletContext app;

	public String getString(String name, String defaultValue) {
		return request.getParameter(name) != null ? request.getParameter(name) : defaultValue;
	}

	public int getInt(String name, int defaultValue) {
		return request.getParameter(name) != null ? Integer.parseInt(request.getParameter(name)) : defaultValue;
	}

	public double getDouble(String name, double defaultValue) {
		return request.getParameter(name) != null ? Double.parseDouble(request.getParameter(name)) : defaultValue;
	}

	public Boolean getBoolean(String name, Boolean defaultValue) {
		if (request.getParameter(name) != null && (request.getParameter(name).equals("on")))
			defaultValue = !defaultValue;
		return defaultValue;
	}

	public Date getDate(String name, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = formatter.parse(request.getParameter(name));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Pattern is invalid !!");
		}
		return date;
	}

	public Date getDate2(String name, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = formatter.parse(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Pattern is invalid !!");
		}
		return date;
	}

	public File save(MultipartFile file, String path) {
		File newFile = null;
		File dir = new File(request.getServletContext().getRealPath(path));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			newFile = new File(app.getRealPath(path + "/" + fileName));
			try {
				file.transferTo(newFile);
			} catch (IOException | IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newFile;
	}
}
