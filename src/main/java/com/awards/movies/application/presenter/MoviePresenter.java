package com.awards.movies.application.presenter;

import com.awards.movies.application.view.IView;
import com.awards.movies.application.view.MovieView;
import com.awards.movies.domain.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoviePresenter implements IPresenter<Movie>{

    @Override
    public IView present(Movie entity) {
        return new MovieView()
                .withYear(entity.getMovieYear())
                .withProducers(entity.getProducers())
                .withStudios(entity.getStudios())
                .withWinner(entity.getWinner());
    }

    @Override
    public List<IView> presentMany(List<Movie> entities) {
        return entities.stream().map(this::present).collect(Collectors.toList());
    }
}
