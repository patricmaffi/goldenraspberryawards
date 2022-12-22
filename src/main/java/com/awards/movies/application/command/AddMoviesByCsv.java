package com.awards.movies.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class AddMoviesByCsv {
    private String pathCsv;
}
