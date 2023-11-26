package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieCommandsTest {

    @Mock
    private MovieRepository movieRepositoryMock;
    @InjectMocks
    private MovieCommands movieCommands;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateMovie() {

        String movieName = "TestMovie";
        String type = "Action";
        int length = 120;

        UserCommands.setAdminLoggedIn(true);

        movieCommands.createMovie(movieName, type, length);

        verify(movieRepositoryMock, times(1)).save(any(Movie.class));
    }

    @Test
    public void testUpdateMovie() {

        String movieTitle = "TestMovie";
        String genre = "Action";
        int duration = 120;

        Movie existingMovie = new Movie(movieTitle, genre, duration);

        UserCommands.setAdminLoggedIn(true);

        when(movieRepositoryMock.findByMovieName(movieTitle)).thenReturn(existingMovie);

        String result = movieCommands.updateMovie(movieTitle, "NewGenre", 150);

        assertEquals("Movie 'TestMovie' updated successfully.", result);

        verify(movieRepositoryMock, times(1)).save(existingMovie);
    }

    @Test
    public void testUpdateMovieNotFound() {

        String movieTitle = "NonexistentMovie";

        UserCommands.setAdminLoggedIn(true);

        when(movieRepositoryMock.findByMovieName(movieTitle)).thenReturn(null);

        String result = movieCommands.updateMovie(movieTitle, "NewGenre", 150);

        assertEquals("Movie with title 'NonexistentMovie' not found.", result);

        verify(movieRepositoryMock, never()).save(any());
    }

    @Test
    public void testUpdateMovieNotAdmin() {

        String movieTitle = "TestMovie";

        UserCommands.setAdminLoggedIn(false);

        String result = movieCommands.updateMovie(movieTitle, "NewGenre", 150);

        assertEquals("You are not signed in", result);

        verify(movieRepositoryMock, never()).save(any());
    }

    @Test
    public void testDeleteMovie() {
        // Arrange
        String movieTitle = "TestMovie";
        Movie existingMovie = new Movie(movieTitle, "Action", 120);

        // Mocking the behavior of isAdminLoggedIn in UserCommands
        UserCommands.setAdminLoggedIn(true);

        // Mocking the behavior of findByMovieName in MovieRepository
        when(movieRepositoryMock.findByMovieName(movieTitle)).thenReturn(existingMovie);

        // Act
        String result = movieCommands.deleteMovie(movieTitle);

        // Assert
        assertEquals("Movie 'TestMovie' deleted successfully.", result);

        // Verify that delete method was called with the existing movie
        verify(movieRepositoryMock, times(1)).delete(existingMovie);
    }

    @Test
    public void testDeleteMovieNotFound() {
        // Arrange
        String movieTitle = "NonexistentMovie";

        // Mocking the behavior of isAdminLoggedIn in UserCommands
        UserCommands.setAdminLoggedIn(true);

        // Mocking the behavior of findByMovieName in MovieRepository
        when(movieRepositoryMock.findByMovieName(movieTitle)).thenReturn(null);

        // Act
        String result = movieCommands.deleteMovie(movieTitle);

        // Assert
        assertEquals("Movie with title 'NonexistentMovie' not found.", result);

        // Verify that delete method was not called (movie not found)
        verify(movieRepositoryMock, never()).delete(any());
    }

    @Test
    public void testDeleteMovieNotAdmin() {
        // Arrange
        String movieTitle = "TestMovie";

        // Mocking the behavior of isAdminLoggedIn in UserCommands
        UserCommands.setAdminLoggedIn(false);

        // Act
        String result = movieCommands.deleteMovie(movieTitle);

        // Assert
        assertEquals("You are not signed in", result);

        // Verify that delete method was not called (admin not logged in)
        verify(movieRepositoryMock, never()).delete(any());
    }

    @Test
    public void testListMoviesWithMovies() {
        // Arrange
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Movie1", "Action", 120));
        movies.add(new Movie("Movie2", "Comedy", 90));

        // Mocking the behavior of findAll in MovieRepository
        when(movieRepositoryMock.findAll()).thenReturn(movies);

        // Act
        String result = movieCommands.listMovies();

        // Assert
        assertEquals("Movie1 (Action, 120 minutes)Movie2 (Comedy, 90 minutes)", result);
    }

    @Test
    public void testListMoviesEmpty() {
        // Arrange
        List<Movie> movies = new ArrayList<>();

        // Mocking the behavior of findAll in MovieRepository
        when(movieRepositoryMock.findAll()).thenReturn(movies);

        // Act
        String result = movieCommands.listMovies();

        // Assert
        assertEquals("There are no movies at the moment", result);
    }
}