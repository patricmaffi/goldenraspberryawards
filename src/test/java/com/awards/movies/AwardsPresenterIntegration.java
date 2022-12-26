package com.awards.movies;

import com.awards.movies.application.command.GetMaxRangedWinnerProducer;
import com.awards.movies.application.command.GetMinRangedWinnerProducer;
import com.awards.movies.application.command.GroupingMoviesByProducer;
import com.awards.movies.application.command_handler.GetMaxRangedWinnerProducerHandler;
import com.awards.movies.application.command_handler.GetMinRangedWinnerProducerHandler;
import com.awards.movies.application.command_handler.GroupingMoviesByProducerHandler;
import com.awards.movies.application.view.PremiumMinMaxWinnerView;
import com.awards.movies.application.presenter.PremiumRangePresenter;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.repository.MovieRepository;
import com.awards.movies.application.exception.MaxAwardsIntervalNotFound;
import com.awards.movies.application.exception.MinAwardsIntervalNotFound;
import com.awards.movies.domain.factory.MovieFactoryByCsvLine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AwardsPresenterIntegration {

    @InjectMocks
    MovieFactoryByCsvLine factory;
    @InjectMocks
    GetMaxRangedWinnerProducerHandler maxHandler;
    @InjectMocks
    GetMinRangedWinnerProducerHandler minHandler;
    @InjectMocks
    GroupingMoviesByProducerHandler gpHandler;
    @Mock
    GroupingMoviesByProducerHandler gpHandlerMock;
    @Mock
    MovieRepository repository;
    @Test
    public void IntegrationAwardIntervalTest() throws JsonProcessingException {
        var list = new ArrayList<Movie>();
        list.add(factory.build("1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes"));
        list.add(factory.build("1982;The Pirate Movie;20th Century Fox;David Joseph;"));
        list.add(factory.build("1983;The Lonely Lady;Universal Studios;Robert R. Weston;yes"));


        String contract = "[Movie(id=null, movieYear=1980, title=Can't Stop the Music, studios=Associated Film Distribution, producers=Allan Carr, winner=true), Movie(id=null, movieYear=1982, title=The Pirate Movie, studios=20th Century Fox, producers=David Joseph, winner=false), Movie(id=null, movieYear=1983, title=The Lonely Lady, studios=Universal Studios, producers=Robert R. Weston, winner=true)]";
        Assertions.assertEquals(contract, list.toString());

        MaxAwardsIntervalNotFound exception =
                Assertions.assertThrows(MaxAwardsIntervalNotFound.class, () ->{
            maxHandler.handle(new GetMaxRangedWinnerProducer());
        });

        Assertions.assertEquals(exception.getMessage(), "No maximum producer award range found");

        MinAwardsIntervalNotFound minException =
                Assertions.assertThrows(MinAwardsIntervalNotFound.class, () ->{
            minHandler.handle(new GetMinRangedWinnerProducer());
        });
        Assertions.assertEquals(minException.getMessage(), "No minimum producer award range found");

        list.add(factory.build("1984;The Lonely Lady;Universal Studios;Robert R. Weston;yes"));
        list.add(factory.build("1994;The Lonely Lady;Universal Studios;David Joseph;yes"));

        when(repository.getOnlyWinners()).thenReturn(list);
        var x = new GroupingMoviesByProducer(list);
        var t = gpHandler.handle(x);
        when(gpHandlerMock.handle(x)).thenReturn(t);

        List<List<Movie>> resultMax = maxHandler.handle(new GetMaxRangedWinnerProducer());
        List<List<Movie>> resultMin = minHandler.handle(new GetMinRangedWinnerProducer());

        PremiumRangePresenter premiumRangePresenter = new PremiumRangePresenter();
        var viewMin = resultMin.stream()
                .map(e -> premiumRangePresenter.presentMin(e))
                .collect(Collectors.toList());
        var viewMax = resultMax.stream()
                .map(e -> premiumRangePresenter.presentMax(e))
                .toList();

        var result = new PremiumMinMaxWinnerView();
        result.withMin(viewMin);
        result.withMax(viewMax.stream().flatMap(e -> e.stream()).toList());

        ObjectMapper mapper = new ObjectMapper();

        contract = "{\"min\":[{\"producer\":\"Robert R. Weston\",\"interval\":1,\"previousWin\":1983,\"followingWin\":1984}],\"max\":[{\"producer\":\"David Joseph\",\"interval\":12,\"previousWin\":1982,\"followingWin\":1994}]}";
        Assertions.assertEquals(contract, mapper.writeValueAsString(result));
    }
}
