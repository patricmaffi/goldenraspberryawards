package com.awards.movies.domain.factory;

import com.awards.movies.domain.Movie;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieFactoryByFile implements IMovieFactory<InputStream, List<Movie>> {

    MovieFactoryByCsvLine factory = new MovieFactoryByCsvLine();

    @Override
    public List<Movie> build(InputStream model) throws IOException {
        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new InputStreamReader(model));

        List<Movie> movieList = new ArrayList<>();
        String st;
        boolean isHeader = true;
        while ((st = br.readLine()) != null) {
            if(isHeader) {
                isHeader = false;
                continue;
            }
            movieList.add(factory.build(st));
        }
        return movieList;
    }
}
