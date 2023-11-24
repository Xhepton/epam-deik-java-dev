package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

    Account findUserByUsername(String username);
}
