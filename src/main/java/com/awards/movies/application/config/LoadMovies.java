package com.awards.movies.application.config;

import com.awards.movies.application.command.AddMoviesByCsv;
import com.awards.movies.application.command_handler.AddMoviesByCsvHandler;
import com.awards.movies.domain.Movie;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Component
public class LoadMovies {
    AddMoviesByCsvHandler handler;
    @PostConstruct
    public List<Movie> run() throws IOException {
        return handler.handle(new AddMoviesByCsv("static/movielist.csv"));
    }
}
