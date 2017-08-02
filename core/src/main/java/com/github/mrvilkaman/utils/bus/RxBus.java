package com.github.mrvilkaman.utils.bus;


import java.lang.ref.Reference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus implements Bus {

	private final QueueCache cache;
	private final Logger logger;
	private final Map<Integer, List<Reference<Consumer<?>>>> loggedObservers;

	private RxBus(QueueCache cache, Logger logger) {
		if (cache == null) {
			throw new IllegalArgumentException("cache cannot be null");
		}
		this.cache = cache;
		this.logger = logger;
		this.loggedObservers = this.logger != null
				? new HashMap<Integer, List<Reference<Consumer<?>>>>() : null;
	}

	/**
	 * Creates a new instance of {@link Bus} configured with a default {@link QueueCache}.
	 */
	public static Bus create() {
		return create(new DefaultQueueCache());
	}

	/**
	 * Creates a new instance of {@link Bus} configured with the given {@link QueueCache}.
	 */
	public static Bus create(QueueCache cache) {
		return create(cache, null);
	}

	/**
	 * Creates a new instance of {@link Bus} configured with the given {@link Logger}.
	 */
	public static Bus create(Logger logger) {
		return create(new DefaultQueueCache(), logger);
	}

	/**
	 * Creates a new instance of {@link Bus} configured with the given {@link QueueCache}
	 * and {@link Logger}.
	 */
	public static Bus create(QueueCache cache, Logger logger) {
		return new RxBus(cache, logger);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> void publish(Queue<T> queue, T event) {
		if (shouldLog()) {
			logEvent(queue, event);
		}
		queue(queue).onNext(event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> Subject<T> queue(Queue<T> queue) {
		Subject<T> relay = cache.get(queue);
		if (relay == null) {
			if (queue.getDefaultEvent() != null) {
				relay = BehaviorSubject.createDefault(queue.getDefaultEvent());
			} else if (queue.isReplayLast()) {
				relay = BehaviorSubject.create();
			} else {
				relay = PublishSubject.create();
			}
			cache.put(queue, relay);
		}
		return relay;
	}

	private boolean shouldLog() {
		return logger != null && loggedObservers != null;
	}

	private void logEvent(Queue queue, Object obj) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Publishing to ")
				.append(queue.getName());
		stringBuilder.append(" [")
				.append(obj)
				.append("]\n");
		List list = loggedObservers.get(queue.getId());
		if (list != null && !list.isEmpty()) {
			stringBuilder.append("Delivering to: \n");
			Iterator iterator = list.iterator();
			do {
				if (!iterator.hasNext()) {
					break;
				}
				Consumer observer = (Consumer) ((Reference) iterator.next()).get();
				if (observer != null) {
					stringBuilder
							.append("-> ")
							.append(observer.getClass()
									.getCanonicalName())
							.append('\n');
				}
			} while (true);
		} else {
			stringBuilder.append("No observers found.");
		}
		logger.log(stringBuilder.toString());
	}

	/**
	 * Classes implementing this interface log debug messages from the {@link RxBus}.
	 */
	public interface Logger {
		void log(String message);
	}

	/**
	 * Classes implementing this interface provide a cache of {@link Subject}s associated to
	 * {@link Queue}s used by the {@link RxBus}.
	 */
	public interface QueueCache {
		<T> Subject<T> get(Queue<T> queue);

		<T> void put(Queue<T> queue, Subject<T> relay);
	}

	static final class DefaultQueueCache implements QueueCache {

		private final Map<Queue<?>, Subject<?>> map
				= Collections.synchronizedMap(new WeakHashMap<Queue<?>, Subject<?>>());

		@Override
		@SuppressWarnings("unchecked")
		public <T> Subject<T> get(Queue<T> queue) {
			return (Subject<T>) map.get(queue);
		}

		@Override
		public <T> void put(Queue<T> queue, Subject<T> relay) {
			map.put(queue, relay);
		}
	}
}