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

    @Autowired
    public Commands(MovieRepository movieRepository, AdminService adminService) {
        this.movieRepository = movieRepository;
        this.adminService = adminService;
    }
    @ShellMethod(key = "sign in privileged", value = "Admin login")
    public String signInPrivileged(String username, String password) {
        if (adminService.authenticate(username, password)) {
            adminLoggedIn = true;
            return "Admin login successful. You now have access to privileged commands.";
        } else {
            return "Login failed due to incorrect credentials.";
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
}
