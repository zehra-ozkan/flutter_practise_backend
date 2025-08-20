package com.sbx.project_database.models;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;

@Entity
@Data
@Table(name = "app_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "user_name", unique = true)
    private String userName;
    private String user_password;
    private Date birthday;

    public User(String name){
        this.userName = name;
    }
    public User(){

    }
    @Override
    public String toString(){
        return this.userName + " " + this.user_password + " " + this.birthday;
    }
}
