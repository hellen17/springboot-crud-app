package com.example.crud_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crud_app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // This interface will automatically provide CRUD operations for User entities
    // No additional code is needed here unless you want to add custom query methods

}
