package com.awards.movies.application.command_handler;

import com.awards.movies.application.command.GetMaxRangedWinnerProducer;
import com.awards.movies.application.command.GetMinRangedWinnerProducer;
import com.awards.movies.application.command.GroupingMoviesByProducer;
import com.awards.movies.domain.Movie;
import com.awards.movies.domain.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RangedWinnerProducerHandlerTests {

    @InjectMocks
    GetMaxRangedWinnerProducerHandler maxHandler;
    @InjectMocks
    GetMinRangedWinnerProducerHandler minHandler;
    @InjectMocks
    GroupingMoviesByProducerHandler gpHandler;
    @Mock
    GroupingMoviesByProducerHandler gpHandler1;
    @Mock
    MovieRepository repository;

    @Test
    void testMaxRangeMovieCollection(){
        var list = new ArrayList<Movie>();
        list.add(Mockito.mock(Movie.class));
        list.add(Mockito.mock(Movie.class));
        list.add(Mockito.mock(Movie.class));

        when(repository.getOnlyWinners()).thenReturn(list);
        when(list.get(0).getProducers()).thenReturn("Bo Derek, Julio");
        when(list.get(1).getProducers()).thenReturn("Mario, Julio");
        when(list.get(2).getProducers()).thenReturn("Mario, Joao");
        when(list.get(0).getWinner()).thenReturn(Boolean.TRUE);
        when(list.get(1).getWinner()).thenReturn(Boolean.TRUE);
        when(list.get(2).getWinner()).thenReturn(Boolean.TRUE);
        when(list.get(0).getMovieYear()).thenReturn(2001L);
        when(list.get(1).getMovieYear()).thenReturn(2002L);
        when(list.get(2).getMovieYear()).thenReturn(2010L);

        var x = new GroupingMoviesByProducer(list);
        var t = gpHandler.handle(x);
        when(gpHandler1.handle(x)).thenReturn(t);

        GetMaxRangedWinnerProducer cmd = new GetMaxRangedWinnerProducer();
        System.out.println(maxHandler.handle(cmd));

        String contract = "[[Movie(id=0, movieYear=2002, title=null, studios=null, producers=Mario, winner=true), Movie(id=0, movieYear=2010, title=null, studios=null, producers=Mario, winner=true)]]";
        Assertions.assertEquals(contract, maxHandler.handle(cmd).toString());

        contract = "[[Movie(id=0, movieYear=2001, title=null, studios=null, producers=Julio, winner=true), Movie(id=0, movieYear=2002, title=null, studios=null, producers=Julio, winner=true)]]";
        System.out.println(minHandler.handle(new GetMinRangedWinnerProducer()));
        Assertions.assertEquals(contract, minHandler.handle(new GetMinRangedWinnerProducer()).toString());

    }
}
