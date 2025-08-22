package com.sbx.project_database.repo;
import com.sbx.project_database.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Integer> { //Integer is the primary key for the table of users

    Optional<User> findByUserName(String userName);

}
