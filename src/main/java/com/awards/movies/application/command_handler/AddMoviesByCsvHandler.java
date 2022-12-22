package com.awards.movies.application.command_handler;

import com.awards.movies.application.command.AddMoviesByCsv;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.MovieRepository;
import com.awards.movies.domain.factory.MovieFactoryByFile;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class AddMoviesByCsvHandler implements ICommandHandler<List<Movie>, AddMoviesByCsv> {
    private static final Logger logger = LogManager.getLogger(AddMoviesByCsvHandler.class);
    private MovieFactoryByFile movieFactory;
    private MovieRepository movieRepository;
    @Override
    public List<Movie> handle(AddMoviesByCsv command) throws IOException {
        logger.debug("Handle Movies by csv");
        ClassPathResource cr = new ClassPathResource(command.getPathCsv());
        List<Movie> entities = this.movieFactory.build(cr.getInputStream());
        logger.debug("Saving Movies by csv");
        return this.movieRepository.saveAll(entities);
    }
}
