package com.fleaMarket.fleaMarketBackend.message;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.fleaMarket.fleaMarketBackend.utils.Image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "messages")
public class Message {

	@Id
	private String id;
	
	@Field(targetType = FieldType.OBJECT_ID)
	private String senderId;
	
	@Field(targetType = FieldType.OBJECT_ID)
	private String chatId;
	
	private String content;
	private Image image;
	private Date date;
}
