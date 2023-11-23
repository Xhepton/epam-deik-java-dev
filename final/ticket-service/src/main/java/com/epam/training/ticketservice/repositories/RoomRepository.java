package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.roomName = :roomName")
    Room findByRoomName(String roomName);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Room r WHERE r.roomName = :roomName")
    boolean existsByRoomName(String roomName);
}
