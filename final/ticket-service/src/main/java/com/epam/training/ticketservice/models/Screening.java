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

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getEndOfBreakPeriod() {
        return endOfBreakPeriod;
    }

    public void setEndOfBreakPeriod(LocalDateTime endOfBreakPeriod) {
        this.endOfBreakPeriod = endOfBreakPeriod;
    }
}
