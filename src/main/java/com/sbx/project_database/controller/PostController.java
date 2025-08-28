package com.sbx.project_database.controller;

import com.sbx.project_database.models.Post;
import com.sbx.project_database.models.User;
import com.sbx.project_database.service.PostService;
import com.sbx.project_database.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
             Set<Map<String, Object>> posts = postService.getUserPosts(user);
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

     @PostMapping(value="/new_post")
    public ResponseEntity<Map<String, Object>> addNewPost(@RequestHeader("Authorization") String token,
                                                          @RequestParam("postText") String content,
                                                          @RequestParam("file") MultipartFile photoFile) throws IOException {
         User user = userService.getUserByToken(token.substring(7));
         System.out.println("Requesting new posts of " + user.getUserName());

         Post post = new Post();
         post.setPostPhoto(photoFile.getBytes());
         post.setPostText(content);
         post.setPostUser(user);
         Date utilDate = new Date();
         post.setPostDate(new java.sql.Date(utilDate.getTime()));

         System.out.println("");
         try {
             postService.addPost(post);
             System.out.println("\nyeeyy\n");
             return ResponseEntity.ok()
                     .body(Map.of(
                             "posts_text", post.getPostText(),
                             "success", true
                     ));
         }catch (Exception e) {
             System.out.println("\nUnsuccessfulll\n");

             System.out.println(e);
             return ResponseEntity.status(401)
                     .body(Map.of("message", "olmadı", "success", false));
         }
     }

}

