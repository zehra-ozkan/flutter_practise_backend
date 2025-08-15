package com.sbx.project_database.resource;

import com.sbx.project_database.persistence.User;
import com.sbx.project_database.persistence.UserRepository;
import com.sbx.project_database.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/app_users")
@RestController
public class UserResource {
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public record LoginRequest(String username, String password) {}

    //managing resting points
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/app_users/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest request,
            HttpSession session) { // Add this parameter


        if (userService.validateLogin(request.username(), request.password())) {
            User user = userRepository.findByUserName(request.username()).get();//I am assumning not nul coming from validate login

            session.setAttribute("userId", user.getUser_id()); // Store username instead of ID
            return ResponseEntity.ok("Login success");
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping(value="/app_users/register")
    public ResponseEntity<User> add(@RequestBody User user){ //to remember loggen-in state
        User s = this.userService.add(user);
        return ResponseEntity.ok(s);
    }


    @GetMapping(value="/app_users")
    public List<User> getAll(){
        return this.userService.getAll();
    }

    @GetMapping(value="/app_users/id/{id}")
    public User getById(@PathVariable int id){
        return this.userService.getById(id);
    }
    @GetMapping(value="/app_users/name/{name}")
    public User getByName(@PathVariable String name){
        return this.userService.getByName(name);
    }


    @PutMapping(value="/app_users/{id}" , consumes = "application/json")
    public User update(@PathVariable int id,@RequestBody User user){
        return this.userService.update(id, user);
    }

    @DeleteMapping(value="/app_users/{id}")
    public void delete(@PathVariable int id){
        this.userService.delete(id);
    }

}
