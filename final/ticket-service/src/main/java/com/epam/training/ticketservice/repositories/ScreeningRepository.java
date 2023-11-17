package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT COUNT(s.id) > 0 FROM Screening s WHERE s.room_name = :room_name AND (:startDateTime BETWEEN s.startDateTime AND s.endDateTime OR :endDateTime BETWEEN s.startDateTime AND s.endDateTime)")
    boolean existsByRoomAndStartTimeBetween(String room_name, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT COUNT(s.id) > 0 FROM Screening s WHERE s.room_name = :room_name AND (:startDateTime BETWEEN s.endDateTime AND s.endOfBreakPeriod)")
    boolean existsByRoomAndStartTimeAfter(String room_name, LocalDateTime startDateTime);

    @Query("SELECT s FROM Screening s WHERE s.movie_name = :movie_name AND s.room_name = :room_name AND s.startDateTime = :startDateTime")
    Screening findByMovieNameAndRoomNameAndStartTime(String movie_name, String room_name, LocalDateTime startDateTime);

}
