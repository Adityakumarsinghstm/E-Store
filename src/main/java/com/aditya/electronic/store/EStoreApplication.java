package com.aditya.electronic.store;

import com.aditya.electronic.store.config.AppConstants;
import com.aditya.electronic.store.entities.Role;
import com.aditya.electronic.store.entities.User;
import com.aditya.electronic.store.repositories.RoleRepository;
import com.aditya.electronic.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableWebMvc
public class EStoreApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(EStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = roleRepository.findByName("ROLE_"+AppConstants.ROLE_ADMIN).orElse(null);
		if(adminRole == null)
		{
			adminRole = new Role();
			adminRole.setRoleId(UUID.randomUUID().toString());
			adminRole.setName("ROLE_"+AppConstants.ROLE_ADMIN);
			roleRepository.save(adminRole);
		}

		Role normalRole = roleRepository.findByName("ROLE_"+AppConstants.ROLE_NORMAL).orElse(null);
		if(normalRole == null)
		{
			normalRole = new Role();
			normalRole.setRoleId(UUID.randomUUID().toString());
			normalRole.setName("ROLE_"+ AppConstants.ROLE_NORMAL);
			roleRepository.save(normalRole);
		}

		 User user = userRepository.findByEmail("aditya@gmail.com").orElse(null);
		if(user == null)
		{
			user = new User();
			user.setUserId(UUID.randomUUID().toString());
			user.setName("aditya");
			user.setEmail("aditya@gmail.com");
			user.setPassword(passwordEncoder.encode("aditya"));
			user.setAbout("Backend Engineer");
			user.setGender("Male");
			user.setRoles(List.of(adminRole));
			userRepository.save(user);
		}
	}
}
