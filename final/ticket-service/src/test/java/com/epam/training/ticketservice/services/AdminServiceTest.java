package com.epam.training.ticketservice.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {
    @Test
    public void testAuthenticate() {
        AdminService instance = new AdminService();

        // Test case: Valid credentials
        assertTrue(instance.authenticate("admin", "admin"));

        // Test case: Invalid username
        assertFalse(instance.authenticate("invalidUser", "admin"));

        // Test case: Invalid password
        assertFalse(instance.authenticate("admin", "invalidPassword"));

        // Test case: Both username and password are invalid
        assertFalse(instance.authenticate("invalidUser", "invalidPassword"));
    }
}