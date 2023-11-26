package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class RoomCommandsTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomCommands roomCommands;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRoom_AdminLoggedIn_RoomDoesNotExist() {

        when(roomRepository.existsByRoomName("TestRoom")).thenReturn(false);

        roomCommands.createRoom("TestRoom", 5, 5);

        assertFalse(roomRepository.existsByRoomName("TestRoom"));

    }

    @Test
    void createRoom_RoomAlreadyExists() {

        UserCommands.setAdminLoggedIn(true);

        String existingRoomName = "ExistingRoom";
        when(roomRepository.existsByRoomName(existingRoomName)).thenReturn(true);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        roomCommands.createRoom(existingRoomName, 5, 5);

        assertTrue(roomRepository.existsByRoomName(existingRoomName));

        assertEquals("Room with name 'ExistingRoom' already exists.", outputStreamCaptor.toString().trim());
    }

    @Test
    void updateRoom_AdminLoggedIn_RoomExists() {

        UserCommands.setAdminLoggedIn(true);

        String existingRoomName = "ExistingRoom";
        int newRows = 10;
        int newColumns = 8;

        Room existingRoom = new Room(existingRoomName, 5, 5);
        when(roomRepository.findByRoomName(existingRoomName)).thenReturn(existingRoom);

        // Act
        roomCommands.updateRoom(existingRoomName, newRows, newColumns);

        // Assert
        assertEquals(newRows, existingRoom.getRows());
        assertEquals(newColumns, existingRoom.getColumns());
        verify(roomRepository, times(1)).save(existingRoom);
    }

    @Test
    void updateRoom_AdminLoggedIn_RoomDoesNotExist() {

        UserCommands.setAdminLoggedIn(true);

        String nonExistingRoomName = "NonExistingRoom";
        int newRows = 10;
        int newColumns = 8;

        when(roomRepository.findByRoomName(nonExistingRoomName)).thenReturn(null);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        roomCommands.updateRoom(nonExistingRoomName, newRows, newColumns);

        verify(roomRepository, never()).save(any(Room.class));

        assertEquals("Cannot find room with the given room name: NonExistingRoom", outputStreamCaptor.toString().trim());
    }

    @Test
    void updateRoom_NotAdminLoggedIn() {

        mockStatic(UserCommands.class);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        roomCommands.updateRoom("AnyRoom", 5, 5);

        assertEquals("You are not signed in", outputStreamCaptor.toString().trim());
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    public void testDeleteRoom() {

        String roomName = "TestRoom";
        Room existingRoom = new Room();

        UserCommands.setAdminLoggedIn(true);

        when(roomRepository.findByRoomName(roomName)).thenReturn(existingRoom);

        roomCommands.deleteRoom(roomName);

        Mockito.verify(roomRepository, Mockito.times(1)).delete(existingRoom);
    }

    @Test
    public void testListRooms() {
        // Arrange
        List<Room> mockRooms = new ArrayList<>();
        Room room1 = new Room("Room1", 5, 3);
        Room room2 = new Room("Room2", 10, 5);
        mockRooms.add(room1);
        mockRooms.add(room2);

        when(roomRepository.findAll()).thenReturn(mockRooms);

        String result = roomCommands.listRooms();

        assertTrue(result.contains("Room Room1 with 15 seats, 5 rows and 3 columns"));
        assertTrue(result.contains("Room Room2 with 50 seats, 10 rows and 5 columns"));
    }

    @Test
    public void testListRoomsEmpty() {

        List<Room> emptyList = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(emptyList);

        String result = roomCommands.listRooms();

        assertEquals("There are no rooms at the moment", result);
    }
}