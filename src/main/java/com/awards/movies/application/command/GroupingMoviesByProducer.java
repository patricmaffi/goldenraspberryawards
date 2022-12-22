package com.awards.movies.application.command;

import com.awards.movies.domain.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupingMoviesByProducer {
    private List<Movie> movies;
}
