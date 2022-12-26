package com.awards.movies.application.presenter;

import com.awards.movies.application.util.MaxMovieIntervalComparator;
import com.awards.movies.application.util.MinMovieIntervalComparator;
import com.awards.movies.application.view.IView;
import com.awards.movies.application.view.PremiumRangeView;
import com.awards.movies.domain.Movie;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PremiumRangePresenter{

    public PremiumRangeView present(Movie entity, Movie entity2) {
        return new PremiumRangeView()
                .withProducer(entity.getProducers())
                .withPreviousWin(entity.getMovieYear())
                .withFollowingWin(entity2.getMovieYear());
    }
    public List<PremiumRangeView> presentMin(List<Movie> entities) {
        int interval = MinMovieIntervalComparator.getMinInterval(entities);
        var orderedMovies = entities.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        var rangedMovies = filterMinIntervalMovies(orderedMovies, interval);

        List<PremiumRangeView> winners = new ArrayList<>();
        for(int i = 0; i < rangedMovies.size(); i+=2) {
            winners.add(present(rangedMovies.get(i), rangedMovies.get(i+1))
                    .withInterval(interval));
        }
        return winners;
    }
    public List<PremiumRangeView> presentMax(List<Movie> entities) {
        int interval = MaxMovieIntervalComparator.getMaxInterval(entities);
        var orderedMovies = entities.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        var rangedMovies = filterMaxIntervalMovies(orderedMovies, interval);

        List<PremiumRangeView> winners = new ArrayList<>();
        for(int i = 0; i < rangedMovies.size(); i+=2) {
            winners.add(present(rangedMovies.get(i), rangedMovies.get(i+1))
                    .withInterval(interval));
        }
        return winners;
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
