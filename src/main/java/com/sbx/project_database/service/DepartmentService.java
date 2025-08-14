package com.sbx.project_database.service;

import com.sbx.project_database.persistence.Department;
import com.sbx.project_database.persistence.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    DepartmentRepository repo;

    public DepartmentService(DepartmentRepository repo) {
        this.repo = repo;

    }
    public List<Department> getAll() {
        return this.repo.findAll(); //select * from department
    }

    public Department getById(Long id) {
        return this.repo.findById(id).get(); //.get protects from null pointer exception
    }

    public Department add(Department department) {
        return this.repo.save(department); //insert into department where ...
    }

    public Department update(Long id, Department department) {
        Optional<Department> depo = this.repo.findById(id);
        if(depo.isPresent()) {
            depo.get().setName(department.getName()); //lombok does the getter and setter
            return this.repo.save(depo.get());

        }
        throw new RuntimeException("Department not found");
    }
    public void delete(Long id) {
        this.repo.deleteById(id);
    }
}
