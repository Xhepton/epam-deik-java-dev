package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT COUNT(s.id) > 0 FROM Screening s WHERE s.roomName = :roomName AND"
            + "(:startDateTime BETWEEN s.startDateTime AND s.endDateTime "
            + "OR :endDateTime BETWEEN s.startDateTime AND s.endDateTime)")
    boolean existsByRoomAndStartTimeBetween(String roomName, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT COUNT(s.id) > 0 FROM Screening s WHERE s.roomName = :roomName AND "
            + "(:startDateTime BETWEEN s.endDateTime AND s.endOfBreakPeriod)")
    boolean existsByRoomAndStartTimeAfter(String roomName, LocalDateTime startDateTime);

    @Query("SELECT s FROM Screening s WHERE s.movieName = :movieName AND "
            + "s.roomName = :roomName AND s.startDateTime = :startDateTime")
    Screening findByMovieNameAndRoomNameAndStartTime(String movieName, String roomName, LocalDateTime startDateTime);

}
