package com.epam.training.ticketservice.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningTest {

    @Test
    public void testGetMovieName() {

        String expectedMovieName = "TestMovie";
        Screening screening = new Screening(expectedMovieName, "Room1", LocalDateTime.now(), 120);

        String actualMovieName = screening.getMovieName();

        assertEquals(expectedMovieName, actualMovieName);
    }

    @Test
    public void testSetMovieName() {

        Screening screening = new Screening("OldMovie", "Room1", LocalDateTime.now(), 120);
        String newMovieName = "NewMovie";

        screening.setMovieName(newMovieName);

        assertEquals(newMovieName, screening.getMovieName());
    }

    @Test
    public void testGetRoomName() {

        String expectedRoomName = "Room1";
        Screening screening = new Screening("TestMovie", expectedRoomName, LocalDateTime.now(), 120);

        String actualRoomName = screening.getRoomName();

        assertEquals(expectedRoomName, actualRoomName);
    }

    @Test
    public void testSetRoomName() {

        Screening screening = new Screening("TestMovie", "OldRoom", LocalDateTime.now(), 120);
        String newRoomName = "NewRoom";

        screening.setRoomName(newRoomName);

        assertEquals(newRoomName, screening.getRoomName());
    }

    @Test
    public void testGetStartDateTime() {

        LocalDateTime expectedStartDateTime = LocalDateTime.now();
        Screening screening = new Screening("TestMovie", "Room1", expectedStartDateTime, 120);

        LocalDateTime actualStartDateTime = screening.getStartDateTime();

        assertEquals(expectedStartDateTime, actualStartDateTime);
    }

    @Test
    public void testSetStartDateTime() {

        Screening screening = new Screening("TestMovie", "Room1", LocalDateTime.now(), 120);
        LocalDateTime newStartDateTime = LocalDateTime.now().plusDays(1);

        screening.setStartDateTime(newStartDateTime);

        assertEquals(newStartDateTime, screening.getStartDateTime());
    }

    @Test
    public void testSetEndDateTime() {

        Screening screening = new Screening("TestMovie", "Room1", LocalDateTime.now(), 120);
        LocalDateTime newEndDateTime = LocalDateTime.now().plusDays(1).plusMinutes(120);

        screening.setEndDateTime(newEndDateTime);

        assertEquals(newEndDateTime, screening.getEndDateTime());
    }

    @Test
    public void testGetEndOfBreakPeriod() {

        var now = LocalDateTime.now();
        LocalDateTime expectedEndOfBreakPeriod = now.plusMinutes(130);
        Screening screening = new Screening("TestMovie", "Room1", now, 120);

        LocalDateTime actualEndOfBreakPeriod = screening.getEndOfBreakPeriod();

        assertEquals(expectedEndOfBreakPeriod, actualEndOfBreakPeriod);
    }

    @Test
    public void testSetEndOfBreakPeriod() {

        Screening screening = new Screening("TestMovie", "Room1", LocalDateTime.now(), 120);
        LocalDateTime newEndOfBreakPeriod = LocalDateTime.now().plusDays(1).plusMinutes(130);

        screening.setEndOfBreakPeriod(newEndOfBreakPeriod);

        assertEquals(newEndOfBreakPeriod, screening.getEndOfBreakPeriod());
    }
}