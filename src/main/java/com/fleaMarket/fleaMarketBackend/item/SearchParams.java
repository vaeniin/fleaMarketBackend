package com.fleaMarket.fleaMarketBackend.item;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchParams {
	
	private ObjectId user;
	private String category;
	private String text;
	private String type;
	private String location;
	private int page;
}
