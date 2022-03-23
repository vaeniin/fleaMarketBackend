package com.fleaMarket.fleaMarketBackend.utils;

import org.springframework.web.multipart.MultipartFile;

public class SetImagesLength {
	
	public static int setLength(String[] images, MultipartFile[] files) {
		int iLength = 0;
		int fLength = 0;
		if (images != null) iLength = images.length;
		if (files != null) fLength = files.length;
		return iLength + fLength;
	}
}
