package com.epam.training.ticketservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SuppressWarnings("unused")
    private Long id;

    private String movieName;

    private String roomName;

    private LocalDateTime startDateTime;

    @SuppressWarnings("unused")
    private LocalDateTime endDateTime;

    @SuppressWarnings("unused")
    private LocalDateTime endOfBreakPeriod;

    public Screening() {
    }

    public Screening(String movieName, String roomName, LocalDateTime startDateTime, int duration) {
        this.movieName = movieName;
        this.roomName = roomName;
        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plusMinutes(duration);
        this.endOfBreakPeriod = startDateTime.plusMinutes(duration + 10);
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRoomName() {
        return roomName;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
}
