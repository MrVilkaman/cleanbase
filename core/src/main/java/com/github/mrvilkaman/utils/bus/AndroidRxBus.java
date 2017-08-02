package com.github.mrvilkaman.utils.bus;


import android.util.Log;
import android.util.SparseArray;

import io.reactivex.Observable;
import io.reactivex.subjects.Subject;

/**
 * Simple {@link Bus} implementation with {@link Queue} caching and logging specific
 * to the Android platform. By default subscriptions are observed on the main thread.
 */
@SuppressWarnings("WeakerAccess")
public final class AndroidRxBus implements Bus {

	public static AndroidRxBus create() {
		return create(new AndroidQueueCache());
	}

	public static AndroidRxBus create(RxBus.QueueCache cache) {
		return create(cache, new RxBus.Logger() {
			@Override
			public void log(String message) {
				Log.d(TAG, message);
			}
		});
	}

	public static AndroidRxBus create(RxBus.Logger logger) {
		return create(new AndroidQueueCache(), logger);
	}

	public static AndroidRxBus create(RxBus.QueueCache cache, RxBus.Logger logger) {
		return new AndroidRxBus(cache, logger);
	}

	private static final String TAG = AndroidRxBus.class.getSimpleName();

	private final Bus bus;

	private AndroidRxBus(RxBus.QueueCache cache, RxBus.Logger logger) {
		bus = RxBus.create(cache, logger);
	}

	@Override
	public <T> void publish(Queue<T> queue, T event) {
		bus.publish(queue, event);
	}

	@Override
	public <T> Observable<T> queue(Queue<T> queue) {
		return bus.queue(queue);
	}


	private static final class AndroidQueueCache implements RxBus.QueueCache {

		private final SparseArray<Subject<?>> sparseArray = new SparseArray<>();

		@Override
		@SuppressWarnings("unchecked")
		public <T> Subject<T> get(Queue<T> queue) {
			return (Subject<T>) sparseArray.get(queue.getId());
		}

		@Override
		public <T> void put(Queue<T> queue, Subject<T> relay) {
			sparseArray.put(queue.getId(), relay);
		}
	}
}