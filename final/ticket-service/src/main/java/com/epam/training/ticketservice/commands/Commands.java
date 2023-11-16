package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Commands {

    private final MovieRepository movieRepository;
    private final AdminService adminService;
    private boolean adminLoggedIn = false;
    private String loggedInUsername;

    @Autowired
    public Commands(MovieRepository movieRepository, AdminService adminService) {
        this.movieRepository = movieRepository;
        this.adminService = adminService;
    }
    @ShellMethod(key = "sign in privileged", value = "Admin login")
    public String signInPrivileged(String username, String password) {
        if (adminService.authenticate(username, password)) {
            adminLoggedIn = true;
            loggedInUsername = username;
            return "Admin login successful. You now have access to privileged commands.";
        } else {
            return "Login failed due to incorrect credentials.";
        }
    }
    @ShellMethod(key = "sign out", value = "Admin logout")
    public String signOut() {
        adminLoggedIn = false;
        return "Admin logout successful. You no longer have access to privileged commands.";
    }

    @ShellMethod(key = "describe account", value = "Describe the current account")
    public String describeAccount() {
        if (adminLoggedIn) {
            return "Signed in with privileged account '" + loggedInUsername + "'";
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(key = "create movie", value = "Létrehoz egy filmet és menti az adatbázisba")
    public String createMovie(String movieName, String type, int length) {
        if (adminLoggedIn) {
            Movie movie = new Movie();
            movie.setMovie_name(movieName);
            movie.setType(type);
            movie.setLength(length);

            movieRepository.save(movie);

            return "Film mentve az adatbázisba: " + movie.getMovie_name();
        }
        else {
            return "You are not connected!";
        }
    }

    @ShellMethod(key = "update movie", value = "Update movie details")
    public String updateMovie(String movieTitle, String genre, int duration) {
        if (adminLoggedIn) {
            // Retrieve the existing movie from the database
            Movie existingMovie = movieRepository.findByMovieName(movieTitle);

            if (existingMovie != null) {
                // Update the properties of the existing movie
                existingMovie.setType(genre);
                existingMovie.setLength(duration);

                // Save the updated movie back to the database
                movieRepository.save(existingMovie);

                return "Movie '" + movieTitle + "' updated successfully.";
            } else {
                return "Movie with title '" + movieTitle + "' not found.";
            }
        } else {
            return "You must be logged in as admin to execute this command.";
        }
    }
}
