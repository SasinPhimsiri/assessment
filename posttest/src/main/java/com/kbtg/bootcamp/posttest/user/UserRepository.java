package com.kbtg.bootcamp.posttest.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, String> {
    Collection<Object> findByUserIdAndTicketNumber(String userId, String ticketNumber);
}
