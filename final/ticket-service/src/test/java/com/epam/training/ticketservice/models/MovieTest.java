package com.epam.training.ticketservice.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    public void testGetMovieName() {

        String expectedMovieName = "TestMovie";
        Movie movie = new Movie(expectedMovieName, "Action", 120);

        String actualMovieName = movie.getMovieName();

        assertEquals(expectedMovieName, actualMovieName);
    }

    @Test
    public void testSetMovieName() {

        Movie movie = new Movie("OldMovie", "Drama", 150);
        String newMovieName = "NewMovie";

        movie.setMovieName(newMovieName);

        assertEquals(newMovieName, movie.getMovieName());
    }

    @Test
    public void testGetType() {

        String expectedType = "Comedy";
        Movie movie = new Movie("FunnyMovie", expectedType, 90);

        String actualType = movie.getType();

        assertEquals(expectedType, actualType);
    }

    @Test
    public void testSetType() {

        Movie movie = new Movie("MysteryMovie", "OldType", 120);
        String newType = "Thriller";

        movie.setType(newType);

        assertEquals(newType, movie.getType());
    }

    @Test
    public void testGetDuration() {

        int expectedDuration = 180;
        Movie movie = new Movie("EpicMovie", "Adventure", expectedDuration);

        int actualDuration = movie.getDuration();

        assertEquals(expectedDuration, actualDuration);
    }

    @Test
    public void testSetDuration() {

        Movie movie = new Movie("ShortFilm", "Documentary", 60);
        int newDuration = 75;

        movie.setDuration(newDuration);

        assertEquals(newDuration, movie.getDuration());
    }
}