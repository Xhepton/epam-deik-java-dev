package com.epam.training.ticketservice.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String movieName;
    private String roomName;
    private LocalDateTime startDateTime;
    @Column(name = "row_number", nullable = false)
    private int row;
    @Column(name = "column_number", nullable = false)
    private int column;

    private String username;

    public Booking() {
    }

    public Booking(String movieName, String roomName, LocalDateTime startDateTime, int row, int column, String username) {
        this.movieName = movieName;
        this.roomName = roomName;
        this.startDateTime = startDateTime;
        this.row = row;
        this.column = column;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
