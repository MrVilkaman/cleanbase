package com.github.mrvilkaman.domainlayer.providers;


import com.github.mrvilkaman.datalayer.api.response.IBaseResponse;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;

public interface DPUtils {
	//// TODO: 02.08.17 Added maybe\Compliteble\Flowable\Observable

	<T extends IBaseResponse<R>, R> SingleTransformer<T, R> handleAnswer();

	<T> SingleTransformer<T, T> handleError();

	<T> Function<Throwable, Single<T>> getThrowableObservableFunc1();
}
