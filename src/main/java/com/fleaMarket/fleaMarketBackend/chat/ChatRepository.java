package com.fleaMarket.fleaMarketBackend.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> 	{

}
