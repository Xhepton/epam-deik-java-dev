package com.epam.training.ticketservice.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Room {

    @Id
    private String roomName;

    private int rows;

    private int columns;

    public Room() {
    }

    public Room(String roomName, int rows, int columns) {
        this.roomName = roomName;
        this.rows = rows;
        this.columns = columns;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
