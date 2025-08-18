package com.sbx.project_database.resource;

import com.sbx.project_database.persistence.User;
import com.sbx.project_database.persistence.UserRepository;
import com.sbx.project_database.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/app_users")
@RestController
public class UserResource {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //managing resting points
    public UserResource(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public record LoginRequest(String username, String password) {}
    public record RegisterRequest(String username, String password, Date date) {}


    //this works
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginRequest request,
            HttpSession session) { // Add this parameter

        if (userService.validateLogin(request.username(), request.password())) {
            User user = userRepository.findByUserName(request.username()).get();//I am assumning not nul coming from validate login

            session.setAttribute("userId", user.getUser_id()); // Store username instead of ID
            return ResponseEntity.ok(Map.of(
                    "success", true, //this is the boolean key
                    "userId", user.getUser_id(), // Sending user ID to Flutter

                    "sessionId", session.getId(),
                    "message", "Login successful"
            ));
        }
        return ResponseEntity.status(401)
                .body(Map.of("error", "Invalid credentials"));
    }

    @PostMapping(value="/register")
    public ResponseEntity<Map<String, Object>> add(
            @RequestBody RegisterRequest request) { // Add this parameter

        User user = new User(); //todo I need to check dublicates names
        user.setUserName(request.username);
        user.setUser_password(request.password);
        user.setBirthday(request.date);
        try  {
           User u = userService.addUser(user);
            return ResponseEntity.ok(Map.of(
                    "success" , true,
                     "userId", user.getUser_id(),
                     "message", "Register successful"
            ));
        }catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(Map.of("success" , false,
                            "userId", -1,
                            "message", "Register UNsuccessful"));
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(
            @RequestHeader("Session-ID") String sessionId, HttpSession session
    ){
        if(!session.getId().equals(sessionId)){
            return ResponseEntity.status(401).build();
        }
        int userId = (int) session.getAttribute("userId");

        User user = this.userService.getById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public List<User> getAll(){
        return this.userService.getAll();
    }

    @GetMapping(value="/id/{id}")
    public User getById(@PathVariable int id){
        return this.userService.getById(id);
    }
    @GetMapping(value="/name/{name}")
    public User getByName(@PathVariable String name){
        return this.userService.getByName(name);
    }


    @PutMapping(value="/{id}" , consumes = "application/json")
    public User update(@PathVariable int id,@RequestBody User user){
        return this.userService.update(id, user);
    }

    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable int id){
        this.userService.delete(id);
    }

}
