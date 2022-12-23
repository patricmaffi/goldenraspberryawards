package com.awards.movies.domain.repository;

import com.awards.movies.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select * from Movie m where m.winner is true", nativeQuery = true)
    List<Movie> getOnlyWinners();

}
