package com.poly.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.poly.service.UploadService;

import jakarta.servlet.ServletContext;

@Service
public class UploadServiceImpl implements UploadService {
	@Autowired
	ServletContext app;

	@Override
	public File save(MultipartFile file, String folder) {
		File dir = new File("src/main/resources/static/img/" + folder).getAbsoluteFile();
		if (dir.exists()) {
			dir.mkdirs();
		}
		String s = file.getOriginalFilename();
		String name = Integer.toHexString(s.hashCode()) + s.substring(s.lastIndexOf("."));
		System.out.println(s);
		try {
			File saveFile = new File(dir, s);
			if (!saveFile.exists()) {
				file.transferTo(saveFile);
				System.out.println(saveFile);
				return saveFile;
			}else {
				 System.out.println("File already exists: " + saveFile);
				return saveFile;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
