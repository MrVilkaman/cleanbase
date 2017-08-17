package com.github.mrvilkaman.testsutils;


import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static junit.framework.Assert.fail;

public class ProviderUtils {

//	public static <T extends BaseResponse> RestApi mockApi(RestApi api, BaseResponse value) {
//		return Mockito.doReturn(just(value))
//				.when(api);
//	}

	public static void assetSuccuse(TestSubscriber<?> subscriber) {
		subscriber.assertComplete();
		subscriber.assertNoErrors();
	}


	@SuppressWarnings("unchecked")
	public static <T> T getNextEvent(TestSubscriber<T> subscriber) {
		return subscriber.values().get(0);
	}

	public static void assertDisposable(TestObserver<?> subscriber) {
		subscriber.assertOf(new Consumer<TestObserver<?>>() {
			@Override
			public void accept(TestObserver<?> testObserver) throws Exception {
				if (!testObserver.isDisposed()) {
					fail("is Not Disposed!");
				}
			}
		});
	}
}
