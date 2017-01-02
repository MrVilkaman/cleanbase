package com.github.mrvilkaman.datalayer.providers;


import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import com.github.mrvilkaman.datalayer.api.response.BaseResponse;
import com.github.mrvilkaman.domainlayer.exceptions.InternetConnectionException;
import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerException;
import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.exceptions.UncheckedException;
import com.github.mrvilkaman.domainlayer.providers.DPUtils;
import rx.Observable;
import rx.functions.Func1;

public class DPUtilsImpl implements DPUtils {

	@Override
	public <T extends BaseResponse> Observable.Transformer<T, T> handleAnswer() {
		return obs -> obs
				.onErrorResumeNext(getThrowableObservableFunc1())
				.concatMap(r -> {
					if (r.isSuccess()) {
						return Observable.just(r);
					} else {
						return handleError(r);
					}
				});
	}

	@Override
	public <T> Observable.Transformer<T, T> handleError() {
		return obs -> obs.onErrorResumeNext(getThrowableObservableFunc1());
	}

	@NonNull
	private <T> Observable<T> handleError(BaseResponse response) {
		Throwable exception = getThrowable(response.getMessage(), response.getCode(), null);
		return Observable.error(exception);
	}

	@Override
	@NonNull
	public <T> Func1<Throwable, Observable<T>> getThrowableObservableFunc1() {
		return throwable -> {
			if (throwable instanceof HttpException) {
				HttpException httpException = (HttpException) throwable;
				Response response = httpException.response();
				return Observable.error(getThrowable(response.message(), response.code(), throwable));
//					RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
			} else if (throwable instanceof IOException) {
				return Observable.error(new InternetConnectionException());
			} else if (throwable instanceof NetworkErrorException) {
				return Observable.error(new InternetConnectionException());
			}
			return Observable.error(throwable);
		};
	}

	@NonNull
	private Throwable getThrowable(String message, int code, Throwable throwable) {
		Throwable exception;
		switch (code) {
			case 404:
				exception = new NotFoundException();
				break;
			case 401:
				exception = new UnauthorizedException();
				break;
			case 500:
			case 501:
			case 502:
			case 503:
			case 504:
				exception = new ServerException(throwable);
				break;
			default:
				exception = new UncheckedException(message);
				break;
		}
		return exception;
	}
}
