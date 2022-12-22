package com.awards.movies.domain.factory;

import com.awards.movies.application.dto.MovieDto;
import com.awards.movies.domain.Movie;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieFactoryByDto implements IMovieFactory<MovieDto, Movie> {
    @Override
    public Movie build(MovieDto model) {
        return new Movie(null,
                model.getMovieYear(),
                model.getTitle(),
                model.getStudios(),
                model.getProducers(),
                model.getWinner());
    }
    public List<Movie> buildMany(List<MovieDto> dtos){
        return dtos.stream()
                .map(e -> this.build(e))
                .toList();
    }
}
