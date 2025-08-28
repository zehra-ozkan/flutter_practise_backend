package com.sbx.project_database.controller;

import com.sbx.project_database.models.Post;
import com.sbx.project_database.models.User;
import com.sbx.project_database.service.PostService;
import com.sbx.project_database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@RequestMapping("/posts")
@RestController
public class PostController {

    @Autowired
    private UserService userService;

     @Autowired
    private PostService postService;


     @GetMapping("/my_posts")
    public ResponseEntity<Map<String, Object>> getMyPosts(@RequestHeader("Authorization") String token) {
         User user = userService.getUserByToken(token.substring(7));

         System.out.println("Requesting posts of " + user.getUserName());
         System.out.println("");
         try {
             Set<Post> posts = postService.getUserPosts(user);
             //ArrayList<Post> posts = postService.getPostsById(user.get)
             return ResponseEntity.ok()
                     .body(Map.of(
                             "posts", posts,
                             "success", true,
                             "count", posts.size())
                     );
         }catch (Exception e) {
             System.out.println(e);
             return ResponseEntity.status(401)
                     .body(Map.of("message", "olmadı", "success", false));
         }



     }

}
