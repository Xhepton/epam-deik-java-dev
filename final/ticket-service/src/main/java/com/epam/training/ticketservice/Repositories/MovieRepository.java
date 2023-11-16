package com.epam.training.ticketservice.Repositories;

import com.epam.training.ticketservice.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
