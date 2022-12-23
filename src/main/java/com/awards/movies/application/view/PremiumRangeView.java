package com.awards.movies.application.view;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PremiumRangeView implements IView{
    private String producer;
    private Integer interval;
    private Long previousWin;
    private Long followingWin;

    public PremiumRangeView withProducer(String producer) {
        this.producer = producer;
        return this;
    }
    public PremiumRangeView withInterval(Integer interval) {
        this.interval = interval;
        return this;
    }

    public PremiumRangeView withPreviousWin(Long previousWin) {
        this.previousWin = previousWin;
        return this;
    }

    public PremiumRangeView withFollowingWin(Long followingWin) {
        this.followingWin = followingWin;
        return this;
    }

    public String getProducer() {
        return producer;
    }

    public Integer getInterval() {
        return interval;
    }

    public Long getPreviousWin() {
        return previousWin;
    }

    public Long getFollowingWin() {
        return followingWin;
    }
}
