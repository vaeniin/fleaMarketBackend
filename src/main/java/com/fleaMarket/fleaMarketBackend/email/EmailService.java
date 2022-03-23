package com.fleaMarket.fleaMarketBackend.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Autowired
	private JavaMailSender emailSender;
	
	public void send(String to, String content) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject("Passcode");
		message.setText(content);
		emailSender.send(message);
	}
	
}
