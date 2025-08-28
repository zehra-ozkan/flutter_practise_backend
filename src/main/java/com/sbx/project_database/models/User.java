package com.sbx.project_database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "user_name", unique = true)
    private String userName;

    private String user_password;
    private Date birthday;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // ← Prevents JSON serialization of this field
    @ToString.Exclude
    private UserProfile profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnore  // ← Prevents JSON serialization of this field
    @ToString.Exclude
    private Set<User> friends = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "postUser")
    @JsonIgnore  // ← Prevents JSON serialization of this field
    @ToString.Exclude
    private Set<Post> posts = new HashSet<>();




    public User(String name){
        this.userName = name;
    }
    public User(){

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_id == user.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id);
    }


}
