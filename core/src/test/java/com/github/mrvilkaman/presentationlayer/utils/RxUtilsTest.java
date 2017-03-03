package com.github.mrvilkaman.presentationlayer.utils;

import com.github.mrvilkaman.testsutils.BaseTestCase;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static rx.Observable.just;


public class RxUtilsTest extends BaseTestCase {

	@Test
	public void testMapList() {
		// Arrange
		TestSubscriber<List<Integer>> subscriber = new TestSubscriber<>();

		// Act
		Observable<List<Integer>> observable =
				just(Arrays.asList(1, 2, 3)).compose(RxUtils.mapList(val -> val * 10));
		observable.subscribe(subscriber);

		// Assert
		subscriber.assertValue(Arrays.asList(10, 20, 30));
	}

	@Test
	public void testMapList2() {
		// Arrange
		TestSubscriber<List<String>> subscriber = new TestSubscriber<>();

		// Act
		Observable<List<String>> observable =
				just(Arrays.asList(1, 2, 3)).compose(RxUtils.mapList(Object::toString));
		observable.subscribe(subscriber);

		// Assert
		subscriber.assertValue(Arrays.asList("1", "2", "3"));
	}


}