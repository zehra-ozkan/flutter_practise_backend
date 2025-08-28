package com.sbx.project_database.service;


import com.sbx.project_database.models.Post;
import com.sbx.project_database.models.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class PostService {
    public Set<Post> getUserPosts(User user) {
        try {
            Set<Post> posts = user.getPosts();

            return posts;
        }catch (Exception e){
            System.out.println("Error in getUserPosts");
            e.printStackTrace();
            return null;
        }

    }
}
