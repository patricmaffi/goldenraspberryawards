package com.awards.movies.application.command_handler;

import java.io.IOException;

public interface ICommandHandler<T,U> {
    public T handle(U command) throws Exception;
}
