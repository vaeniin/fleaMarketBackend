package com.fleaMarket.fleaMarketBackend.chatuserx;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleaMarket.fleaMarketBackend.chat.Chat;
import com.fleaMarket.fleaMarketBackend.chat.ChatRepository;
import com.fleaMarket.fleaMarketBackend.message.Message;
import com.fleaMarket.fleaMarketBackend.message.MessageRepository;

import lombok.Getter;
import lombok.Setter;
	
@RestController
@RequestMapping("/chat")
public class ChatUserXController {
	
	@Autowired
	ChatUserXRepository xRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	ChatRepository chatRepository;
	
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<List<UserChatResult>> getAll(@PathVariable String userId) {
		return ResponseEntity.ok().body(xRepository.findChatsByUserId(userId).getMappedResults());
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> add(AddRequest request) {

		if (request.getSenderId().equals(request.getGetterId())) return null;
		
		List<String> senderChats = xRepository.findByUserId(new ObjectId(request.getSenderId()));
		List<String> getterChats = xRepository.findByUserId(new ObjectId(request.getGetterId()));
		senderChats.retainAll(getterChats);
		
		Message message = new Message();
		message.setContent(request.getContent());
		message.setSenderId(request.getSenderId());
		message.setDate(new Date());

		if (senderChats.size() > 0) {
			message.setChatId(senderChats.get(0).substring(21, 45));
		} else {
			Chat chat = new Chat(request.getItemId());
			chatRepository.save(chat);
			
			ChatUserX x1 = new ChatUserX(chat.getId(), request.getSenderId());
			ChatUserX x2 = new ChatUserX(chat.getId(), request.getGetterId());
			
			xRepository.saveAll(Arrays.asList(x1, x2));
			
			message.setChatId(chat.getId());
		}
		
		messageRepository.save(message);

		return ResponseEntity.ok().build();
	}
	
	@Getter
	@Setter
	private class AddRequest {
		
		private String senderId;
		private String getterId;
		private String itemId;
		private String content;
	}
	
}
