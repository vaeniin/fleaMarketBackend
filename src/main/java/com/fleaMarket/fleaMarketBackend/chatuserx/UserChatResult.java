package com.fleaMarket.fleaMarketBackend.chatuserx;

import java.util.Date;

import com.fleaMarket.fleaMarketBackend.message.Message;

import lombok.Data;

@Data
public class UserChatResult {
	
	private String id;
	private String itemId;
	private Message message;
	private Date lastOpened;
	
	private String name;
	private String heading;

}
