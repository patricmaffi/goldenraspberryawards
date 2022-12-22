package com.awards.movies.domain.factory;

import com.awards.movies.domain.Movie;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class MovieFactoryByCsvLine implements IMovieFactory<String, Movie> {

    @Override
    public Movie build(String model) throws IOException {

        String[] arrModel = model.split(";");
        return new Movie(
                null,
                Long.parseLong(arrModel[0]),
                arrModel[1],
                arrModel[2],
                arrModel[3],
                arrModel.length >= 5 && arrModel[4].equals("yes"));

    }
}
