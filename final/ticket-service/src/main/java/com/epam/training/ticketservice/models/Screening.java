package com.epam.training.ticketservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String movie_name;

    private String room_name;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private LocalDateTime endOfBreakPeriod;

    public Screening() {
    }

    public Screening(String movie_name, String room_name, LocalDateTime startDateTime, int duration) {
        this.movie_name = movie_name;
        this.room_name = room_name;
        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plusMinutes(duration);
        this.endOfBreakPeriod = startDateTime.plusMinutes(duration + 10);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getStartDateTimeMinus(int minutes) { return startDateTime.minusMinutes(minutes); }

    public LocalDateTime getStartDateTimePlus(int minutes) { return startDateTime.plusMinutes(minutes); }

    public LocalDateTime getEndDateTimeMinus(int minutes) { return endDateTime.minusMinutes(minutes); }

    public LocalDateTime getEndDateTimePlus(int minutes) { return endDateTime.plusMinutes(minutes); }

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
