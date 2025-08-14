package com.sbx.project_database;//package com.sbx.project_database;

import com.sbx.project_database.persistence.Department;
import com.sbx.project_database.persistence.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectDatabaseApplication {
	@Autowired
	private DepartmentRepository departmentRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectDatabaseApplication.class, args);

	}
	@Bean
	public CommandLineRunner demo(DepartmentRepository departmentRepository) {
		return (args) -> {
			// Add sample departments
			Department dept1 = new Department();
			dept1.setName("Human Resources");
			departmentRepository.save(dept1);

			Department dept2 = new Department();
			dept2.setName("Information Technology");
			departmentRepository.save(dept2);

			Department dept3 = new Department();
			dept3.setName("Finance");
			departmentRepository.save(dept3);

			Department dept4 = new Department();
			dept4.setName("Marketing");
			departmentRepository.save(dept4);

			Department dept5 = new Department();
			dept5.setName("Operations");
			departmentRepository.save(dept5);

			System.out.println("Sample departments added to database");
		};
	}
}
