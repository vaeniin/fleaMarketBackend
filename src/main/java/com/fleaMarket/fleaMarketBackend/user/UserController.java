package com.fleaMarket.fleaMarketBackend.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fleaMarket.fleaMarketBackend.email.EmailService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository repository;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok().body(repository.findAll());
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody User user) {
		if (user.getTheme() == null) {
			 Optional<User> u = repository.findById(user.getId());
			 if (u.isPresent()) {
				 user.setTheme(u.get().getTheme());
			 }
		} else {
			Optional<User> u = repository.findById(user.getId());
			if (u.isPresent()) {
				user.setName(u.get().getName());
				user.setEmail(u.get().getEmail());
				if (u.get().getCountry() != null) user.setCountry(u.get().getCountry());
				if (u.get().getRegion() != null) user.setRegion(u.get().getRegion());
				if (u.get().getCity() != null) user.setCity(u.get().getCity());
				if (u.get().getPostalcode() != null) user.setPostalcode(u.get().getPostalcode());
			}
		}
		
		repository.save(user);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/get/{email}")
	public ResponseEntity<Optional<User>> get(@PathVariable String email) {
		return ResponseEntity.ok().body(repository.findByEmail(email));
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> login(
			@RequestParam String email,
			@RequestParam String otp,
			@RequestParam String name,
			@RequestParam Boolean register
		) {
		
		Optional<User> user = repository.findByEmail(email);
		if (user.isPresent() && !register) {
			emailService.send(email, otp);
			//System.out.println(otp);
			
		} else if (user.isEmpty() && register) {
			User newUser = new User(email, name);
			repository.save(newUser);
			
			emailService.send(email, otp);
		}
		
		return ResponseEntity.ok().build();
	}
}
