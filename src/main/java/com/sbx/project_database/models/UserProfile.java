package com.sbx.project_database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Arrays;

@Entity
@Data
@Table(name="user_profiles")
public class UserProfile {

    @Id
    private int profileUserId;

    @Lob
    @Column(name = "profile_pic", columnDefinition = "VARBINARY(MAX)")
    private byte[] photo;

    @OneToOne
    @MapsId // CRITICAL: This maps the user's ID to profileUserId
    @JoinColumn(name = "profile_user_id") // Owning side - has the foreign key
    private User user;

    @Override
    public String toString() {
        return "UserProfile{" +
                "profileUserId=" + profileUserId +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
