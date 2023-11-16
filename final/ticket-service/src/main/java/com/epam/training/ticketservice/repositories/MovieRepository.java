package com.epam.training.ticketservice.repositories;

import com.epam.training.ticketservice.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
