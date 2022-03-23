package com.fleaMarket.fleaMarketBackend.user;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "users")
public class User {

	@Id
	private String id;

	@NotNull
	private String name;
	
	@NotNull
	@Indexed(unique=true)
	private String email;
	
	private String postalcode;
	private String city;
	private String region;
	private String country;
	
	private String theme;
	
	public User(String email, String name) {
		this.email = email;
		this.name = name;
	}

}
