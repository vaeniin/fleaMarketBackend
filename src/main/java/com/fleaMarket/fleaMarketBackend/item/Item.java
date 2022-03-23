package com.fleaMarket.fleaMarketBackend.item;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fleaMarket.fleaMarketBackend.utils.Image;
import com.fleaMarket.fleaMarketBackend.utils.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "items")
public class Item {
	
	@Id
	private String id;
	
	@NotNull(message = "User id must not be null")
	@Field(targetType = FieldType.OBJECT_ID)
	private String userId;
	
	private String heading;
	private Location location;
	private String type;
	private String[] categories;
	
	private String price;
	private String oldprice;
	private String description;
	private String condition;
	private Image[] images;
	private String[] tags;
	private String[] methods;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date date;
}
