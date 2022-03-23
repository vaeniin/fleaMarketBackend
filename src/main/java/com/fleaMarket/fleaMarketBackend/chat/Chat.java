package com.fleaMarket.fleaMarketBackend.chat;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "chats")
public class Chat {

	@Id
	private String id;
	
	@Field(targetType = FieldType.OBJECT_ID)
	private String itemId;
	
	private Date lastOpened;
	
	public Chat(String itemId) {
		this.itemId = itemId;
	}
}
