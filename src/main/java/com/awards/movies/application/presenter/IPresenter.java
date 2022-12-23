package com.awards.movies.application.presenter;

import com.awards.movies.application.view.IView;

import java.util.List;

public interface IPresenter<T> {
    IView present(T entity) throws Exception;
    List<IView> presentMany(List<T> entities);
}
