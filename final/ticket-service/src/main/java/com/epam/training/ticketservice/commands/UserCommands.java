package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.User;
import com.epam.training.ticketservice.repositories.UserRepository;
import com.epam.training.ticketservice.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommands {

    private String loggedInUsername;
    private final AdminService adminService;
    private static boolean adminLoggedIn = false;
    private static UserRepository userRepository;

    @Autowired
    public UserCommands(AdminService adminService, UserRepository userRepository) {
        this.adminService = adminService;
        UserCommands.userRepository = userRepository;
    }

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

    @ShellMethod(key = "sign up", value = "User sign up")
    public void signUp(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
    }

    @ShellMethod(key = "sign in", value = "User sign in")
    public void signIn(String username, String password) {
        User user = userRepository.findUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            loggedInUsername = username;
        } else {
            System.out.println("Login failed due to incorrect credentials");
        }
    }

    @ShellMethod(key = "sign out", value = "Admin logout")
    public void signOut() {
        adminLoggedIn = false;
        loggedInUsername = null;
    }

    @ShellMethod(key = "describe account", value = "Describe the current account")
    public String describeAccount() {
        if (adminLoggedIn || loggedInUsername != null) {
            return "Signed in with privileged account '" + loggedInUsername + "'";
        } else {
            return "You are not signed in";
        }
    }
}
