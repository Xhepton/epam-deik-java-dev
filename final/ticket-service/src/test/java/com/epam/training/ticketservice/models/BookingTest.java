package com.epam.training.ticketservice.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    @Test
    public void testGetMovieName() {

        String expectedMovieName = "TestMovie";
        Booking booking = new Booking(expectedMovieName, "Room1", LocalDateTime.now(), 1, 1, "user");

        String actualMovieName = booking.getMovieName();

        assertEquals(expectedMovieName, actualMovieName);
    }

    @Test
    public void testSetMovieName() {

        Booking booking = new Booking("OldMovie", "Room1", LocalDateTime.now(), 1, 1, "user");
        String newMovieName = "NewMovie";

        booking.setMovieName(newMovieName);

        assertEquals(newMovieName, booking.getMovieName());
    }

    @Test
    public void testGetRoomName() {

        String expectedRoomName = "Room1";
        Booking booking = new Booking("TestMovie", expectedRoomName, LocalDateTime.now(), 1, 1, "user");

        String actualRoomName = booking.getRoomName();

        assertEquals(expectedRoomName, actualRoomName);
    }

    @Test
    public void testSetRoomName() {

        Booking booking = new Booking("TestMovie", "OldRoom", LocalDateTime.now(), 1, 1, "user");
        String newRoomName = "NewRoom";

        booking.setRoomName(newRoomName);

        assertEquals(newRoomName, booking.getRoomName());
    }

    @Test
    public void testGetStartDateTime() {

        LocalDateTime expectedDateTime = LocalDateTime.now();
        Booking booking = new Booking("TestMovie", "Room1", expectedDateTime, 1, 1, "user");

        LocalDateTime actualDateTime = booking.getStartDateTime();

        assertEquals(expectedDateTime, actualDateTime);
    }

    @Test
    public void testSetStartDateTime() {

        Booking booking = new Booking("TestMovie", "Room1", LocalDateTime.now(), 1, 1, "user");
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);

        booking.setStartDateTime(newDateTime);

        assertEquals(newDateTime, booking.getStartDateTime());
    }

    @Test
    public void testGetRowNumber() {

        int expectedRowNumber = 2;
        Booking booking = new Booking("TestMovie", "Room1", LocalDateTime.now(), expectedRowNumber, 1, "user");

        int actualRowNumber = booking.getRowNumber();

        assertEquals(expectedRowNumber, actualRowNumber);
    }

    @Test
    public void testSetRowNumber() {

        Booking booking = new Booking("TestMovie", "Room1", LocalDateTime.now(), 1, 1, "user");
        int newRowNumber = 3;

        booking.setRowNumber(newRowNumber);

        assertEquals(newRowNumber, booking.getRowNumber());
    }

    @Test
    public void testGetColumnNumber() {

        int expectedColumnNumber = 3;
        Booking booking = new Booking("TestMovie", "Room1", LocalDateTime.now(), 1, expectedColumnNumber, "user");

        int actualColumnNumber = booking.getColumnNumber();

        assertEquals(expectedColumnNumber, actualColumnNumber);
    }

    @Test
    public void testSetColumnNumber() {

        Booking booking = new Booking("TestMovie", "Room1", LocalDateTime.now(), 1, 1, "user");
        int newColumnNumber = 4;

        booking.setColumnNumber(newColumnNumber);

        assertEquals(newColumnNumber, booking.getColumnNumber());
    }

    @Test
    public void testGetUsername() {

        String expectedUsername = "testUser";
        Booking booking = new Booking("TestMovie", "Room1", LocalDateTime.now(), 1, 1, expectedUsername);

        String actualUsername = booking.getUsername();

        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    public void testSetUsername() {

        Booking booking = new Booking("TestMovie", "Room1", LocalDateTime.now(), 1, 1, "oldUser");
        String newUsername = "newUser";

        booking.setUsername(newUsername);

        assertEquals(newUsername, booking.getUsername());
    }
}