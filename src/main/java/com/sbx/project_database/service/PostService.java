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

    public List<Map<String, Object>> getUserPosts(User user) {//todo custom json for post!!
        try {
            List<Post> posts = user.getPosts();
            List<Map<String, Object>> userPosts = new ArrayList<>();
            posts.sort(Comparator.comparing(Post::getPostDate).reversed());


            for (Post post : posts) {
                Map<String, Object> userPost = new HashMap<>();
                userPost.put("postUserName", user.getUserName());
                userPost.put("postText", post.getPostText());
                userPost.put("postDate", post.getPostDate());
                userPost.put("postPhoto", Base64.getEncoder().encodeToString(post.getPostPhoto()));

                if(user.getProfile() != null){
                    userPost.put("postUserPhoto", Base64.getEncoder().encodeToString(user.getProfile().getPhoto()));
                }else{
                    userPost.put("postUserPhoto", "");
                }
                userPosts.add(userPost);
            }
            return userPosts;
        }catch (Exception e){
            System.out.println("Error in getUserPosts");
            e.printStackTrace();
            return null;
        }
    }
    public List<Map<String, Object>> getUserFriendPosts(User user) {
        try {

            Set<User> friends = user.getFriends();
            List<Map<String, Object>> userPosts = new ArrayList<>();
            for (User u : friends) {
                List<Map<String, Object>> userPost = new ArrayList<>();
                userPost = this.getUserPosts(u);
                userPosts.addAll(userPost);
            }
            //userPosts.sort(Comparator.comparing(Ma).reversed());

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
