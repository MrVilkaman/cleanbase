package com.github.mrvilkaman.datalayer.providers;

import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.testsutils.BaseTestCase;

import net.jokubasdargis.rxbus.Bus;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class GlobalSubscriptionManagerImplTest extends BaseTestCase {

	@Mock Bus bus;
	private GlobalSubscriptionManager manager;

	@Override
	public void init() {
		manager = new GlobalSubscriptionManagerImpl(bus);
	}

	@Ignore
	@Test
	public void testSubscribe_success() {
		// Arrange
		PublishSubject<String> subject = PublishSubject.create();
		TestSubscriber<String> client = new TestSubscriber<>();
		Observable<String> stringObservable = subject.asObservable().doOnNext(client::onNext);

		// Act
		subject.onNext("qwer1");
		manager.subscribe(stringObservable);
		subject.onNext("qwer2");

		// Assert
		client.assertValueCount(1);
		client.assertValue("qwer2");
	}

	@Ignore
	@Test
	public void testSubscribe_error() {
		// Act
		UnauthorizedException exception = new UnauthorizedException();
		manager.subscribe(Observable.error(exception));

		// Assert
		verify(bus).publish(eq(GlobalBusQuery.GLOBAL_ERRORS), eq(exception));
	}

	@Ignore
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

	@Ignore
	@Test
	public void testSubscribeWithResult_error_inBg() {
		// Arrange
		UnauthorizedException exception = new UnauthorizedException();
		TestSubscriber<Object> subscriber = new TestSubscriber<>();
		PublishSubject<Object> subject = PublishSubject.create();

		// Act
		Observable<Object> objectObservable = manager.subscribeWithResult(subject.asObservable());
		objectObservable.subscribe(subscriber).unsubscribe();
		subject.onError(exception);

		// Assert
		verify(bus).publish(eq(GlobalBusQuery.GLOBAL_ERRORS), eq(exception));
		subscriber.assertNoErrors();
		subscriber.assertUnsubscribed();
	}

	@Ignore
	@Test
	public void testSubscribeWithResult_nextAndCompletedWithResponse() {
		// Arrange
		TestSubscriber<String> subscriber = new TestSubscriber<>();
		TestSubscriber<String> client = new TestSubscriber<>();
		PublishSubject<String> subject = PublishSubject.create();
		Observable<String> stringObservable =
				subject.asObservable().doOnNext(client::onNext).doOnCompleted(client::onCompleted);

		// Act
		Observable<String> objectObservable =
				manager.subscribeWithResult(stringObservable.asObservable());
		objectObservable.subscribe(subscriber);
		subject.onNext("qwer");
		subject.onCompleted();

		// Assert
		subscriber.assertValue("qwer");
		subscriber.assertCompleted();
		client.assertValue("qwer");
		client.assertCompleted();
	}


	@Ignore
	@Test
	public void testSubscribeWithResult_nextAndCompleted_NoUI() {
		// Arrange
		TestSubscriber<String> subscriber = new TestSubscriber<>();
		TestSubscriber<String> client = new TestSubscriber<>();
		PublishSubject<String> subject = PublishSubject.create();
		Observable<String> stringObservable =
				subject.asObservable().doOnNext(client::onNext).doOnCompleted(client::onCompleted);

		// Act
		Observable<String> objectObservable =
				manager.subscribeWithResult(stringObservable.asObservable());
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

	@Ignore
	@Test
	public void testsubscribeSingle_twoSubscribe_oneDate() {
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		when(mock.getNext()).thenReturn("1", "2", "3");
		TestScheduler scheduler = new TestScheduler();

		Observable<String> stringObservable = Observable.fromCallable(mock::getNext)
				.delay(1, TimeUnit.SECONDS, scheduler);

		// Act
		TestSubscriber<String> testSubscriber1 = new TestSubscriber<>();
		TestSubscriber<String> testSubscriber2 = new TestSubscriber<>();
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber1);
		testSubscriber1.assertValueCount(0);
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testSubscriber1.assertValueCount(1);
		testSubscriber1.assertValue("1");
		testSubscriber2.assertValueCount(1);
		testSubscriber2.assertValue("1");
		verify(mock).getNext();
	}

	@Test
	public void testsubscribeSingle_twoSubscribe_oneDateSequnce_withInitDelay() {
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		TestScheduler scheduler = new TestScheduler();
		when(mock.getNext()).thenReturn("1", "2", "3");

		Observable<String> stringObservable =
				Observable.zip(
				Observable.just(mock.getNext(), mock.getNext(), mock.getNext())
						.toList()
						.concatMap(Observable::from),
						Observable.interval(1, TimeUnit.SECONDS, scheduler),
						(s, aLong) -> s);

		// Act
		TestSubscriber<String> testSubscriber1 = new TestSubscriber<>();
		TestSubscriber<String> testSubscriber2 = new TestSubscriber<>();
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber1);
		scheduler.advanceTimeTo(2, TimeUnit.SECONDS);
		testSubscriber1.assertValues("1", "2");

		manager.createCached("qwer", stringObservable).subscribe(testSubscriber2);

		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testSubscriber1.assertValues("1", "2", "3");
		testSubscriber2.assertValues("1", "2", "3");
		verify(mock, times(3)).getNext();

	}

	@Test
	public void testsubscribeSingle_twoSubscribe_oneError() {
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		TestScheduler scheduler = new TestScheduler();
		Exception exception1 = Mockito.mock(Exception.class);
		Exception exception2 = Mockito.mock(Exception.class);
		when(mock.getNextException()).thenReturn(exception1,exception2);

		Observable<String> stringObservable = Observable.fromCallable(mock::getNextException)
				.delay(1, TimeUnit.SECONDS, scheduler)
				.concatMap(Observable::error);

		// Act
		TestSubscriber<String> testSubscriber1 = new TestSubscriber<>();
		TestSubscriber<String> testSubscriber2 = new TestSubscriber<>();
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber1);
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testSubscriber1.assertError(exception1);
		testSubscriber2.assertError(exception1);
		verify(mock).getNextException();
	}

	@Test
	public void testsubscribeSingle_newInstanceAfterFinish() {
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		when(mock.getNext()).thenReturn("1", "2", "3");
		TestScheduler scheduler = new TestScheduler();

		Observable<String> stringObservable = Observable.fromCallable(mock::getNext)
				.delay(1, TimeUnit.SECONDS, scheduler);

		// Act
		TestSubscriber<String> testSubscriber1 = new TestSubscriber<>();
		TestSubscriber<String> testSubscriber2 = new TestSubscriber<>();
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber1);
		testSubscriber1.assertValueCount(0);
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		TestSubscriber<String> testSubscriber3 = new TestSubscriber<>();
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber3);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testSubscriber1.assertValueCount(1);
		testSubscriber1.assertValue("1");
		testSubscriber2.assertValueCount(1);
		testSubscriber2.assertValue("1");
		testSubscriber3.assertValueCount(1);
		testSubscriber3.assertValue("2");
		verify(mock, times(2)).getNext();
	}

	@Test
	public void testsubscribeSingle_buildEquealsLogicDiffObserver(){
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		when(mock.getNext()).thenReturn("1", "2", "3");
		TestScheduler scheduler = new TestScheduler();

		Observable<String> stringObservable = Observable.fromCallable(mock::getNext)
				.delay(1, TimeUnit.SECONDS, scheduler);
		Observable<String> stringObservable2 = Observable.fromCallable(mock::getNext)
				.delay(1, TimeUnit.SECONDS, scheduler);

		// Act
		TestSubscriber<String> testSubscriber1 = new TestSubscriber<>();
		TestSubscriber<String> testSubscriber2 = new TestSubscriber<>();
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber1);
		testSubscriber1.assertValueCount(0);
		manager.createCached("qwer", stringObservable2).subscribe(testSubscriber2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testSubscriber1.assertValueCount(1);
		testSubscriber1.assertValue("1");
		testSubscriber2.assertValueCount(1);
		testSubscriber2.assertValue("1");
		verify(mock).getNext();
	}


	@Test
	public void testsubscribeSingle_twoSubscribe_unsubscribeOnWork() {
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		TestScheduler scheduler = new TestScheduler();
		when(mock.getNext()).thenReturn("1", "2", "3");

		Observable<String> stringObservable =
				Observable.zip(
						Observable.just(mock.getNext(), mock.getNext(), mock.getNext())
								.toList()
								.concatMap(Observable::from),
						Observable.interval(1, TimeUnit.SECONDS, scheduler),
						(s, aLong) -> s);

		// Act
		TestSubscriber<String> testSubscriber1 = new TestSubscriber<>();
		TestSubscriber<String> testSubscriber2 = new TestSubscriber<>();
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber1);
		manager.createCached("qwer", stringObservable).subscribe(testSubscriber2);
		scheduler.advanceTimeTo(1, TimeUnit.SECONDS);
		testSubscriber2.unsubscribe();
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testSubscriber1.assertValues("1", "2", "3");
		testSubscriber1.assertCompleted();
		testSubscriber2.assertValues("1");
		testSubscriber2.assertNotCompleted();
		testSubscriber2.assertUnsubscribed();
		verify(mock, times(3)).getNext();
	}

	@Test
	public void testsubscribeSingle_newInstanceAfterUnsubscribe() {
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		when(mock.getNext()).thenReturn("1", "2", "3");
		TestScheduler scheduler = new TestScheduler();

		Observable<String> stringObservable = Observable.fromCallable(mock::getNext)
				.delay(2, TimeUnit.SECONDS, scheduler);

		// Act
		TestSubscriber<String> testSubscriber1 = new TestSubscriber<>();
		TestSubscriber<String> testSubscriber2 = new TestSubscriber<>();

		manager.createCached("qwer", stringObservable).subscribe(testSubscriber1);
		scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
		testSubscriber1.unsubscribe();
		scheduler.advanceTimeBy(50, TimeUnit.MILLISECONDS);


		manager.createCached("qwer", stringObservable).subscribe(testSubscriber2);
		scheduler.advanceTimeBy(3, TimeUnit.SECONDS);


		// Assert
		testSubscriber1.assertNoValues();
		testSubscriber1.assertNotCompleted();
		testSubscriber1.assertUnsubscribed();
		testSubscriber2.assertValueCount(1);
		testSubscriber2.assertValue("2");
		verify(mock, times(2)).getNext();
	}

	interface ITestClass {
		String getNext();
		Exception getNextException();
	}

}