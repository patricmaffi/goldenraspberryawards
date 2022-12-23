package com.awards.movies.application.command_handler;

import com.awards.movies.application.command.GetMaxRangedWinnerProducer;
import com.awards.movies.application.command.GroupingMoviesByProducer;
import com.awards.movies.application.util.MaxMovieIntervalComparator;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.repository.MovieRepository;
import com.awards.movies.application.exception.MaxAwardsIntervalNotFound;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.awards.movies.application.util.MaxMovieIntervalComparator.getMaxInterval;

/**
 * Service responsible for getting the highest award interval from a movie producer
 */
@Component
@AllArgsConstructor
public class GetMaxRangedWinnerProducerHandler implements ICommandHandler<List<List<Movie>>, GetMaxRangedWinnerProducer>{
    private static final Logger logger = LogManager.getLogger(GetMaxRangedWinnerProducerHandler.class);
    MovieRepository repository;
    GroupingMoviesByProducerHandler groupingMoviesByProducerHandler;

    /**
     * get highest award interval
     * @param GetMaxRangedWinnerProducer respective command
     * @return the list of producers and their awarded movies
     */
    @Override
    public List<List<Movie>> handle(GetMaxRangedWinnerProducer command) {
        logger.debug("Handle get list Max Ranged Winner");
        List<Movie> entities = repository.getOnlyWinners();
        var producersAward = groupingMoviesByProducerHandler
                .handle(new GroupingMoviesByProducer(entities));
        //filter producers with more than 1 award
        var producersMultiplesAwards = producersAward.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .toList();
        logger.debug("Producers with multiples awards grouped: " +producersMultiplesAwards.size());
        //Get the max interval to filter producers
        var producerMaxInterval = producersMultiplesAwards
                .stream()
                .map(Map.Entry::getValue)
                .max(new MaxMovieIntervalComparator())
                .orElseThrow(MaxAwardsIntervalNotFound::new);
        int maxInterval = getMaxInterval(producerMaxInterval);
        logger.debug("maxInterval: " + maxInterval);
        var filteredMax = producersMultiplesAwards
                .stream()
                .filter( e -> getMaxInterval(e.getValue()) == maxInterval)
                .map(Map.Entry::getValue)
                .toList();
        logger.debug("producers founded: " + filteredMax.size());
        return filteredMax;
    }
}
