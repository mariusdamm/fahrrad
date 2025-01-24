package org.mariusdamm.fahrrad;

import org.mariusdamm.fahrrad.dao.RoleRepository;
import org.mariusdamm.fahrrad.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository) {
		return args -> {
			if (!roleRepository.findAll().isEmpty()) {
				return;
			}

			roleRepository.save(new Role(0, "ADMIN", new ArrayList<>()));
			roleRepository.save(new Role(0, "USER", new ArrayList<>()));
		};

	}
}
