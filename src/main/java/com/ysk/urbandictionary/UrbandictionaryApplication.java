package com.ysk.urbandictionary;

import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UrbandictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbandictionaryApplication.class, args);
	}

	/*@Bean  //springin kullandığı bir object olabilmesi için
	@Profile("dev") //sadece dev modundayken çalışsın deriz.
	CommandLineRunner createInitialUsers(UserService userService){
		return new CommandLineRunner(){
			@Override
			public void run(String... args) throws Exception {  //logini denemek için yazdık bu kodu uygulama başlarken veritabanına yazdığımız user i ekler.
				User user = new User();
				user.setUsername("username1");
				user.setPassword("Yasin.38");
				userService.save(user);
			}
		};
	}*/

}
