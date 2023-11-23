package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class MovieCommands {

    private static MovieRepository movieRepository;

    @Autowired
    public MovieCommands(MovieRepository movieRepository) {
        MovieCommands.movieRepository = movieRepository;
    }

    public static MovieRepository getMovieRepository() {
        return movieRepository;
    }

    @ShellMethod(key = "create movie", value = "Létrehoz egy filmet és menti az adatbázisba")
    public void createMovie(String movieName, String type, int length) {
        if (UserCommands.isAdminLoggedIn()) {
            Movie movie = new Movie(movieName, type, length);

            movieRepository.save(movie);

            System.out.println("Film mentve az adatbázisba: " + movie.getMovieName());
        } else {
            System.out.println("You are not signed in");
        }
    }

    @ShellMethod(key = "update movie", value = "Update movie details")
    public String updateMovie(String movieTitle, String genre, int duration) {
        if (UserCommands.isAdminLoggedIn()) {
            // Retrieve the existing movie from the database
            Movie existingMovie = movieRepository.findByMovieName(movieTitle);

            if (existingMovie != null) {
                // Update the properties of the existing movie
                existingMovie.setType(genre);
                existingMovie.setDuration(duration);

                // Save the updated movie back to the database
                movieRepository.save(existingMovie);

                return "Movie '" + movieTitle + "' updated successfully.";
            } else {
                return "Movie with title '" + movieTitle + "' not found.";
            }
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(key = "delete movie", value = "Delete a movie")
    public String deleteMovie(String movieTitle) {
        if (UserCommands.isAdminLoggedIn()) {
            // Retrieve the existing movie from the database
            Movie existingMovie = movieRepository.findByMovieName(movieTitle);

            if (existingMovie != null) {
                // Delete the movie from the database
                movieRepository.delete(existingMovie);

                return "Movie '" + movieTitle + "' deleted successfully.";
            } else {
                return "Movie with title '" + movieTitle + "' not found.";
            }
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(key = "list movies", value = "List all movies")
    public String listMovies() {
        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder output = new StringBuilder();
            for (Movie movie : movies) {
                output.append(movie.getMovieName())
                        .append(" (")
                        .append(movie.getType())
                        .append(", ")
                        .append(movie.getDuration())
                        .append(" minutes)");
            }
            return output.toString();
        }
    }
}
