package com.fleaMarket.fleaMarketBackend.item;

import java.util.Date;

import com.fleaMarket.fleaMarketBackend.utils.Image;

import lombok.Data;

@Data
public class ItemShort {
	
	private String id;
	private String userId;
	private String heading;
	private String price;
	private Image[] images;
	
	private Date date;
}
