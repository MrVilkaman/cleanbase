package ru.fixapp.fooproject.testsutils;


import rx.observers.TestSubscriber;

public class ProviderUtils {

//	public static <T extends BaseResponse> RestApi mockApi(RestApi api, BaseResponse value) {
//		return Mockito.doReturn(just(value))
//				.when(api);
//	}

	public static void assetSuccuse(TestSubscriber<?> subscriber) {
		subscriber.assertCompleted();
		subscriber.assertNoErrors();
	}


	public static <T> T getNextEvent(TestSubscriber<T> subscriber) {
		return subscriber.getOnNextEvents()
				.get(0);
	}
}
