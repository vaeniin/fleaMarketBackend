package com.fleaMarket.fleaMarketBackend.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	
	private final static String imgPath = "src/main/resources/static/images/";
	
	public static void saveFile(String uploadDir, String fileName, MultipartFile file) {
		Path uploadPath = Paths.get(imgPath + uploadDir);
		
		if (!Files.exists(uploadPath)) {
			try {
				Files.createDirectories(uploadPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			InputStream input = file.getInputStream();
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteFile(String uploadDir, String fileName) {
		Path deletePath = Paths.get(imgPath + uploadDir + "/" + fileName);
		if (Files.exists(deletePath)) {
			try {
				Files.delete(deletePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
