package com.sbx.project_database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(insertable=false, updatable=false)
    private int postUserId;


    @ManyToOne
    @JoinColumn(name = "postUserId", nullable = false) // has the foreign key
    @JsonIgnore  // ← Prevents JSON serialization of this field
    @ToString.Exclude
    private User postUser;

    @Lob
    @Column(name = "post_photo", columnDefinition = "VARBINARY(MAX)")
    private byte[] postPhoto;

    private String postText;

    private LocalDateTime postDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post that = (Post) o;
        return postId == that.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }

}
