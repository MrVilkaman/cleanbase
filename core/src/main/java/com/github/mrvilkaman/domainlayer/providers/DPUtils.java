package com.github.mrvilkaman.domainlayer.providers;


import com.github.mrvilkaman.datalayer.api.response.IBaseResponse;
import rx.Observable;
import rx.functions.Func1;

public interface DPUtils {

	<T extends IBaseResponse<R>, R> Observable.Transformer<T, R> handleAnswer();

	<T> Observable.Transformer<T, T> handleError();

	<T> Func1<Throwable, Observable<T>> getThrowableObservableFunc1();
}
