package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.room_name = :room_name")
    Room findByRoomName(String room_name);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Room r WHERE r.room_name = :room_name")
    boolean existsByRoomName(String room_name);
}
