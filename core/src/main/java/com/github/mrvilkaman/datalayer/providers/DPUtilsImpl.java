package com.github.mrvilkaman.datalayer.providers;


import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.mrvilkaman.datalayer.api.response.IBaseResponse;
import com.github.mrvilkaman.domainlayer.exceptions.InternetConnectionException;
import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerException;
import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.exceptions.UncheckedException;
import com.github.mrvilkaman.domainlayer.providers.DPUtils;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;
import rx.Observable;
import rx.annotations.Experimental;
import rx.functions.Func1;

public class DPUtilsImpl implements DPUtils {


	@Nullable private Processor processor;

	public DPUtilsImpl() {
		this(null);
	}

	public DPUtilsImpl(@Nullable Processor processor) {
		this.processor = processor;
	}

	@Override
	public <T extends IBaseResponse<R>, R> Observable.Transformer<T, R> handleAnswer() {
		return obs -> obs.onErrorResumeNext(getThrowableObservableFunc1()).concatMap(r -> {
			if (r.isSuccess()) {
				return Observable.just(r.getBody());
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
	protected <T> Observable<T> handleError(IBaseResponse response) {
		return Observable.error(getThrowable(response.getMessage(), response.getCode(), null));
	}

	@Override
	@NonNull
	public <T> Func1<Throwable, Observable<T>> getThrowableObservableFunc1() {
		return throwable -> {
			if (throwable instanceof HttpException) {
				HttpException httpException = (HttpException) throwable;
				Response response = httpException.response();

				return Observable.error(
						getThrowable(response.message(), response.code(), throwable));
			} else if (throwable instanceof IOException) {
				return Observable.error(new InternetConnectionException());
			} else if (throwable instanceof NetworkErrorException) {
				return Observable.error(new InternetConnectionException());
			}

			if (processor != null) {
				Throwable processorThrowable = processor.getThrowable(null, 0, throwable);
				if (processorThrowable != null) {
					return Observable.error(processorThrowable);
				}
			}
			return Observable.error(throwable);
		};
	}

	@NonNull
	protected Throwable getThrowable(String message, int code, Throwable throwable) {

		Throwable exception;

		if (processor != null) {
			Throwable processorThrowable = processor.getThrowable(message, code, throwable);
			if (processorThrowable != null) {
				return processorThrowable;
			}
		}

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
				exception = new ServerException(message, throwable);
				break;
			default:
				exception = new UncheckedException(message);
				break;
		}
		return exception;
	}

	@SuppressWarnings("WeakerAccess")
	@Experimental
	public interface Processor {
		// return notnull if throwable processed
		@Nullable
		Throwable getThrowable(@Nullable String message, int code, @Nullable Throwable throwable);
	}
}
