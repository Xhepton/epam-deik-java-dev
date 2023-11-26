package com.epam.training.ticketservice.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    public void testGetRoomName() {

        String expectedRoomName = "TestRoom";
        Room room = new Room(expectedRoomName, 5, 10);

        String actualRoomName = room.getRoomName();

        assertEquals(expectedRoomName, actualRoomName);
    }

    @Test
    public void testSetRoomName() {

        Room room = new Room("OldRoom", 5, 10);
        String newRoomName = "NewRoom";

        room.setRoomName(newRoomName);

        assertEquals(newRoomName, room.getRoomName());
    }

    @Test
    public void testGetRows() {

        int expectedRows = 7;
        Room room = new Room("TestRoom", expectedRows, 8);

        int actualRows = room.getRows();

        assertEquals(expectedRows, actualRows);
    }

    @Test
    public void testSetRows() {

        Room room = new Room("TestRoom", 5, 10);
        int newRows = 6;

        room.setRows(newRows);

        assertEquals(newRows, room.getRows());
    }

    @Test
    public void testGetColumns() {

        int expectedColumns = 12;
        Room room = new Room("TestRoom", 5, expectedColumns);

        int actualColumns = room.getColumns();

        assertEquals(expectedColumns, actualColumns);
    }

    @Test
    public void testSetColumns() {

        Room room = new Room("TestRoom", 5, 10);
        int newColumns = 15;

        room.setColumns(newColumns);

        assertEquals(newColumns, room.getColumns());
    }
}