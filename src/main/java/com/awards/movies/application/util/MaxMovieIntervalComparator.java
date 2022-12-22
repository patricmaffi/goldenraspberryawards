package com.awards.movies.application.util;

import com.awards.movies.domain.Movie;

import java.util.Comparator;
import java.util.List;

public class MaxMovieIntervalComparator implements Comparator<List<Movie>> {

    @Override
    public int compare(List<Movie> o1, List<Movie> o2) {
        var interval1 = getMaxInterval(o1);
        var interval2 = getMaxInterval(o2);

        return interval1 - interval2;
    }
    public static int getMaxInterval(List<Movie> movies){
        Long max = 0L;
        Long lastYear = 0L;
        var orderedMovies = movies.stream()
                .sorted((a,b) -> a.getMovieYear().compareTo(b.getMovieYear()))
                .toList();
        for (Movie e: orderedMovies) {
            if(lastYear == 0){
                lastYear = e.getMovieYear();
                continue;
            }
            var diff = e.getMovieYear() - lastYear;
            if(diff > max){
                max = diff;
            }
            lastYear = e.getMovieYear();
        }
        return max.intValue();
    }
}