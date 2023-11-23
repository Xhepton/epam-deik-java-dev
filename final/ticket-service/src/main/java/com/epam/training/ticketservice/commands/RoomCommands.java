package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Room;
import com.epam.training.ticketservice.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class RoomCommands {

    private static RoomRepository roomRepository;

    @Autowired
    public RoomCommands(RoomRepository roomRepository) {
        RoomCommands.roomRepository = roomRepository;
    }

    public static RoomRepository getRoomRepository() {
        return roomRepository;
    }

    @ShellMethod(key = "create room", value = "Create a room")
    public void createRoom(String roomName, int rows, int columns) {
        if (UserCommands.isAdminLoggedIn()) {
            // Check if a room with the same name already exists
            if (roomRepository.existsByRoomName(roomName)) {
                System.out.println("Room with name '" + roomName + "' already exists.");
            } else {
                // Create a new room
                Room newRoom = new Room(roomName, rows, columns);

                // Save the new room to the database
                roomRepository.save(newRoom);
            }

        } else {
            System.out.println("You must be logged in as admin to execute this command.");
        }
    }

    @ShellMethod(key = "update room", value = "Update a room")
    public void updateRoom(String roomName, int rows, int columns) {
        if (UserCommands.isAdminLoggedIn()) {
            Room existingRoom = roomRepository.findByRoomName(roomName);

            if (existingRoom != null) {
                // Update the room properties
                existingRoom.setRows(rows);
                existingRoom.setColumns(columns);

                // Save the updated room
                roomRepository.save(existingRoom);
            } else {
                System.out.println("Cannot find room with the given room name: " + roomName);
            }
        } else {
            System.out.println("You are not signed in");
        }
    }

    @ShellMethod(key = "delete room", value = "Delete a room")
    public void deleteRoom(String roomName) {
        if (UserCommands.isAdminLoggedIn()) {
            Room existingRoom = roomRepository.findByRoomName(roomName);

            if (existingRoom != null) {
                // Delete the room from the database
                roomRepository.delete(existingRoom);

            } else {
                System.out.println("Cannot find room with the given room name: " + roomName);
            }
        } else {
            System.out.println("You are not signed in");
        }
    }

    @ShellMethod(key = "list rooms", value = "List rooms")
    public String listRooms() {
        List<Room> rooms = roomRepository.findAll();
        StringBuilder result = new StringBuilder();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            for (Room room : rooms) {
                result.append(String.format("Room %s with %d seats, %d rows and %d columns",
                        room.getRoomName(),
                        room.getColumns() * room.getRows(),
                        room.getRows(),
                        room.getColumns()
                ));
            }
            return result.toString();
        }
    }
}
