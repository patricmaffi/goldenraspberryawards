package com.awards.movies.entrypoint.http;

import com.awards.movies.application.command.GetPremiumMinMaxWinner;
import com.awards.movies.application.command_handler.GetPremiumMinMaxWinnerHandler;
import com.awards.movies.application.exception.MaxAwardsIntervalNotFound;
import com.awards.movies.application.exception.MinAwardsIntervalNotFound;
import com.awards.movies.application.presenter.IView;
import com.awards.movies.application.presenter.MoviePresenter;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/awards")
@AllArgsConstructor
public class AwardsController {
    MovieRepository repository;
    MoviePresenter presenter;
    GetPremiumMinMaxWinnerHandler getPremiumMinMaxWinnerHandler;

    @GetMapping()
    public ResponseEntity<List<IView>> winners() {
        List<Movie> entities = repository.getOnlyWinners();
        return ResponseEntity.status(HttpStatus.OK)
                .body(presenter.presentMany(entities));
    }
    @ExceptionHandler({ MinAwardsIntervalNotFound.class, MaxAwardsIntervalNotFound.class })
    @GetMapping("/interval")
    public ResponseEntity<IView> interval() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(getPremiumMinMaxWinnerHandler.handle(new GetPremiumMinMaxWinner()));
    }
}