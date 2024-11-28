package com.aditya.electronic.store;

import com.aditya.electronic.store.entities.Role;
import com.aditya.electronic.store.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class EStoreApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(EStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
		if(adminRole == null)
		{
			adminRole = new Role();
			adminRole.setRoleId(UUID.randomUUID().toString());
			adminRole.setName("ROLE_ADMIN");
			roleRepository.save(adminRole);
		}

		Role normalRole = roleRepository.findByName("ROLE_NORMAL").orElse(null);
		if(normalRole == null)
		{
			normalRole = new Role();
			normalRole.setRoleId(UUID.randomUUID().toString());
			normalRole.setName("ROLE_NORMAL");
			roleRepository.save(normalRole);
		}
	}
}
