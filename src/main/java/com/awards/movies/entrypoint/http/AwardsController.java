package com.awards.movies.entrypoint.http;

import com.awards.movies.application.command.GetMaxRangedWinnerProducer;
import com.awards.movies.application.command.GetMinRangedWinnerProducer;
import com.awards.movies.application.command_handler.GetMaxRangedWinnerProducerHandler;
import com.awards.movies.application.command_handler.GetMinRangedWinnerProducerHandler;
import com.awards.movies.application.presenter.IView;
import com.awards.movies.application.presenter.MoviePresenter;
import com.awards.movies.application.presenter.PremiumMinMaxWinnerView;
import com.awards.movies.application.presenter.PremiumRangePresenter;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.MovieRepository;
import com.awards.movies.domain.exception.MaxAwardsIntervalNotFound;
import com.awards.movies.domain.exception.MinAwardsIntervalNotFound;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/awards")
@AllArgsConstructor
public class AwardsController {
    MovieRepository repository;
    MoviePresenter presenter;
    PremiumRangePresenter premiumRangePresenter;
    GetMinRangedWinnerProducerHandler getMinRangedWinnerProducerHandler;
    GetMaxRangedWinnerProducerHandler getMaxRangedWinnerProducerHandler;

    @GetMapping()
    public ResponseEntity<List<IView>> winners() {
        List<Movie> entities = repository.getOnlyWinners();
        return ResponseEntity.status(HttpStatus.OK)
                .body(presenter.presentMany(entities));
    }
    @ExceptionHandler({ MinAwardsIntervalNotFound.class, MaxAwardsIntervalNotFound.class })
    @GetMapping("/interval")
    public ResponseEntity<IView> interval() {
        List<Movie> entities = repository.getOnlyWinners();
        var resultMin = getMinRangedWinnerProducerHandler
                .handle(new GetMinRangedWinnerProducer());
        var resultMax = getMaxRangedWinnerProducerHandler
                .handle(new GetMaxRangedWinnerProducer());

        var viewMin = resultMin.stream()
                .map(e -> premiumRangePresenter.presentMin(e))
                .collect(Collectors.toList());
        var viewMax = resultMax.stream()
                .map(e -> premiumRangePresenter.presentMax(e))
                .toList();

        var result = new PremiumMinMaxWinnerView();
        result.withMin(viewMin);
        result.withMax(viewMax);

        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}