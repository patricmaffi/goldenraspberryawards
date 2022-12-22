package com.awards.movies.application.presenter;

import com.awards.movies.application.util.MaxMovieIntervalComparator;
import com.awards.movies.application.util.MinMovieIntervalComparator;
import com.awards.movies.domain.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PremiumRangePresenter{

    public PremiumRangeView present(List<Movie> entities) {
        Movie entity = entities.get(0);
        Movie entity2 = entities.get(1);
        return new PremiumRangeView()
                .withProducer(entity.getProducers())
                .withPreviousWin(entity.getMovieYear())
                .withFollowingWin(entity2.getMovieYear());
    }
    public PremiumRangeView presentMin(List<Movie> entities) {
        int interval = MinMovieIntervalComparator.getMinInterval(entities);
        var orderedMovies = entities.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        var rangedMovies = filterMinIntervalMovies(orderedMovies, interval);
        var view = present(rangedMovies);
        return view.withInterval(interval);
    }
    public PremiumRangeView presentMax(List<Movie> entities) {
        int interval = MaxMovieIntervalComparator.getMaxInterval(entities);
        var orderedMovies = entities.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        var rangedMovies = filterMaxIntervalMovies(orderedMovies, interval);
        var view = present(rangedMovies);
        return view.withInterval(interval);
    }

    private List<Movie> filterMinIntervalMovies(List<Movie> movies, int interval){
        Movie last = movies.get(0);
        List<Movie> rangedList = new ArrayList<>();
        for (Movie movie: movies) {
            var  a = (int)(movie.getMovieYear() - last.getMovieYear());
            if(a == interval) {
                rangedList.add(last);
                rangedList.add(movie);
            }
            last = movie;
        }
        return rangedList;
    }

    private List<Movie> filterMaxIntervalMovies(List<Movie> movies, int interval){
        Movie last = movies.get(0);
        List<Movie> rangedList = new ArrayList<>();
        for (Movie movie: movies) {
            var  a = (int)(movie.getMovieYear() - last.getMovieYear());
            if(a == interval) {
                rangedList.add(last);
                rangedList.add(movie);
            }
            last = movie;
        }
        return rangedList;
    }
}
