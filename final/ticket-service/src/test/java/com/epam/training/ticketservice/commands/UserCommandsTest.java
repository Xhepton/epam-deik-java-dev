package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Account;
import com.epam.training.ticketservice.models.Booking;
import com.epam.training.ticketservice.repositories.BookingRepository;
import com.epam.training.ticketservice.repositories.UserRepository;
import com.epam.training.ticketservice.services.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCommandsTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private BookingRepository bookingRepositoryMock;

    @Mock
    private AdminService adminServiceMock;

    @InjectMocks
    private UserCommands userCommands;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsAdminLoggedIn() {

        UserCommands.setAdminLoggedIn(true);

        boolean result = UserCommands.isAdminLoggedIn();

        assertTrue(result);
    }

    @Test
    public void testGetLoggedInUsername_AfterLoggingIn_ShouldReturnUsername() {

        String username = "testUser";
        UserCommands.setLoggedInUsername(username);

        String result = UserCommands.getLoggedInUsername();

        assertEquals(username, result);
    }

    @Test
    public void testSignInPrivileged_CorrectCredentials_ShouldSetLoggedInState() {

        String username = "admin";
        String password = "admin";
        when(adminServiceMock.authenticate(username, password)).thenReturn(true);

        userCommands.signInPrivileged(username, password);

        assertTrue(UserCommands.isAdminLoggedIn());
        assertEquals(username, UserCommands.getLoggedInUsername());
    }

    @Test
    public void testSignInPrivileged_IncorrectCredentials_ShouldPrintErrorMessage() {

        userCommands.signOut();

        String username = "admin";
        String password = "wrongPassword";

        userCommands.signInPrivileged(username, password);

        assertFalse(UserCommands.isAdminLoggedIn());
        assertNull(UserCommands.getLoggedInUsername());

    }

    @Test
    public void testSignUp_ShouldSaveUserAccount() {

        String username = "newUser";
        String password = "Password123";

        userCommands.signUp(username, password);

        verify(userRepositoryMock, times(1)).save(argThat(account ->
                account.getUsername().equals(username) &&
                        account.getPassword().equals(password)
        ));
    }
    @Test
    public void testSignIn_CorrectCredentials_ShouldSetLoggedInUsername() {

        String username = "testUser";
        String password = "testPassword";
        Account mockAccount = new Account(username, password);
        when(userRepositoryMock.findUserByUsername(username)).thenReturn(mockAccount);

        userCommands.signIn(username, password);

        assertEquals(username, UserCommands.getLoggedInUsername());
    }
    @Test
    public void testSignOut_AdminLoggedIn_ShouldResetAdminState() {

        UserCommands.setAdminLoggedIn(true);
        UserCommands.setLoggedInUsername("admin");

        userCommands.signOut();

        assertFalse(UserCommands.isAdminLoggedIn());
        assertNull(UserCommands.getLoggedInUsername());
    }

    @Test
    public void testDescribeAccount_AdminLoggedIn_ShouldReturnAdminDescription() {

        UserCommands.setAdminLoggedIn(true);

        String result = userCommands.describeAccount();

        assertEquals("Signed in with privileged account 'admin'", result);
    }
    @Test
    public void testDescribeAccount_UserLoggedInWithNoBookings_ShouldReturnUserDescription() {

        UserCommands.setAdminLoggedIn(false);
        UserCommands.setLoggedInUsername("testUser");

        String result = userCommands.describeAccount();

        assertEquals("Signed in with account 'testUser'\nYou have not booked any tickets yet", result);
    }

    @Test
    public void testDescribeAccount_UserLoggedInWithBookings_ShouldReturnUserDescriptionWithBookings() {

        UserCommands.setAdminLoggedIn(false);
        UserCommands.setLoggedInUsername("testUser");

        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking("Sátántangó", "Pedersoli", LocalDateTime.now(), 1, 1, "testUser"));
        bookings.add(new Booking("Sátántangó", "Pedersoli", LocalDateTime.now(), 2, 2, "testUser"));

        when(bookingRepositoryMock.findAllByUsername("testUser")).thenReturn(bookings);

        String result = userCommands.describeAccount();

        String expected = "Signed in with account 'testUser'\n" +
                "Your previous bookings are\n" +
                "Seats (1,1), (2,2) on Sátántangó in room Pedersoli starting at " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " for " + bookings.size() * 1500 + " HUF";

        assertEquals(expected, result);
    }

    @Test
    public void testDescribeAccount_NotLoggedIn_ShouldReturnNotLoggedInDescription() {

        UserCommands.setAdminLoggedIn(false);
        UserCommands.setLoggedInUsername(null);

        String result = userCommands.describeAccount();

        assertEquals("You are not signed in", result);
    }
}