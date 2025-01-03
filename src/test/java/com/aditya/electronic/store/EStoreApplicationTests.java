package com.aditya.electronic.store;

import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.repositories.UserRepository;
import com.aditya.electronic.store.security.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EStoreApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtHelper helper;

	@Test
	void contextLoads() {
	}

	@Test
	void testToken()
	{
		 User userDetails = userRepository.findByEmail("aditya@gmail.com").get();

		 String token = helper.generateToken(userDetails);
		System.out.println("The token is ["+token+"]");
		System.out.println("User name getting from token "+helper.getUserFromToken(token));
		System.out.println("Is token Expired : "+helper.isTokenExpired(token));
	}
}
