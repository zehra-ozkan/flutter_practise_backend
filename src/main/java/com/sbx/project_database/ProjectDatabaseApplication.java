package com.sbx.project_database;//package com.sbx.project_database;

import com.sbx.project_database.persistence.User;
import com.sbx.project_database.persistence.DepartmentRepository;
import com.sbx.project_database.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDateTime;

@SpringBootApplication
public class ProjectDatabaseApplication {
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectDatabaseApplication.class, args);

	}
	@Bean
	public CommandLineRunner demo(UserRepository departmentRepository) {
		return (args) -> {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			// Add sample departments
			User dept1 = new User();
			//dept1.setUser_name("LocalDateTime.now().toString()");
			dept1.setUserName("dddgit");
			dept1.setUser_password("kitty");
			dept1.setUser_password(passwordEncoder.encode(dept1.getUser_password()));
			dept1.setBirthday(java.sql.Date.valueOf("2024-06-15"));
			departmentRepository.save(dept1);


			System.out.println("Sample departments added to database");
		};
	}
}
