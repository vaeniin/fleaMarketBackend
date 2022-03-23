package com.fleaMarket.fleaMarketBackend.message;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fleaMarket.fleaMarketBackend.chat.Chat;
import com.fleaMarket.fleaMarketBackend.chat.ChatRepository;
import com.fleaMarket.fleaMarketBackend.utils.FileUpload;
import com.fleaMarket.fleaMarketBackend.utils.Image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@RestController
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageRepository repository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@GetMapping("/getAll/{chatId}")
	public ResponseEntity<List<Message>> getAll(@PathVariable String chatId) {
		Optional<Chat> chat = chatRepository.findById(chatId);
		if (chat.isPresent()) {
			chat.get().setLastOpened(new Date());
			chatRepository.save(chat.get());
		}
		
		return ResponseEntity.ok().body(repository.findAllByChatId(chatId, Sort.by(Sort.Direction.DESC, "date")));
	}
	
	@PostMapping("/add")
	public ResponseEntity<MsgResponse> add(@RequestParam(name="file", required=false) MultipartFile file, Image img, Message msg) throws IOException {
		if (file != null) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			FileUpload.saveFile(msg.getSenderId(), fileName, file);
		}

		if (img.getName() != null) msg.setImage(img);
		msg.setDate(new Date());
		repository.save(msg);
		
		return ResponseEntity.ok().body(new MsgResponse(msg.getId(), msg.getDate()));
	}
	
	@AllArgsConstructor
	@Getter
	private class MsgResponse {
		private String id;
		private Date date;
	}
}
