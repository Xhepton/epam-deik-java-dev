package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Booking;
import com.epam.training.ticketservice.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingCommandsTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingCommands bookingCommands;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookTicket() {

        UserCommands.setLoggedInUsername("TestUser");

        String movieName = "TestMovie";
        String roomName = "TestRoom";
        String startDateTimeS = "2023-01-01 12:00";
        String seats = "1,1 2,2";

        when(bookingRepository.existsByRowNumberAndColumnNumber(1, 1)).thenReturn(false);
        when(bookingRepository.existsByRowNumberAndColumnNumber(2, 2)).thenReturn(false);

        String result = bookingCommands.bookTicket(movieName, roomName, startDateTimeS, seats);

        assertEquals("Seats booked: (1,1), (2,2); the price for this booking is 3000 HUF", result);
    }

    @Test
    void testBookTicketSeatAlreadyTaken() {

        UserCommands.setLoggedInUsername("TestUser");

        String movieName = "TestMovie";
        String roomName = "TestRoom";
        String startDateTimeS = "2023-01-01 12:00";
        String seats = "1,1 2,2";

        when(bookingRepository.existsByRowNumberAndColumnNumber(1, 1)).thenReturn(true);

        String result = bookingCommands.bookTicket(movieName, roomName, startDateTimeS, seats);

        assertEquals("Seat (1,1) is already taken", result);

        verify(bookingRepository, never()).save(any(Booking.class));
    }
}