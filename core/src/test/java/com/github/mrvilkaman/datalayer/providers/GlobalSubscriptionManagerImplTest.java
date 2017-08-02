package com.github.mrvilkaman.datalayer.providers;

import com.github.mrvilkaman.domainlayer.exceptions.UnauthorizedException;
import com.github.mrvilkaman.domainlayer.providers.GlobalSubscriptionManager;
import com.github.mrvilkaman.testsutils.BaseTestCase;
import com.github.mrvilkaman.testsutils.ProviderUtils;
import com.github.mrvilkaman.utils.bus.Bus;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.PublishSubject;

import static com.github.mrvilkaman.testsutils.ProviderUtils.*;
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

	
	@Test
	public void testSubscribe_success() {
		// Arrange
		PublishSubject<String> subject = PublishSubject.create();
		TestObserver<String> client = new TestObserver<>();
		Observable<String> stringObservable = subject.doOnNext(client::onNext);

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
	public void testSubscribeCompose_success() {
		// Arrange
		PublishSubject<String> subject = PublishSubject.create();
		TestObserver<String> client = new TestObserver<>();
		Observable<String> stringObservable = subject.doOnNext(client::onNext);

		// Act
		subject.onNext("qwer1");
		stringObservable.compose(manager.subscribe());
		subject.onNext("qwer2");

		// Assert
		client.assertValueCount(1);
		client.assertValue("qwer2");
	}


	@Test
	public void testSubscribeCompose_error() {
		// Act
		UnauthorizedException exception = new UnauthorizedException();
		Observable.error(exception).compose(manager.subscribe());

		// Assert
		verify(bus).publish(eq(GlobalBusQuery.GLOBAL_ERRORS), eq(exception));
	}

	@Test
	public void testSubscribeWithResult_error_inUI() {
		// Arrange
		UnauthorizedException exception = new UnauthorizedException();
		TestObserver<Object> subscriber = new TestObserver<>();
		PublishSubject<Object> subject = PublishSubject.create();
		// Act
		Observable<Object> objectObservable = subject.compose(manager.subscribeWithResult());
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
		TestObserver<Object> subscriber = new TestObserver<>();
		PublishSubject<Object> subject = PublishSubject.create();

		// Act
		Observable<Object> objectObservable = subject.compose(manager.subscribeWithResult());;
		objectObservable.subscribeWith(subscriber).dispose();
		subject.onError(exception);

		// Assert
		verify(bus).publish(eq(GlobalBusQuery.GLOBAL_ERRORS), eq(exception));
		subscriber.assertNoErrors();
		assertDisposable(subscriber);
	}


	@Test
	public void testSubscribeWithResult_nextAndCompleteWithResponse() {
		// Arrange
		TestObserver<String> subscriber = new TestObserver<>();
		TestObserver<String> client = new TestObserver<>();
		PublishSubject<String> subject = PublishSubject.create();
		Observable<String> stringObservable =
				subject.doOnNext(client::onNext).doOnComplete(client::onComplete);

		// Act
		Observable<String> objectObservable =
				stringObservable.compose(manager.subscribeWithResult());
		objectObservable.subscribeWith(subscriber);
		subject.onNext("qwer");
		subject.onComplete();

		// Assert
		subscriber.assertValue("qwer");
		subscriber.assertComplete();
		client.assertValue("qwer");
		client.assertComplete();
	}


	@Test
	public void testSubscribeWithResult_nextAndComplete_NoUI() {
		// Arrange
		TestObserver<String> subscriber = new TestObserver<>();
		TestObserver<String> client = new TestObserver<>();
		PublishSubject<String> subject = PublishSubject.create();
		Observable<String> stringObservable =
				subject.doOnNext(client::onNext).doOnComplete(client::onComplete);

		// Act
		Observable<String> objectObservable =
				stringObservable.compose(manager.subscribeWithResult());
		objectObservable.subscribeWith(subscriber).dispose();
		subject.onNext("qwer");
		subject.onComplete();

		// Assert
		subscriber.assertNoValues();
		subscriber.assertNotComplete();
		assertDisposable(subscriber);
		client.assertValue("qwer");
		client.assertComplete();
	}

	@Test
	public void testsubscribeSingle_twoSubscribe_oneDate() {
		// Arrange
		ITestClass mock = Mockito.mock(ITestClass.class);
		when(mock.getNext()).thenReturn("1", "2", "3");
		TestScheduler scheduler = new TestScheduler();

		Observable<String> stringObservable = Observable.fromCallable(mock::getNext)
				.delay(1, TimeUnit.SECONDS, scheduler);

		// Act
		TestObserver<String> testObserver1 = new TestObserver<>();
		TestObserver<String> testObserver2 = new TestObserver<>();
		stringObservable.compose(manager.createCached("qwer")).subscribe(testObserver1);
		testObserver1.assertValueCount(0);
		stringObservable.compose(manager.createCached("qwer")).subscribe(testObserver2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testObserver1.assertValueCount(1);
		testObserver1.assertValue("1");
		testObserver2.assertValueCount(1);
		testObserver2.assertValue("1");
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
						.toObservable()
						.concatMap(Observable::fromIterable),
						Observable.interval(1, TimeUnit.SECONDS, scheduler),
						(s, aLong) -> s);

		// Act
		TestObserver<String> TestObserver1 = new TestObserver<>();
		TestObserver<String> TestObserver2 = new TestObserver<>();
		manager.createCached("qwer", stringObservable).subscribe(TestObserver1);
		scheduler.advanceTimeTo(2, TimeUnit.SECONDS);
		TestObserver1.assertValues("1", "2");

		manager.createCached("qwer", stringObservable).subscribe(TestObserver2);

		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		TestObserver1.assertValues("1", "2", "3");
		TestObserver2.assertValues("1", "2", "3");
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
		TestObserver<String> TestObserver1 = new TestObserver<>();
		TestObserver<String> TestObserver2 = new TestObserver<>();
		manager.createCached("qwer", stringObservable).subscribe(TestObserver1);
		manager.createCached("qwer", stringObservable).subscribe(TestObserver2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		TestObserver1.assertError(exception1);
		TestObserver2.assertError(exception1);
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
		TestObserver<String> TestObserver1 = new TestObserver<>();
		TestObserver<String> TestObserver2 = new TestObserver<>();
		manager.createCached("qwer", stringObservable).subscribe(TestObserver1);
		TestObserver1.assertValueCount(0);
		manager.createCached("qwer", stringObservable).subscribe(TestObserver2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		TestObserver<String> TestObserver3 = new TestObserver<>();
		manager.createCached("qwer", stringObservable).subscribe(TestObserver3);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		TestObserver1.assertValueCount(1);
		TestObserver1.assertValue("1");
		TestObserver2.assertValueCount(1);
		TestObserver2.assertValue("1");
		TestObserver3.assertValueCount(1);
		TestObserver3.assertValue("2");
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
		TestObserver<String> TestObserver1 = new TestObserver<>();
		TestObserver<String> TestObserver2 = new TestObserver<>();
		manager.createCached("qwer", stringObservable).subscribe(TestObserver1);
		TestObserver1.assertValueCount(0);
		manager.createCached("qwer", stringObservable2).subscribe(TestObserver2);
		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		TestObserver1.assertValueCount(1);
		TestObserver1.assertValue("1");
		TestObserver2.assertValueCount(1);
		TestObserver2.assertValue("1");
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
								.toObservable()
								.concatMap(Observable::fromIterable),
						Observable.interval(1, TimeUnit.SECONDS, scheduler),
						(s, aLong) -> s);

		// Act
		TestObserver<String> testObserver1 = new TestObserver<>();
		TestObserver<String> testObserver2 = new TestObserver<>();
		manager.createCached("qwer", stringObservable).subscribe(testObserver1);
		manager.createCached("qwer", stringObservable).subscribe(testObserver2);
		scheduler.advanceTimeTo(1, TimeUnit.SECONDS);
		testObserver2.dispose();

		scheduler.advanceTimeBy(2, TimeUnit.SECONDS);

		// Assert
		testObserver1.assertValues("1", "2", "3");
		testObserver1.assertComplete();
		testObserver2.assertValues("1");
		testObserver2.assertNotComplete();
		assertDisposable(testObserver2);
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
		TestObserver<String> testObserver1 = new TestObserver<>();
		TestObserver<String> testObserver2 = new TestObserver<>();

		manager.createCached("qwer", stringObservable).subscribe(testObserver1);
		scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
		testObserver1.dispose();
		scheduler.advanceTimeBy(50, TimeUnit.MILLISECONDS);


		manager.createCached("qwer", stringObservable).subscribe(testObserver2);
		scheduler.advanceTimeBy(3, TimeUnit.SECONDS);


		// Assert
		testObserver1.assertNoValues();
		testObserver1.assertNotComplete();
		assertDisposable(testObserver1);
		testObserver2.assertValueCount(1);
		testObserver2.assertValue("2");
		verify(mock, times(2)).getNext();
	}

	interface ITestClass {
		String getNext();
		Exception getNextException();
	}

}