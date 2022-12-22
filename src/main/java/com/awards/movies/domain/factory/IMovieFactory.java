package com.awards.movies.domain.factory;

import java.io.IOException;


public interface IMovieFactory<T,M> {
    M build(T model) throws IOException;
}
