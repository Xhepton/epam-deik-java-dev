package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
}
