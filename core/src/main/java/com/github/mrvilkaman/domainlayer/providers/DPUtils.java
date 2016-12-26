package com.github.mrvilkaman.domainlayer.providers;


import com.github.mrvilkaman.datalayer.api.response.BaseResponse;
import rx.Observable;
import rx.functions.Func1;

public interface DPUtils {

//	<T extends BaseResponse, R> Observable.Transformer<ResponseWrapper<T , BaseResponse>, R> handleAnswer(Func1<T, R> act);

	<T extends BaseResponse> Observable.Transformer<T, T> handleAnswer();

	<T> Observable.Transformer<T, T> handleError();

	<T> Func1<Throwable, Observable<T>> getThrowableObservableFunc1();
}
