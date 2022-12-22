package com.awards.movies.application.command_handler;

import com.awards.movies.application.command.GroupingMoviesByProducer;
import com.awards.movies.domain.Movie;
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
public class GroupingMoviesByProducerHandlerTests {


    @InjectMocks
    GroupingMoviesByProducerHandler handler;
    @Test
    void testGroupingMovieCollection(){
        var list = new ArrayList<Movie>();
        list.add(Mockito.mock(Movie.class));
        list.add(Mockito.mock(Movie.class));
        GroupingMoviesByProducer g = new GroupingMoviesByProducer(list);
        when(list.get(0).getProducers()).thenReturn("Bo Derek, Julio");
        when(list.get(1).getProducers()).thenReturn("Mario, Julio");
        System.out.println(handler.handle(g));

        String contract = "{Mario=[Movie(id=0, movieYear=0, title=null, studios=null, producers=Mario, winner=false)], Julio=[Movie(id=0, movieYear=0, title=null, studios=null, producers=Julio, winner=false), Movie(id=0, movieYear=0, title=null, studios=null, producers=Julio, winner=false)], Bo Derek=[Movie(id=0, movieYear=0, title=null, studios=null, producers=Bo Derek, winner=false)]}";
        Assertions.assertEquals(contract, handler.handle(g).toString());
    }
}
