package com.github.mrvilkaman.datalayer.tools;

import com.github.mrvilkaman.domainlayer.exceptions.ServerException;
import com.github.mrvilkaman.domainlayer.models.DataErrorWrapper;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;


public class RxLoadWrapperHolderTest extends BaseTestCase {

	private RxLoadWrapperHolder holder;

	@Override
	public void init() {
	}

	@Test
	public void testSingleRequest() {
		// Arrange
		holder = RxLoadWrapperHolder.create();
		TestScheduler scheduler = new TestScheduler();

		// Act


		Observable<String> test = Observable.just("Test")
				.delay(2, TimeUnit.SECONDS, scheduler)
				.compose(holder.bindToLoad())
				.subscribeOn(scheduler);

		TestSubscriber<DataErrorWrapper<String>> subscriber = new TestSubscriber<>();

		Observable.just(0, 1,2).concatMap(integer -> test.compose(holder.modifySingle())).subscribe
				(subscriber);

		scheduler.advanceTimeBy(10, TimeUnit.SECONDS);

		// Assert
		subscriber.assertValueCount(6);
		assertMy(subscriber, 0);
		assertMy(subscriber, 2);
		assertMy(subscriber, 4);
	}

	public void assertMy(TestSubscriber<DataErrorWrapper<String>> subscriber, int i) {
		List<DataErrorWrapper<String>> onNextEvents = subscriber.getOnNextEvents();

		DataErrorWrapper<String> stringDataErrorWrapper = onNextEvents.get(i + 0);
		Assert.assertTrue(stringDataErrorWrapper.isProgress());
		Assert.assertFalse(stringDataErrorWrapper.isSuccess());
		Assert.assertFalse(stringDataErrorWrapper.isError());

		DataErrorWrapper<String> stringDataErrorWrapper2 = onNextEvents.get(i + 1);
		Assert.assertFalse(stringDataErrorWrapper2.isProgress());
		Assert.assertTrue(stringDataErrorWrapper2.isSuccess());
		Assert.assertFalse(stringDataErrorWrapper2.isError());
		Assert.assertEquals("Test", stringDataErrorWrapper2.getValue());
	}

	@Test()
	public void testHandle() {
		// Arrange
		holder = RxLoadWrapperHolder.create();
		TestSubscriber<DataErrorWrapper<Object>> subscriber = new TestSubscriber<>();

		// Act
		holder.modifyStream(PublishSubject.create()).subscribe(subscriber);

		Observable.error(new ServerException())
				.compose(holder.bindToLoad())
				.onErrorResumeNext(throwable -> Observable.empty())
				.subscribe();

		// Assert
		// 1 - начальное значение false
		// 2 - начало загрузки true
		// 3 - терменальное состояние (в данном случае ошибка) false
		// 4 - Сама ошибка  false
		subscriber.assertValueCount(4);
		List<DataErrorWrapper<Object>> onNextEvents = subscriber.getOnNextEvents();

		Assertions.assertThat(onNextEvents.get(0).isProgress()).isFalse();
		Assertions.assertThat(onNextEvents.get(1).isProgress()).isTrue();
		Assertions.assertThat(onNextEvents.get(2).isProgress()).isFalse();

		DataErrorWrapper<Object> objectDataErrorWrapper = onNextEvents.get(3);
		Assertions.assertThat(objectDataErrorWrapper.isError()).isTrue();
		Assertions.assertThat(objectDataErrorWrapper.getThrowable())
				.isOfAnyClassIn(ServerException.class);
	}

	@Test()
	public void testHandle_errors() {
		// Arrange
		holder = RxLoadWrapperHolder.create();
		TestSubscriber<DataErrorWrapper<Object>> subscriber = new TestSubscriber<>();

		// Act
		holder.modifyStream(PublishSubject.create()).subscribe(subscriber);

		Observable.error(new ServerException())
				.compose(holder.bindToLoad())
				.onErrorResumeNext(throwable -> Observable.empty())
				.subscribe();
		Observable.error(new IOException())
				.compose(holder.bindToLoad())
				.onErrorResumeNext(throwable -> Observable.empty())
				.subscribe();

		// Assert

		// 1 - начальное значение false
		// 2 - начало загрузки true
		// 3 - терменальное состояние (в данном случае ошибка) false
		// 4 - Сама ошибка  false
		// 5 - начало загрузки true
		// 6 - терменальное состояние (в данном случае ошибка) false
		// 7 - Сама ошибка  false
		subscriber.assertValueCount(7);
		List<DataErrorWrapper<Object>> onNextEvents = subscriber.getOnNextEvents();

		Assertions.assertThat(onNextEvents.get(0).isProgress()).isFalse();
		Assertions.assertThat(onNextEvents.get(1).isProgress()).isTrue();
		Assertions.assertThat(onNextEvents.get(2).isProgress()).isFalse();

		DataErrorWrapper<Object> objectDataErrorWrapper = onNextEvents.get(3);
		Assertions.assertThat(objectDataErrorWrapper.isError()).isTrue();
		Assertions.assertThat(objectDataErrorWrapper.getThrowable())
				.isOfAnyClassIn(ServerException.class);

		Assertions.assertThat(onNextEvents.get(4).isProgress()).isTrue();
		Assertions.assertThat(onNextEvents.get(5).isProgress()).isFalse();

		DataErrorWrapper<Object> objectDataErrorWrapper2 = onNextEvents.get(6);

		Assertions.assertThat(objectDataErrorWrapper2.isError()).isTrue();
		Assertions.assertThat(objectDataErrorWrapper2.getThrowable())
				.isOfAnyClassIn(IOException.class);
	}

}