package com.github.mrvilkaman.domainlayer.providers;

import android.accounts.NetworkErrorException;

import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import com.github.mrvilkaman.testsutils.BaseTestCase;
import com.github.mrvilkaman.datalayer.api.response.BaseResponse;
import com.github.mrvilkaman.datalayer.providers.DPUtilsImpl;
import com.github.mrvilkaman.domainlayer.exceptions.InternetConnectionException;
import com.github.mrvilkaman.domainlayer.exceptions.NotFoundException;
import com.github.mrvilkaman.domainlayer.exceptions.ServerException;
import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.exceptions.UncheckedException;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static rx.Observable.just;


public class DPUtilsTest extends BaseTestCase {

	private DPUtilsImpl dpUtils;

	@Override
	public void init() {
		dpUtils = new DPUtilsImpl();
	}

	@Test
	public void testHandleAnswer() {
		// Arrange
		BaseResponse response = new BaseResponse(200, "");
		TestSubscriber<BaseResponse> subscriber = new TestSubscriber<>();

		// Act
		just(response)
				.compose(dpUtils.handleAnswer())
				.subscribe(subscriber);

		// Assert
		subscriber.assertValue(response);
		subscriber.assertValueCount(1);
		subscriber.assertCompleted();
		subscriber.assertNoErrors();
	}

	@Test
	public void testHandle404() {
		errorResponseTest(404, NotFoundException.class);
	}

	@Test
	public void testHandle404Exception() {
		Response<Object> error = Response.error(404, mock(ResponseBody.class));
		exceptionErrorText(new HttpException(error), NotFoundException.class);
	}

	@Test
	public void testHandle401() {
		errorResponseTest(401, UnauthorizedException.class);
	}

	@Test
	public void testHandle401Exception() {
		Response<Object> error = Response.error(401, mock(ResponseBody.class));
		exceptionErrorText(new HttpException(error), UnauthorizedException.class);
	}

	@Test
	public void testHandleError() {
		errorResponseTest(499, UncheckedException.class);
	}

	@Test
	public void testHandleErrorException() {
		Response<Object> error = Response.error(499, mock(ResponseBody.class));
		exceptionErrorText(new HttpException(error), UncheckedException.class);
	}


	@Test
	public void testHandleError500() {
		errorResponseTest(500, ServerException.class);
	}

	@Test
	public void testHandleErrorException500() {
		Response<Object> error = Response.error(500, mock(ResponseBody.class));
		exceptionErrorText(new HttpException(error), ServerException.class);
	}

	@Test
	public void testHandleError501() {
		errorResponseTest(501, ServerException.class);
	}

	@Test
	public void testHandleError503() {
		errorResponseTest(503, ServerException.class);
	}

	@Test
	public void testHandleError502() {
		errorResponseTest(502, ServerException.class);
	}

	@Test
	public void testHandleLowInternetException() {
		exceptionErrorText(new SocketTimeoutException(), InternetConnectionException.class);
	}

	@Test
	public void testHandleNoInternetException() {
		exceptionErrorText(new UnknownHostException(), InternetConnectionException.class);
	}

	@Test
	public void testHandleIOException() {
		exceptionErrorText(new IOException(), InternetConnectionException.class);
	}

	@Test
	public void testHandleNetworkErrorException() {
		exceptionErrorText(new NetworkErrorException(), InternetConnectionException.class);
	}

	@Test
	public void testHandleAnotherException() {
		exceptionErrorText(new RuntimeException(), RuntimeException.class);
	}

	private void exceptionErrorText(Throwable exception, Class<? extends Throwable> clazz) {
		// Arrange

		TestSubscriber<Object> subscriber = new TestSubscriber<>();

		// Act
		Observable.<BaseResponse>error(exception)
				.compose(dpUtils.handleAnswer())
				.subscribe(subscriber);

		// Assert
		subscriber.assertNoValues();
		subscriber.assertNotCompleted();
		subscriber.assertError(clazz);
	}

	private <T extends Throwable> void errorResponseTest(int code, Class<T> exceptionClass) {
		// Arrange
		TestSubscriber<Object> subscriber = new TestSubscriber<>();

		// Act
		just(new BaseResponse(code, "qwer"))
				.compose(dpUtils.handleAnswer())
				.subscribe(subscriber);

		// Assert
		subscriber.assertNoValues();
		subscriber.assertNotCompleted();
		subscriber.assertError(exceptionClass);
	}
}