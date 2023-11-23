package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.services.AdminService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommands {

    private String loggedInUsername;
    private final AdminService adminService;
    private static boolean adminLoggedIn = false;

    public UserCommands(AdminService adminService) {
        this.adminService = adminService;
    }

    public static boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }

    @ShellMethod(key = "sign in privileged", value = "Admin login")
    public void signInPrivileged(String username, String password) {
        if (adminService.authenticate(username, password)) {
            adminLoggedIn = true;
            loggedInUsername = username;
        } else {
            System.out.println("Login failed due to incorrect credentials");
        }
    }

    @ShellMethod(key = "sign out", value = "Admin logout")
    public void signOut() {
        adminLoggedIn = false;
    }

    @ShellMethod(key = "describe account", value = "Describe the current account")
    public String describeAccount() {
        if (adminLoggedIn) {
            return "Signed in with privileged account '" + loggedInUsername + "'";
        } else {
            return "You are not signed in";
        }
    }
}
