package com.epam.training.ticketservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SuppressWarnings("unused")
    private Long id;
    @SuppressWarnings("unused")
    private String movieName;
    @SuppressWarnings("unused")
    private String roomName;
    private LocalDateTime startDateTime;
    private int rowNumber;
    private int columnNumber;
    @SuppressWarnings("unused")
    private String username;

    public Booking() {
    }

    public Booking(String movieName,
                   String roomName,
                   LocalDateTime startDateTime,
                   int rowNumber,
                   int columnNumber,
                   String username) {
        this.movieName = movieName;
        this.roomName = roomName;
        this.startDateTime = startDateTime;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.username = username;
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

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
