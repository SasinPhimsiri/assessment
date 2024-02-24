package com.kbtg.bootcamp.posttest.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, length = 10)
    private final String userId; // Making userId final to enforce immutability

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public User(String userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now(); // Initialize createdAt in the constructor
        this.updatedAt = LocalDateTime.now(); // Initialize updatedAt in the constructor
    }


    // Getter for userId
    public String getUserId() {
        return userId;
    }

    // Getter for createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setter for createdAt if needed, else remove this setter
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Getter for updatedAt
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setter for updatedAt if needed, else remove this setter
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
