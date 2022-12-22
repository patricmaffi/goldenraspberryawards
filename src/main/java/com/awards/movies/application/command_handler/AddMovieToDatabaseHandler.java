package com.awards.movies.application.command_handler;

import com.awards.movies.application.command.AddMovieToDatabase;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.MovieRepository;
import com.awards.movies.domain.factory.MovieFactoryByDto;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddMovieToDatabaseHandler implements ICommandHandler<Movie, AddMovieToDatabase> {
    private static final Logger logger = LogManager.getLogger(AddMovieToDatabaseHandler.class);

    MovieRepository repository;
    MovieFactoryByDto factory;

    @Override
    public Movie handle(AddMovieToDatabase command) {
        logger.debug("Saving Movie");
        return repository.save(factory.build(command.getDto()));
    }
}
