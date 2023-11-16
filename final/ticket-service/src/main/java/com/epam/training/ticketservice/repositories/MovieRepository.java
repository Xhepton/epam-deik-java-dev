package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE m.movie_name = :movieName")
    Movie findByMovieName(String movieName);
}
