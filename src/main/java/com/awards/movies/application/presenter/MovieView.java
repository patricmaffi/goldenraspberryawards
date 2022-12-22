package com.awards.movies.application.presenter;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieView implements IView{
    private Long id;
    private Long year;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;

    public MovieView withID(Long id) {
        this.id = id;
        return this;
    }

    public MovieView withYear(Long year) {
        this.year = year;
        return this;
    }

    public MovieView withStudios(String studios) {
        this.studios = studios;
        return this;
    }

    public MovieView withWinner(Boolean winner) {
        this.winner = winner;
        return this;
    }

    public MovieView withProducers(String producers) {
        this.producers = producers;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Long getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getStudios() {
        return studios;
    }

    public String getProducers() {
        return producers;
    }

    public Boolean getWinner() {
        return winner;
    }
}
