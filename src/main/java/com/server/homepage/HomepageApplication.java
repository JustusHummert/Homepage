package com.server.homepage;

import com.server.homepage.entities.Admin;
import com.server.homepage.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		SecurityAutoConfiguration.class
})
public class HomepageApplication {
	@Autowired
	private AdminRepository adminRepository;

	public static void main(String[] args) {
		SpringApplication.run(HomepageApplication.class, args);
	}

	//Set the admin password
	@Bean
	public CommandLineRunner setAdminPassword(){
		return args -> {
			if(args.length>0 && !args[0].isEmpty()){
				String hashedPassword = BCrypt.hashpw(args[0], BCrypt.gensalt());
				adminRepository.save(new Admin(hashedPassword));
			}
		};
	}

}
