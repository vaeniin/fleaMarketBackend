package com.fleaMarket.fleaMarketBackend.chatuserx;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ChatUserXRepository extends MongoRepository<ChatUserX, String> {

	@Aggregation(pipeline = {
		"{$match: { userId: ObjectId(?0) }}",
		"{$lookup: { from: chats, localField: chatId, foreignField: _id, as: chat }}",
		"{$unwind: $chat}",
		"{$lookup: { from: items, localField: 'chat.itemId', foreignField: _id, as: item }}",
		"{$unwind: $item}",
		"{$lookup: { from: messages, let: { ch: $chatId }, pipeline: [{ $match: { $expr: { $eq: [$chatId, $$ch ] } } }, { $sort: { date: -1 }}, { $limit: 1 }] as: message }}",
		"{$unwind: $message}",
		"{$lookup: {"
		+ "from: chatuserxes,"
		+ "let: { ch: $chatId, us: $userId },"
		+ "pipeline: ["
		+ "{ $match: { $expr: { $eq: [$chatId, $$ch ] } } },"
		+ "{ $match: { $expr: { $ne: [$userId, $$us ] } } }"
		+ "], as: user }}",
		"{$unwind: $user}",
		"{$lookup: { from: users, localField: 'user.userId', foreignField: _id, as: receiver }}",
		"{$unwind: $receiver}",
		"{$project: { id: $chatId, itemId: '$item._id', lastOpened: '$chat.lastOpened', heading: '$item.heading', message: 1, name: '$receiver.name' }}"
	})
	AggregationResults<UserChatResult> findChatsByUserId(String userId);
	
	@Query(value="{ userId: ?0 }", fields="{ chatId: 1, _id:0 }")
	List<String> findByUserId(ObjectId userId);
}
