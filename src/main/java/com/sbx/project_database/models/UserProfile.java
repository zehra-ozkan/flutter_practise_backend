package com.sbx.project_database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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
    @JsonIgnore  // ← Prevents JSON serialization of this field
    @ToString.Exclude
    private User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return profileUserId == that.profileUserId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUserId);
    }
}
