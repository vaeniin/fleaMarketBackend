package com.fleaMarket.fleaMarketBackend.message;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {

	List<Message> findAllByChatId(String chatId, Sort sort);
	
}
