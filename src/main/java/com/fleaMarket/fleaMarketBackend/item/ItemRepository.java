package com.fleaMarket.fleaMarketBackend.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends MongoRepository<Item, String> {
	
	@Aggregation(pipeline = {
		"{$match: { id: ObjectId(?0) }}",
		"{$lookup: {from: users, localField: userId, foreignField: _id, as: user }}",
		"{$unwind: $user}",
		"{$project: {id: 1, userId: 1, heading: 1, location: 1, price: 1, oldprice: 1, description: 1, type: 1, condition: 1, images: 1, categories: 1, tags: 1, methods: 1, date: 1, user: '$user.name' } }"
	})
	Optional<ItemResult> findItemById(String id);
	
	@Query("{ $and: ["
			+ "{$or: [ { $expr: { $eq: [:#{#search.user}, null] } }, { userId: :#{#search.user} } ]},"
			+ "{$or: [ { $expr: { $eq: [:#{#search.category}, null] } }, { categories: :#{#search.category} } ]},"
			+ "{$or: [ { heading: { $regex: :#{#search.text}, $options: i } }, { tags: { $regex: :#{#search.text}, $options: i } } ]},"
			+ "{$or: [ { $expr: { $eq: [:#{#search.type}, null] } }, { type: :#{#search.type} } ]},"
			+ "{$or: [ { 'location.country': { $regex: :#{#search.location}, $options: i } }, { 'location.region': { $regex: :#{#search.location}, $options: i } }, { 'location.city': { $regex: :#{#search.location}, $options: i } }, { 'location.postalcode': { $regex: :#{#search.location}, $options: i } } ]}"
			+ "] }")
	List<ItemShort> findItems(@Param("search") SearchParams params, Pageable pageable);

}
