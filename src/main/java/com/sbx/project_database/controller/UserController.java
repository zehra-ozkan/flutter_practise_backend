package com.sbx.project_database.controller;

import com.sbx.project_database.models.User;
import com.sbx.project_database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RequestMapping("/app_users")
@RestController
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    private List<User> kk = new ArrayList<User>();

    @GetMapping("/students")
    public List<User> getUsers(){
        kk.add(new User("sadf"));
        kk.add(new User("saasdfdf"));
        kk.add(new User("sadddddddf"));

        return kk;
    }

    //this works
    @PostMapping("/login")
    public String login(@RequestBody User user){
        System.out.println(user);
        return userService.validateLogin(user);
    }

    @PostMapping(value="/register")
    public User register(
            @RequestBody User user) { // Add this parameter
        return userService.addUser(user);
    }

//    @GetMapping()
//    public List<User> getAll(){
//        return this.userService.getAll();
//    }
//
//    @GetMapping(value="/id/{id}")
//    public User getById(@PathVariable int id){
//        return this.userService.getById(id);
//    }
//    @GetMapping(value="/name/{name}")
//    public User getByName(@PathVariable String name){
//        return this.userService.getByName(name);
//    }
//
//
//    @PutMapping(value="/{id}" , consumes = "application/json")
//    public User update(@PathVariable int id,@RequestBody User user){
//        return this.userService.update(id, user);
//    }
//
//    @DeleteMapping(value="/{id}")
//    public void delete(@PathVariable int id){
//        this.userService.delete(id);
//    }

}
