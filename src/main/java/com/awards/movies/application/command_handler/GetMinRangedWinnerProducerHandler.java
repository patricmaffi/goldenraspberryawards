package com.awards.movies.application.command_handler;

import com.awards.movies.application.command.GetMinRangedWinnerProducer;
import com.awards.movies.application.command.GroupingMoviesByProducer;
import com.awards.movies.application.util.MinMovieIntervalComparator;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.repository.MovieRepository;
import com.awards.movies.application.exception.MinAwardsIntervalNotFound;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.awards.movies.application.util.MinMovieIntervalComparator.getMinInterval;

/**
 * Service responsible for getting the lowest award interval from a movie producer
 */
@Component
@AllArgsConstructor
public class GetMinRangedWinnerProducerHandler implements ICommandHandler<List<List<Movie>>, GetMinRangedWinnerProducer>{
    private static final Logger logger = LogManager.getLogger(GetMinRangedWinnerProducerHandler.class);
    MovieRepository repository;
    GroupingMoviesByProducerHandler groupingMoviesByProducerHandler;
    /**
     * get lowest award interval
     * @param GetMinRangedWinnerProducer respective command
     * @return the list of producers and their awarded movies
     */
    @Override
    public List<List<Movie>> handle(GetMinRangedWinnerProducer command) {
        logger.debug("Handle get list Max Ranged Winner");
        List<Movie> entities = repository.getOnlyWinners();
        var producersAward = groupingMoviesByProducerHandler
                .handle(new GroupingMoviesByProducer(entities));
        //filter producers with more than 1 award
        var producersMultiplesAwards = producersAward.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .toList();
        logger.debug("Producers with multiples awards grouped: " +producersMultiplesAwards.size());
        //Get the minimal interval to filter producers
        var producerMinInterval = producersMultiplesAwards
                .stream()
                .map(Map.Entry::getValue)
                .min(new MinMovieIntervalComparator())
                .orElseThrow(MinAwardsIntervalNotFound::new);
        var minInterval = getMinInterval(producerMinInterval);
        logger.debug("minInterval: " + minInterval);
        var filteredMin = producersMultiplesAwards
                .stream()
                .filter( e -> getMinInterval(e.getValue()) == minInterval)
                .map(Map.Entry::getValue)
                .toList();
        logger.debug("producers founded: " + filteredMin.size());
        return filteredMin;
    }
}
