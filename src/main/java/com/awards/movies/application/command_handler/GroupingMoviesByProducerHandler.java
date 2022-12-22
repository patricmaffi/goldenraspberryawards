package com.awards.movies.application.command_handler;

import com.awards.movies.application.command.GroupingMoviesByProducer;
import com.awards.movies.domain.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service responsible for grouping movies by producers
 */
@Component
public class GroupingMoviesByProducerHandler implements ICommandHandler<Map<String, List<Movie>>, GroupingMoviesByProducer>{
    /**
     * Grouping movies
     * @param GroupingMoviesByProducer respective command containing the movies
     * @return Map of producers and their respective movies
     */
    @Override
    public Map<String, List<Movie>> handle(GroupingMoviesByProducer command) {

        var producersAward = command.getMovies().stream()
                .map(el -> {
                    String[] producers = el.getProducers()
                            .replace(" and ", ",")
                            .replace(",,",",")
                            .trim().split(",");
                    return Arrays.stream(producers)
                            .map(producer -> new Movie(el.getId(),
                                    el.getMovieYear(),
                                    el.getTitle(),
                                    el.getStudios(),
                                    producer.trim(),
                                    el.getWinner()))
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Movie::getProducers));
        return producersAward;
    }
}
