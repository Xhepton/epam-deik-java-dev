package com.epam.training.ticketservice.Repositories;

import com.epam.training.ticketservice.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
