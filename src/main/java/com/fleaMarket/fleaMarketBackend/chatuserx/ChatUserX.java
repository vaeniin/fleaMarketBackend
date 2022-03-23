package com.fleaMarket.fleaMarketBackend.chatuserx;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Getter;

@Getter
@Document(collection = "chatuserxes")
public class ChatUserX {

	@Id
	private String id;
	
	@Field(targetType = FieldType.OBJECT_ID)
	private String chatId;
	
	@Field(targetType = FieldType.OBJECT_ID)
	private String userId;
	
	public ChatUserX(String chatId, String userId) {
		this.chatId = chatId;
		this.userId = userId;
	}
	
}
