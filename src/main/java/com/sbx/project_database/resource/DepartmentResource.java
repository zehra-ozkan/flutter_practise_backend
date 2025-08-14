package com.sbx.project_database.resource;

import com.sbx.project_database.persistence.Department;
import com.sbx.project_database.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentResource {
    DepartmentService departmentService;


    //these are called resting points? I do not know how this thing works I do not know how this thing works
    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/departments")
    public List<Department> getAll() {
        return this.departmentService.getAll();
    }

    @GetMapping(value = "/departments/{id}") //is the parameter
    public Department getById(@PathVariable Long id) { //this tells where it comes from??
        return this.departmentService.getById(id);
    }

    @PostMapping(value = "/departments")
    public Department add(Department department) {
        return this.departmentService.add(department);
    }

    @PutMapping(value = "/departments/{id}" , consumes = "application/json")
    public Department update(@PathVariable Long id, @RequestBody Department department) {
        return this.departmentService.update(id, department);
    }

    @DeleteMapping(value = "/departments/{id}")
    public void delete(@PathVariable Long id) {
        this.departmentService.delete(id);
    }
}
