package com.sbx.project_database.service;


import com.sbx.project_database.models.Post;
import com.sbx.project_database.models.User;
import com.sbx.project_database.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public Set<Map<String, Object>> getUserPosts(User user) {//todo custom json for post!!
        try {
            Set<Post> posts = user.getPosts();
            Set<Map<String, Object>> userPosts = new HashSet<>();
            for (Post post : posts) {
                Map<String, Object> userPost = new HashMap<>();
                userPost.put("postUserName", user.getUserName());
                userPost.put("postText", post.getPostText());
                userPost.put("postDate", post.getPostDate());
                userPost.put("postPhoto", Base64.getEncoder().encodeToString(post.getPostPhoto()));
                userPosts.add(userPost);
            }
            return userPosts;
        }catch (Exception e){
            System.out.println("Error in getUserPosts");
            e.printStackTrace();
            return null;
        }
    }
    public Set<Map<String, Object>> getUserFriendPosts(User user) {
        try {

            Set<User> friends = user.getFriends();
            Set<Map<String, Object>> userPosts = new HashSet<>();
            for (User u : friends) {
                Set<Map<String, Object>> userPost = new HashSet<>();
                userPost = this.getUserPosts(u);
                userPosts.addAll(userPost);
            }
            return userPosts;
        }catch (Exception e){
            System.out.println("Error in getUserPosts");
            e.printStackTrace();
            return null;
        }
    }

    public Post addPost(Post post) {

        return this.postRepository.save(post);
    }
}
