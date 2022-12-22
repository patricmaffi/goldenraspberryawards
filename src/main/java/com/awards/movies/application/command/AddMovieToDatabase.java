package com.awards.movies.application.command;

import com.awards.movies.application.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddMovieToDatabase {
    private MovieDto dto;
}
