package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class Commands {

    private final MovieRepository movieRepository;

    @Autowired
    public Commands(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @ShellMethod(key = "create movie", value = "Létrehoz egy filmet és menti az adatbázisba")
    public String createMovie(String movieName, String type, int length) {
        Movie movie = new Movie();
        movie.setMovie_name(movieName);
        movie.setType(type);
        movie.setLength(length);

        movieRepository.save(movie);

        return "Film mentve az adatbázisba: " + movie.getMovie_name();
    }


}
