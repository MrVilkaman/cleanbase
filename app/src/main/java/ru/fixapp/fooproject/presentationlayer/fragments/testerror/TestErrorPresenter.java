package ru.fixapp.fooproject.presentationlayer.fragments.testerror;


import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ru.fixapp.fooproject.datalayer.subscriber.ViewSubscriber;
import ru.fixapp.fooproject.presentationlayer.fragments.core.BasePresenter;
import rx.Observable;

public class TestErrorPresenter extends BasePresenter<TestErrorView> {

	@Inject
	public TestErrorPresenter() {

	}

	@Override
	public void onViewAttached() {
		super.onViewAttached();
		Observable<Long> timer = Observable.timer(1, TimeUnit.SECONDS).concatMap(aLong -> Observable.error(new NoSuchElementException("qwer")));
		subscribeUI(timer, new ViewSubscriber<>(view()));
	}
}
