package ru.fixapp.fooproject.presentationlayer.fragments.core;


import ru.fixapp.fooproject.presentationlayer.resolution.UIResolver;

public interface BaseView extends UIResolver {

	BasePresenter getPresenter();

	void showProgress();

	void hideProgress();

    void handleError(Throwable throwable);

}