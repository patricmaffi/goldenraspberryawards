package com.awards.movies.entrypoint.http;

import com.awards.movies.application.command.AddMovieToDatabase;
import com.awards.movies.application.command_handler.AddMovieToDatabaseHandler;
import com.awards.movies.application.dto.MovieDto;
import com.awards.movies.application.view.IView;
import com.awards.movies.application.presenter.MoviePresenter;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.MovieRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
public class MovieController {
    MovieRepository repository;
    MoviePresenter presenter;
    AddMovieToDatabaseHandler addMovieToDatabaseHandler;

    @GetMapping()
    public ResponseEntity<List<IView>> getMovies() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(presenter.presentMany(repository.findAll()));
    }

    @PostMapping()
    public ResponseEntity<IView> insertMovie(@Valid @RequestBody MovieDto movie) {
        Movie savedMovie = addMovieToDatabaseHandler.handle(new AddMovieToDatabase(movie));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(presenter.present(savedMovie));
    }
}