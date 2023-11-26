package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Booking;
import com.epam.training.ticketservice.models.Account;
import com.epam.training.ticketservice.repositories.BookingRepository;
import com.epam.training.ticketservice.repositories.UserRepository;
import com.epam.training.ticketservice.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
public class UserCommands {

    private static String loggedInUsername;
    private final AdminService adminService;
    private static boolean adminLoggedIn = false;
    private static UserRepository userRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public UserCommands(AdminService adminService, UserRepository userRepository, BookingRepository bookingRepository) {
        this.adminService = adminService;
        UserCommands.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public UserCommands(AdminService adminService) {
        this.adminService = adminService;
    }

    public static boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }

    public static void setAdminLoggedIn(boolean adminLoggedIn) {
        UserCommands.adminLoggedIn = adminLoggedIn;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static void setLoggedInUsername(String loggedInUsername) {
        UserCommands.loggedInUsername = loggedInUsername;
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
        Account account = new Account(username, password);
        userRepository.save(account);
    }

    @ShellMethod(key = "sign in", value = "User sign in")
    public void signIn(String username, String password) {
        Account account = userRepository.findUserByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
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
        if (adminLoggedIn) {
            return "Signed in with privileged account 'admin'";
        } else if (loggedInUsername != null) {
            List<Booking> bookings = bookingRepository.findAllByUsername(loggedInUsername);
            if (bookings.isEmpty()) {
                return "Signed in with account '" + loggedInUsername + "'" + "\n"
                        + "You have not booked any tickets yet";
            } else {
                StringBuilder seatsToPrint = new StringBuilder();
                for (Booking booking : bookings) {
                    if (booking.equals(bookings.get(bookings.size() - 1))) {
                        seatsToPrint.append(String.format("(%d,%d)",
                                booking.getRowNumber(), booking.getColumnNumber()));
                    } else {
                        seatsToPrint.append(String.format("(%d,%d), ",
                                booking.getRowNumber(), booking.getColumnNumber()));
                    }
                }
                return "Signed in with account '" + loggedInUsername + "'" + "\n"
                        + "Your previous bookings are" + "\n"
                        + String.format("Seats %s on Sátántangó in room Pedersoli starting at %s for %d HUF",
                                seatsToPrint,
                                bookings.get(0).getStartDateTime()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                bookings.size() * 1500);
            }
        } else {
            return "You are not signed in";
        }
    }
}
