package com.epam.training.ticketservice.services;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public boolean authenticate(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }
}
