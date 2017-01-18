package com.github.mrvilkaman.datalayer.providers;

import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import net.jokubasdargis.rxbus.Bus;

import org.junit.Test;
import org.mockito.Mock;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class GlobalSubscriptionManagerImplTest extends BaseTestCase {

	@Mock Bus bus;
	private GlobalSubscriptionManager manager;

	@Override
	public void init() {
		manager = new GlobalSubscriptionManagerImpl(bus);
	}

	@Test
	public void testSubscribe_success() {
		// Arrange
		PublishSubject<String> subject = PublishSubject.create();
		TestSubscriber<String> client = new TestSubscriber<>();
		Observable<String> stringObservable = subject.asObservable()
				.doOnNext((t) -> client.onNext(t));

		// Act
		subject.onNext("qwer1");
		manager.subscribe(stringObservable);
		subject.onNext("qwer2");

		// Assert
		client.assertValueCount(1);
		client.assertValue("qwer2");
	}

	@Test
	public void testSubscribe_error() {
		// Act
		UnauthorizedException exception = new UnauthorizedException();
		manager.subscribe(Observable.error(exception));

		// Assert
		verify(bus).publish(eq(GlobalBusQuery.GLOBAL_ERRORS), eq(exception));
	}

	@Test
	public void testSubscribeWithResult_error_inUI() {
		// Arrange
		UnauthorizedException exception = new UnauthorizedException();
		TestSubscriber<Object> subscriber = new TestSubscriber<>();
		PublishSubject<Object> subject = PublishSubject.create();
		// Act
		Observable<Object> objectObservable = manager.subscribeWithResult(subject.asObservable());
		objectObservable.subscribe(subscriber);
		subject.onError(exception);

		// Assert
		verify(bus, never()).publish(eq(GlobalBusQuery.GLOBAL_ERRORS), eq(exception));
		subscriber.assertError(UnauthorizedException.class);
	}


	@Test
	public void testSubscribeWithResult_error_inBg() {
		// Arrange
		UnauthorizedException exception = new UnauthorizedException();
		TestSubscriber<Object> subscriber = new TestSubscriber<>();
		PublishSubject<Object> subject = PublishSubject.create();

		// Act
		Observable<Object> objectObservable = manager.subscribeWithResult(subject.asObservable());
		objectObservable.subscribe(subscriber)
				.unsubscribe();
		subject.onError(exception);

		// Assert
		verify(bus).publish(eq(GlobalBusQuery.GLOBAL_ERRORS), eq(exception));
		subscriber.assertNoErrors();
		subscriber.assertUnsubscribed();
	}

	@Test
	public void testSubscribeWithResult_nextAndCompletedWithResponse() {
		// Arrange
		TestSubscriber<String> subscriber = new TestSubscriber<>();
		TestSubscriber<String> client = new TestSubscriber<>();
		PublishSubject<String> subject = PublishSubject.create();
		Observable<String> stringObservable = subject.asObservable()
				.doOnNext(client::onNext)
				.doOnCompleted(client::onCompleted);

		// Act
		Observable<String> objectObservable = manager.subscribeWithResult(stringObservable.asObservable());
		objectObservable.subscribe(subscriber);
		subject.onNext("qwer");
		subject.onCompleted();

		// Assert
		subscriber.assertValue("qwer");
		subscriber.assertCompleted();
		client.assertValue("qwer");
		client.assertCompleted();
	}


	@Test
	public void testSubscribeWithResult_nextAndCompleted_NoUI() {
		// Arrange
		TestSubscriber<String> subscriber = new TestSubscriber<>();
		TestSubscriber<String> client = new TestSubscriber<>();
		PublishSubject<String> subject = PublishSubject.create();
		Observable<String> stringObservable = subject.asObservable()
				.doOnNext(client::onNext)
				.doOnCompleted(client::onCompleted);

		// Act
		Observable<String> objectObservable = manager.subscribeWithResult(stringObservable.asObservable());
		objectObservable.subscribe(subscriber).unsubscribe();
		subject.onNext("qwer");
		subject.onCompleted();

		// Assert
		subscriber.assertNoValues();
		subscriber.assertNotCompleted();
		subscriber.assertUnsubscribed();
		client.assertValue("qwer");
		client.assertCompleted();
	}

}