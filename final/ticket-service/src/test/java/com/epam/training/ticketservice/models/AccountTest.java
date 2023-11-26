package com.epam.training.ticketservice.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    public void testGetPassword() {

        String expectedPassword = "testPassword";
        Account account = new Account("testUsername", expectedPassword);

        String actualPassword = account.getPassword();

        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    public void testGetUsername() {

        String expectedUsername = "testUsername";
        Account account = new Account(expectedUsername, "testPassword");

        String actualUsername = account.getUsername();

        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void testSetUsername() {

        Account account = new Account("oldUsername", "testPassword");
        String newUsername = "newUsername";

        account.setUsername(newUsername);

        assertEquals(newUsername, account.getUsername());
    }

    @Test
    public void testSetPassword() {

        Account account = new Account("testUsername", "oldPassword");
        String newPassword = "newPassword";

        account.setPassword(newPassword);

        assertEquals(newPassword, account.getPassword());
    }
}